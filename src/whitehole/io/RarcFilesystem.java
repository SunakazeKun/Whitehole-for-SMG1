// Copyright © 2019 - Whitehole for SMG1 team
//
// This file is part of "Whitehole for SMG1"
//
// "Whitehole for SMG1" is free software: you can redistribute it and/or modify it under
// the terms of the GNU General Public License as published by the Free
// Software Foundation, either version 3 of the License, or (at your option)
// any later version.
//
// "Whitehole for SMG1" is distributed in the hope that it will be useful, but WITHOUT ANY 
// WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
// FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along 
// with "Whitehole for SMG1". If not, see http://www.gnu.org/licenses/.

package whitehole.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class RarcFilesystem implements FilesystemBase {
    public RarcFilesystem(FileBase basefile) throws IOException {
        baseFile = basefile;
        baseFile.setBigEndian(true);
        baseFile.position(0);
        
        int tag = baseFile.readInt();
        if (tag != 0x52415243) 
            throw new IOException(String.format("File isn't a RARC (tag 0x%1$08X, expected 0x52415243)", tag));

        baseFile.position(0xC);
        int fileDataOffset = baseFile.readInt() + 0x20;
        baseFile.position(0x20);
        int numDirNodes = baseFile.readInt();
        int dirNodesOffset = baseFile.readInt() + 0x20;
        int numFileEntries = baseFile.readInt();
        int fileEntriesOffset = baseFile.readInt() + 0x20;
        baseFile.skip(0x4);
        int stringTableOffset = baseFile.readInt() + 0x20;
        unk38 = baseFile.readInt();

        dirEntries = new LinkedHashMap<>(numDirNodes);
        fileEntries = new LinkedHashMap<>(numFileEntries);

        DirEntry root = new DirEntry();
        root.parentDir = null;

        baseFile.position(dirNodesOffset + 0x6);
        int rnoffset = baseFile.readShort();
        baseFile.position(stringTableOffset + rnoffset);
        root.name = baseFile.readString("ASCII", 0);
        root.fullName = "/" + root.name;
        root.tempID = 0;

        dirEntries.put("/", root);
        for (int i = 0; i < numDirNodes; i++)
        {
            DirEntry parentdir = null;
            for (DirEntry de : dirEntries.values())
            {
                if (de.tempID == i)
                {
                    parentdir = de;
                    break;
                }
            }

            baseFile.position(dirNodesOffset + (i * 0x10) + 10);

            short numentries = baseFile.readShort();
            int firstentry = baseFile.readInt();
            for (int j = 0; j < numentries; j++)
            {
                int entryoffset = fileEntriesOffset + ((j + firstentry) * 0x14);
                baseFile.position(entryoffset);

                baseFile.skip(4);
                int entrytype = baseFile.readShort() & 0xFFFF;
                int nameoffset = baseFile.readShort() & 0xFFFF;
                int dataoffset = baseFile.readInt();
                int datasize = baseFile.readInt();

                baseFile.position(stringTableOffset + nameoffset);
                String name = baseFile.readString("ASCII", 0);
                if (name.equals(".") || name.equals("..")) continue;
                
                String fullname = parentdir.fullName + "/" + name;

                if (entrytype == 0x0200)
                {
                    DirEntry d = new DirEntry();
                    d.parentDir = parentdir;
                    d.name = name;
                    d.fullName = fullname;
                    d.tempID = dataoffset;

                    dirEntries.put(pathToKey(fullname), d);
                    parentdir.childrenDirs.add(d);
                }
                else
                {
                    FileEntry f = new FileEntry();
                    f.parentDir = parentdir;
                    f.dataOffset = fileDataOffset + dataoffset;
                    f.dataSize = datasize;
                    f.name = name;
                    f.fullName = fullname;
                    f.data = null;

                    fileEntries.put(pathToKey(fullname), f);
                    parentdir.childrenFiles.add(f);
                }
            }
        }
    }
    
    // fix: ignore the root directory name in filenames
    // SMG ignores it as well, and some RARC packers set it arbitrarily
    private String pathToKey(String path)
    {
        String ret = path.toLowerCase();
        ret = ret.substring(1);
        if (!ret.contains("/"))
            return "/";
        ret = ret.substring(ret.indexOf("/"));
        return ret;
    }
    
    private int align32(int val)
    {
        return (val + 0x1F) & ~0x1F;
    }
    
    private int dirMagic(String name)
    {
        String uppername = name.toUpperCase();
        int ret = 0;
        
        for (int i = 0; i < 4; i++)
        {
            ret <<= 8;
            
            if (i >= uppername.length())
                ret += 0x20;
            else
                ret += uppername.charAt(i);
        }
        
        return ret;
    }
    
    private short nameHash(String name)
    {
        short ret = 0;
        for (char ch : name.toCharArray())
        {
            ret *= 3;
            ret += ch;
        }
        return ret;
    }
    
    @Override
    public void save() throws IOException
    {
        for (FileEntry fe : fileEntries.values())
        {
            if (fe.data != null) continue;
            baseFile.position(fe.dataOffset);
            fe.data = baseFile.readBytes(fe.dataSize);
        }
        
        int dirOffset = 0x40;
        int fileOffset = dirOffset + align32(dirEntries.size() * 0x10);
        int stringOffset = fileOffset + align32((fileEntries.size() + (dirEntries.size() * 3) - 1) * 0x14);
        
        int dataOffset = stringOffset;
        int dataLength = 0;
        for (DirEntry de : dirEntries.values())
            dataOffset += de.name.length() + 1;
        for (FileEntry fe : fileEntries.values())
        {
            dataOffset += fe.name.length() + 1;
            dataLength += align32(fe.dataSize);
        }
        dataOffset += 5;
        dataOffset = align32(dataOffset);
        
        int dirSubOffset = 0;
        int fileSubOffset = 0;
        int stringSubOffset = 0;
        int dataSubOffset = 0;
        
        baseFile.setLength(dataOffset + dataLength);
        
        // RARC header
        // certain parts of this will have to be written later on
        baseFile.position(0);
        baseFile.writeInt(0x52415243);
        baseFile.writeInt(dataOffset + dataLength);
        baseFile.writeInt(0x00000020);
        baseFile.writeInt(dataOffset - 0x20);
        baseFile.writeInt(dataLength);
        baseFile.writeInt(dataLength);
        baseFile.writeInt(0x00000000);
        baseFile.writeInt(0x00000000);
        baseFile.writeInt(dirEntries.size());
        baseFile.writeInt(dirOffset - 0x20);
        baseFile.writeInt(fileEntries.size() + (dirEntries.size() * 3) - 1);
        baseFile.writeInt(fileOffset - 0x20);
        baseFile.writeInt(dataOffset - stringOffset);
        baseFile.writeInt(stringOffset - 0x20);
        baseFile.writeInt(unk38);
        baseFile.writeInt(0x00000000);
        
        baseFile.position(stringOffset);
        baseFile.writeString("ASCII", ".", 0);
        baseFile.writeString("ASCII", "..", 0);
        stringSubOffset += 5;
        
        Stack<Iterator<DirEntry>> dirstack = new Stack<>();
        Object[] entriesarray = dirEntries.values().toArray();
        DirEntry curdir = (DirEntry)entriesarray[0];
        int c = 1;
        while (curdir.parentDir != null) curdir = (DirEntry)entriesarray[c++];
        short fileid = 0;
        for (;;)
        {
            // write the directory node
            curdir.tempID = dirSubOffset / 0x10;
            baseFile.position(dirOffset + dirSubOffset);
            baseFile.writeInt((curdir.tempID == 0) ? 0x524F4F54 : dirMagic(curdir.name));
            baseFile.writeInt(stringSubOffset);
            baseFile.writeShort(nameHash(curdir.name));
            baseFile.writeShort((short)(2 + curdir.childrenDirs.size() + curdir.childrenFiles.size()));
            baseFile.writeInt(fileSubOffset / 0x14);
            dirSubOffset += 0x10;
            
            if (curdir.tempID > 0)
            {
                baseFile.position(curdir.tempNameOffset);
                baseFile.writeShort((short)stringSubOffset);
                baseFile.writeInt(curdir.tempID);
            }
            baseFile.position(stringOffset + stringSubOffset);
            stringSubOffset += baseFile.writeString("ASCII", curdir.name, 0);
            
            // write the child file/dir entries
            baseFile.position(fileOffset + fileSubOffset);
            for (DirEntry cde : curdir.childrenDirs)
            {
                baseFile.writeShort((short)0xFFFF);
                baseFile.writeShort(nameHash(cde.name));
                baseFile.writeShort((short)0x0200);
                cde.tempNameOffset = (int)baseFile.position();
                baseFile.skip(6);
                baseFile.writeInt(0x00000010);
                baseFile.writeInt(0x00000000);
                fileSubOffset += 0x14;
            }
            
            for (FileEntry cfe : curdir.childrenFiles)
            {
                baseFile.position(fileOffset + fileSubOffset);
                baseFile.writeShort(fileid);
                baseFile.writeShort(nameHash(cfe.name));
                baseFile.writeShort((short)0x1100);
                baseFile.writeShort((short)stringSubOffset);
                baseFile.writeInt(dataSubOffset);
                baseFile.writeInt(cfe.dataSize);
                baseFile.writeInt(0x00000000);
                fileSubOffset += 0x14;
                fileid++;

                baseFile.position(stringOffset + stringSubOffset);
                stringSubOffset += baseFile.writeString("ASCII", cfe.name, 0);
                
                baseFile.position(dataOffset + dataSubOffset);
                cfe.dataOffset = (int)baseFile.position();
                byte[] thedata = Arrays.copyOf(cfe.data, cfe.dataSize);
                baseFile.writeBytes(thedata);
                dataSubOffset += align32(cfe.dataSize);
                cfe.data = null;
            }
            
            baseFile.position(fileOffset + fileSubOffset);
            baseFile.writeShort((short)0xFFFF);
            baseFile.writeShort((short)0x002E);
            baseFile.writeShort((short)0x0200);
            baseFile.writeShort((short)0x0000);
            baseFile.writeInt(curdir.tempID);
            baseFile.writeInt(0x00000010);
            baseFile.writeInt(0x00000000);
            baseFile.writeShort((short)0xFFFF);
            baseFile.writeShort((short)0x00B8);
            baseFile.writeShort((short)0x0200);
            baseFile.writeShort((short)0x0002);
            baseFile.writeInt((curdir.parentDir != null) ? curdir.parentDir.tempID : 0xFFFFFFFF);
            baseFile.writeInt(0x00000010);
            baseFile.writeInt(0x00000000);
            fileSubOffset += 0x28;
            
            // determine who's next on the list
            // * if we have a child directory, process it
            // * otherwise, look if we have remaining siblings
            // * and if none, go back to our parent and look for siblings again
            // * until we have done them all
            if (!curdir.childrenDirs.isEmpty())
            {
                dirstack.push(curdir.childrenDirs.iterator());
                curdir = dirstack.peek().next();
            }
            else
            {
                curdir = null;
                while (curdir == null)
                {
                    if (dirstack.empty())
                        break;
                    
                    Iterator<DirEntry> it = dirstack.peek();
                    if (it.hasNext())
                        curdir = it.next();
                    else
                        dirstack.pop();
                }
                
                if (curdir == null)
                    break;
            }
        }
        
        baseFile.save();
    }

    @Override
    public void close() throws IOException
    {
        baseFile.close();
    }


    @Override
    public boolean directoryExists(String directory)
    {
        return dirEntries.containsKey(pathToKey(directory));
    }

    @Override
    public List<String> getDirectories(String directory)
    {
        if (!dirEntries.containsKey(pathToKey(directory))) 
            return null;
        
        DirEntry dir = dirEntries.get(pathToKey(directory));
        
        List<String> ret = new ArrayList<>();
        for (DirEntry de : dir.childrenDirs)
        {
            ret.add(de.name);
        }
        
        return ret;
    }


    @Override
    public boolean fileExists(String filename)
    {
        return fileEntries.containsKey(pathToKey(filename));
    }

    @Override
    public List<String> getFiles(String directory)
    {
        if (!dirEntries.containsKey(pathToKey(directory))) 
            return null;
        
        DirEntry dir = dirEntries.get(pathToKey(directory));
        
        List<String> ret = new ArrayList<>();
        for (FileEntry fe : dir.childrenFiles)
        {
            ret.add(fe.name);
        }
        
        return ret;
    }

    @Override
    public FileBase openFile(String filename) throws FileNotFoundException
    {
        if (!fileEntries.containsKey(pathToKey(filename)))
            throw new FileNotFoundException(filename + " not found in RARC");
        
        try
        {
            return new RarcFile(this, filename);
        }
        catch (IOException ex)
        {
            throw new FileNotFoundException("got IOException");
        }
    }
    
    @Override
    public void createFile(String parent, String newfile)
    {
        String parentkey = pathToKey(parent);
        String fnkey = pathToKey(parent + "/" + newfile);
        if (!dirEntries.containsKey(parentkey)) return;
        if (fileEntries.containsKey(fnkey)) return;
        if (dirEntries.containsKey(fnkey)) return;
        DirEntry parentdir = dirEntries.get(parentkey);
        
        FileEntry fe = new FileEntry();
        fe.data = new byte[0];
        
        fe.dataSize = fe.data.length;
        fe.fullName = parent + "/" + newfile;
        fe.name = newfile;
        fe.parentDir = parentdir;
        
        parentdir.childrenFiles.add(fe);
        fileEntries.put(pathToKey(fe.fullName), fe);
    }

    @Override
    public void renameFile(String file, String newname)
    {
        file = pathToKey(file);
        if (!fileEntries.containsKey(file)) return;
        FileEntry fe = fileEntries.get(file);
        DirEntry parent = fe.parentDir;
        
        String parentkey = pathToKey(parent.fullName + "/" + newname);
        if (fileEntries.containsKey(parentkey) ||
            dirEntries.containsKey(parentkey)) 
            return;
        
        String fnkey = pathToKey(fe.fullName);
        fileEntries.remove(fnkey);
        
        fe.name = newname;
        fe.fullName = parent.fullName + "/" + newname;
        
        fileEntries.put(fnkey, fe);
    }

    @Override
    public void deleteFile(String file)
    {
        file = pathToKey(file);
        if (!fileEntries.containsKey(file)) return;
        FileEntry fe = fileEntries.get(file);
        DirEntry parent = fe.parentDir;
        
        parent.childrenFiles.remove(fe);
        fileEntries.remove(file);
    }


    // support functions for RarcFile
    public byte[] getFileContents(String fullname) throws IOException
    {
        FileEntry fe = fileEntries.get(pathToKey(fullname));
        
        if (fe.data != null)
        {
            byte[] thedata = Arrays.copyOf(fe.data, fe.dataSize);
            return thedata;
        }

        baseFile.position(fe.dataOffset);
        return baseFile.readBytes((int)fe.dataSize);
    }

    public void reinsertFile(RarcFile _file) throws IOException
    {
        FileEntry fe = fileEntries.get(pathToKey(_file.fileName));
        fe.data = _file.getContents();
        fe.dataSize = (int)_file.getLength();
    }


    private class FileEntry
    {
        public FileEntry()
        { 
            data = null;
        }
        
        public int dataOffset;
        public int dataSize;

        public DirEntry parentDir;

        public String name;
        public String fullName;
        
        public byte[] data;
    }

    private class DirEntry
    {
        public DirEntry()
        {
            childrenDirs = new LinkedList<>();
            childrenFiles = new LinkedList<>();
        }

        public DirEntry parentDir;
        public LinkedList<DirEntry> childrenDirs;
        public LinkedList<FileEntry> childrenFiles;

        public String name;
        public String fullName;
        
        public int tempID;
        public int tempNameOffset;
    }


    private FileBase baseFile;

    private int unk38;

    private LinkedHashMap<String, FileEntry> fileEntries;
    private LinkedHashMap<String, DirEntry> dirEntries;
}
