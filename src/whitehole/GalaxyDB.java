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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public final class GalaxyDB {
    public static HashMap<String, HashMap<String, String>> WORLDS;
    
    private GalaxyDB() {}
    
    public static void init() {
        WORLDS = new LinkedHashMap();
        
        File dbfile = new File("assets/galaxies.xml");
        if (!(dbfile.exists() && dbfile.isFile()))
            return;
        
        Element root;
        
        try {
            root = new SAXBuilder().build(dbfile).getRootElement();
        }
        catch(IOException | JDOMException ex) {
            return;
        }
        
        for (Element eworld : root.getChildren("world")) {
            String worldname = eworld.getAttributeValue("name");
            HashMap<String, String> galaxies = new LinkedHashMap();
            
            for (Element egalaxy : eworld.getChildren("galaxy")) {
                String galaxyname = egalaxy.getAttributeValue("name");
                String galaxyfile = egalaxy.getAttributeValue("file");
                galaxies.put(galaxyfile, galaxyname);
            }
            
            WORLDS.put(worldname, galaxies);
        }
    }
}
