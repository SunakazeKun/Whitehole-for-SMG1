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

package whitehole.util;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static <K, V> K valueToKey(V value, HashMap<K, V> map) {
        if (map == null || map.isEmpty())
            return null;
        
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getValue().equals(value))
                return (K)entry.getKey();
        }
        
        return null;
    }
    
    public static long superFastHash(byte[] data, long start, int offset, int len) {
        long hash = start & 0xFFFFFFFF;
        long tmp;
        int rem;
        
        if (len < 1)
            return hash;
        
        rem = len & 3;
        len >>>= 2;
        
        int pos = offset;
        for (; len > 0; len--) {
            hash += (data[pos++] | (data[pos++] << 8));
            tmp = ((data[pos++] | (data[pos++] << 8)) << 11) ^ hash;
            hash = ((hash << 16) ^ tmp);
            hash += (hash >>> 11);
        }
        
        switch (rem) {
            case 3:
                hash += (data[pos++] | (data[pos++] << 8));
                hash ^= (hash << 16);
                hash ^= (data[pos++] << 18);
                hash += (hash >>> 11);
                break;

            case 2:
                hash += (data[pos++] | (data[pos++] << 8));
                hash ^= (hash << 11);
                hash += (hash >>> 17);
                break;

            case 1:
                hash += data[pos++];
                hash ^= (hash << 10);
                hash += (hash >>> 1);
                break;
        }

        hash ^= (hash << 3);
        hash += (hash >>> 5);
        hash ^= (hash << 4);
        hash += (hash >>> 17);
        hash ^= (hash << 25);
        hash += (hash >>> 6);

        return hash & 0xFFFFFFFF;
    }
}