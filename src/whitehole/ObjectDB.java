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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public final class ObjectDB {
    public static HashMap<String, String> CATEGORIES;
    public static HashMap<String, DbObject> OBJECTS;
    
    private ObjectDB() {}
    
    public static void init() {
        CATEGORIES = new LinkedHashMap();
        OBJECTS = new LinkedHashMap();
        
        File dbfile = new File("assets/objectdb.xml");
        if (!(dbfile.exists() && dbfile.isFile()))
            return;
        
        Element root;
        
        try {
            root = new SAXBuilder().build(dbfile).getRootElement();
        }
        catch(IOException | JDOMException ex) {
            return;
        }
        
        for (Element ecategory : root.getChild("categories").getChildren("category")) {
            String cattag = ecategory.getAttributeValue("id");
            String catname = ecategory.getText();
            
            CATEGORIES.put(cattag, catname);
        }
        
        for (Element eobject : root.getChildren("object")) {
            DbObject dbobj = new DbObject();
            dbobj.internalName = eobject.getAttributeValue("id");
            dbobj.japaneseName = eobject.getChildText("japname");
            dbobj.className = eobject.getChildText("classname");
            dbobj.name = eobject.getChildText("name");
            dbobj.notes = eobject.getChildText("notes");
            
            Element attributes = eobject.getChild("attributes");
            dbobj.games = attributes.getAttributeValue("games");
            dbobj.list = attributes.getAttributeValue("list");
            dbobj.category = attributes.getAttributeValue("category");
            
            for (Element eproperty : eobject.getChild("properties").getChildren("property")) {
                DbProperty dbprop = new DbProperty();
                dbprop.name = eproperty.getAttributeValue("id");
                dbprop.type = eproperty.getAttributeValue("type");
                dbprop.description = eproperty.getChildText("description");
                
                for (Element evalue : eproperty.getChild("values").getChildren("value")) {
                    dbprop.values.put(
                            Integer.valueOf(evalue.getAttributeValue("id")),
                            evalue.getText()
                    );
                }
                
                dbobj.properties.put(dbprop.name, dbprop);
            }
            
            OBJECTS.put(dbobj.internalName, dbobj);
        }
    }
    
    public static final class DbProperty {
        public String name, type, description;
        public HashMap<Integer, String> values;
        
        private DbProperty() {
            values = new LinkedHashMap();
        }
        
        @Override
        public String toString() {
            return description;
        }
    }
    
    public static final class DbObject {
        public String internalName, japaneseName, className, name, notes;
        public String games, list, category;
        public HashMap<String, DbProperty> properties;
        public List<String> required;
        
        private DbObject() {
            properties = new LinkedHashMap();
            required = new ArrayList();
        }
        
        @Override
        public String toString() {
            return name + " (" + internalName + ')';
        }
        
        public boolean existsForGame(String game) {
            if (games.equals("both"))
                return true;
            return games.equals(game);
        }
        
        public String getFieldName(int i) {
            String fieldkey = "Obj_arg" + i;
            
            if (properties.containsKey(fieldkey))
                return properties.get(fieldkey).toString();
            return fieldkey;
        }
    }
}
