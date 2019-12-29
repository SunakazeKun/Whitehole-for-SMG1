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

import java.io.*;
import java.util.*;

public class ExternalFilesystem implements FilesystemBase {
    private final File baseDirectory;
    
    public ExternalFilesystem(String basedir) throws IOException {
        baseDirectory = new File(basedir);
        
        if (!baseDirectory.exists())
            throw new IOException("Directory '" + basedir + "' doesn't exist");
        if (!baseDirectory.isDirectory())
            throw new IOException(basedir + " isn't a directory");
    }
    
    @Override
    public void save() {}
    
    @Override
    public void close() {}
    
    @Override
    public List<String> getDirectories(String directory) {
        File[] files = new File(baseDirectory, directory).listFiles();
        List<String> ret = new ArrayList();
        
        for (File file: files) {
            if (!file.isDirectory())
                continue;
            ret.add(file.getName());
        }
        
        return ret;
    }
    
    @Override
    public boolean directoryExists(String directory) {
        File dir = new File(baseDirectory, directory);
        return dir.exists() && dir.isDirectory();
    }
    
    @Override
    public List<String> getFiles(String directory) {
        File[] files = new File(baseDirectory, directory).listFiles();
        List<String> ret = new ArrayList();
        
        for (File file: files) {
            if (!file.isFile())
                continue;
            ret.add(file.getName());
        }
        
        return ret;
    }
    
    @Override
    public boolean fileExists(String filename) {
        File file = new File(baseDirectory, filename);
        return file.exists() && file.isFile();
    }
    
    @Override
    public FileBase openFile(String filename) throws IOException {
        File file = new File(baseDirectory, filename);
        if (!file.exists() || !file.isFile())
            throw new FileNotFoundException(file.getAbsolutePath());
        return new ExternalFile(file);
    }
    
    @Override
    public void createFile(String parent, String newname) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void renameFile(String filename, String newname) {
        File file = new File(baseDirectory, filename);
        if (file.exists() && file.isFile())
            file.renameTo(new File(baseDirectory, newname));
    }

    @Override
    public void deleteFile(String filename) {
        File file = new File(baseDirectory, filename);
        if (file.exists() && file.isFile())
            file.delete();
    }
}
