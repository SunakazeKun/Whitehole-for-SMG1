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

package whitehole.smg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import whitehole.HashDB;
import whitehole.io.FileBase;

public class Bcsv  {
    public static final int TYPE_INT32 = 0;
    public static final int TYPE_FLOAT32 = 2;
    public static final int TYPE_INT32_2 = 3;
    public static final int TYPE_INT16 = 4;
    public static final int TYPE_INT8 = 5;
    public static final int TYPE_STRING = 6;
    private static final int[] FIELD_SIZES = { 4, -1, 4, 4, 2, 1, 4 };
    
    private FileBase file;
    public LinkedHashMap<Integer, Field> fields;
    public List<Entry> entries;
    
    public Bcsv(FileBase file) throws IOException {
        this.file = file;
        file.setBigEndian(true);
        
        if (file.getLength() == 0) {
            fields = new LinkedHashMap();
            entries = new ArrayList();
            return;
        }
        
        file.position(0);
        int entrycount = file.readInt();
        int fieldcount = file.readInt();
        int dataoffset = file.readInt();
        int entrydatasize = file.readInt();
        int stringtableoffset = (int)(dataoffset + (entrycount * entrydatasize));
        
        fields = new LinkedHashMap(fieldcount);
        entries = new ArrayList(entrycount);
        
        for (int i = 0; i < fieldcount; i++) {
            Field field = new Field();
            file.position(0x10 + (0xC * i));
            
            field.hash = file.readInt();
            field.mask = file.readInt();
            field.offset = file.readShort();
            field.shift = file.readByte();
            field.type = file.readByte();
            
            String fieldname = HashDB.hashToName(field.hash);
            field.name = fieldname;
            fields.put(field.hash, field);
        }
        
        for (int i = 0; i < entrycount; i++) {
            Entry entry = new Entry();
            
            for (Field field: fields.values()) {
                file.position(dataoffset + (i * entrydatasize) + field.offset);
                Object val = null;
                
                switch (field.type) {
                    case TYPE_INT32:
                    case TYPE_INT32_2:
                        val = (int)(file.readInt() & field.mask >>> field.shift);
                        break;
                    case TYPE_INT16:
                        val = (short)(file.readShort() & field.mask >>> field.shift);
                        break;
                    case TYPE_INT8:
                        val = (byte)(file.readByte() & field.mask >>> field.shift);
                        break;
                    case TYPE_FLOAT32:
                        val = file.readFloat();
                        break;
                    case TYPE_STRING:
                        file.position(file.readInt() + stringtableoffset);
                        val = file.readString("SJIS", 0);
                        break;
                    default:
                        throw new IOException(String.format("Bcsv: Unsupported data type 0x%02X", field.type));
                }
                
                entry.put(field.hash, val);
            }
            
            entries.add(entry);
        }
    }

    public void save() throws IOException {
        int dataoffset = 0x10 + 0xC * fields.size();
        int entrysize = (fixFields() + 3) & ~3;
        int stringtableoffset = dataoffset + entrysize * entries.size();
        
        file.setBigEndian(true);
        file.setLength(stringtableoffset);
        
        file.position(0);
        file.writeInt(entries.size());
        file.writeInt(fields.size());
        file.writeInt(dataoffset);
        file.writeInt(entrysize);
        
        for (Field field : fields.values()) {
            file.writeInt(field.hash);
            file.writeInt(field.mask);
            file.writeShort(field.offset);
            file.writeByte(field.shift);
            file.writeByte(field.type);
        }
        
        HashMap<String, Integer> stringoffsets = new HashMap();
        int curstring = 0;
        int curentry = 0;
        
        for (Entry entry : entries) {
            for (Field field : fields.values()) {
                int valoffset = (int)(dataoffset + (curentry * entrysize) + field.offset);
                file.position(valoffset);
                
                switch (field.type) {
                    case TYPE_INT32:
                    case TYPE_INT32_2:
                        file.writeInt((entry.getInt(field.hash) << field.shift) & field.mask);
                        break;
                    case TYPE_INT16:
                        file.writeShort((short)((entry.getShort(field.hash) << field.shift) & field.mask));
                        break;
                    case TYPE_INT8:
                        file.writeByte((byte)((entry.getByte(field.hash) << field.shift) & field.mask));
                        break;
                    case TYPE_FLOAT32:
                        file.writeFloat(entry.getFloat(field.hash));
                        break;
                    case TYPE_STRING:
                        String val = (String)entry.get(field.hash);
                        if (stringoffsets.containsKey(val))
                            file.writeInt(stringoffsets.get(val));
                        else {
                            stringoffsets.put(val, curstring);
                            file.writeInt(curstring);
                            file.position(stringtableoffset + curstring);
                            curstring += file.writeString("SJIS", val, 0);
                        }
                        break;
                }
            }
            
            curentry++;
        }
        
        curentry = (int)file.getLength();
        file.position(curentry);
        int alignend = (curentry + 0x1F) & ~0x1F;
        for ( ; curentry < alignend ; curentry++)
            file.writeByte((byte)0x40);
        
        file.save();
    }

