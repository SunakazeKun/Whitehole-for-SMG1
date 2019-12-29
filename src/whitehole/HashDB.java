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

package whitehole;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class HashDB {
    public static HashMap<Integer, String> LOOKUP; 
    
    private HashDB() {}
    
    public static void init() {
        LOOKUP = new LinkedHashMap();
        
        File dbfile = new File("assets/hashes.txt");
        if (!(dbfile.exists() && dbfile.isFile()))
            return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(dbfile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.length() == 0)
                    continue;
                if (line.charAt(0) == '#')
                    continue;

                addHash(line);
            }
        }
        catch(IOException ex) {}
    }
    
    public static int nameToHash(String field) {
        int ret = 0;
        
        for (char ch : field.toCharArray()) {
            ret *= 0x1F;
            ret += ch;
        }
        
        return ret;
    }

    public static String hashToName(int hash) {
        if (!LOOKUP.containsKey(hash))
            return String.format("[%1$08X]", hash);
        return LOOKUP.get(hash);
    }

    public static int addHash(String field) {
        int hash = nameToHash(field);
        
        if (!LOOKUP.containsKey(hash))
            LOOKUP.put(hash, field);
        
        return hash;
    }
}