    public void close() throws IOException {
        file.close();
    }
    
    public Field addField(int hash, int type, int mask, int shift, Object defval) {
        Field newfield = new Field();
        newfield.name = HashDB.hashToName(hash);
        newfield.hash = hash;
        newfield.mask = mask;
        newfield.shift = (byte)shift;
        newfield.type = (byte)type;
        fields.put(newfield.hash, newfield);

        for (Entry entry : entries)
            entry.put(newfield.name, defval);

        return newfield;
    }

    public Field addField(String name, int type, int mask, int shift, Object defaultval) {
        return addField(HashDB.addHash(name), type, mask, shift, defaultval);
    }
    
    public void removeField(int hash) {
        fields.remove(hash);

        for (Entry entry : entries)
            entry.remove(hash);
    }

    public void removeField(String name) {
        removeField(HashDB.nameToHash(name));
    }
    
    private int fixFields() {
        int entryoffset = 0;
        
        for (Field field : fields.values()) {
            field.offset = (short)entryoffset;
            entryoffset += FIELD_SIZES[field.type];
            
            switch (field.type) {
                case TYPE_FLOAT32:
                case TYPE_STRING:
                    field.mask = 0xFFFFFFFF;
                    field.shift = 0;
                    break;
                case TYPE_INT16:
                    field.mask &= 0xFFFF;
                    break;
                case TYPE_INT8:
                    field.mask &= 0xFF;
                    break;
            }
        }
        
        return entryoffset;
    }

    public static class Field {
        public String name;
        public int hash;
        public byte type;
        private int mask;
        private short offset;
        private byte shift;
    }

    public static class Entry extends LinkedHashMap<Integer, Object> {
        public boolean containsKey(String key) {
            return containsKey(HashDB.nameToHash(key));
        }
        
        public Object get(String key) {
            return get(HashDB.nameToHash(key));
        }
        
        public Object getOrDefault(String key, Object val) {
            return getOrDefault(HashDB.nameToHash(key), val);
        }
        
        public void put(String key, Object val) {
            put(HashDB.nameToHash(key), val);
        }
        
        public int getInt(Integer key) {
            return (int)getOrDefault(key, -1);
        }
        
        public int getInt(String key) {
            return getInt(HashDB.nameToHash(key));
        }
        
        public short getShort(Integer key) {
            return (short)getOrDefault(key, -1);
        }
        
        public short getShort(String key) {
            return getShort(HashDB.nameToHash(key));
        }
        
        public byte getByte(Integer key) {
            return (byte)getOrDefault(key, -1);
        }
        
        public byte getByte(String key) {
            return getByte(HashDB.nameToHash(key));
        }
        
        public float getFloat(Integer key) {
            return (float)getOrDefault(key, 0f);
        }
        
        public float getFloat(String key) {
            return getFloat(HashDB.nameToHash(key));
        }
        
        public String getString(Integer key) {
            return (String)getOrDefault(key, "");
        }
        
        public String getString(String key) {
            return getString(HashDB.nameToHash(key));
        }
    }
}