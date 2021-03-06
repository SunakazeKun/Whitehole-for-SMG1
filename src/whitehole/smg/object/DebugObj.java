/*
    © 2012 - 2017 - Whitehole Team

    Whitehole is free software: you can redistribute it and/or modify it under
    the terms of the GNU General Public License as published by the Free
    Software Foundation, either version 3 of the License, or (at your option)
    any later version.

    Whitehole is distributed in the hope that it will be useful, but WITHOUT ANY 
    WARRANTY; See the GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along 
    with Whitehole. If not, see http://www.gnu.org/licenses/.
*/

package whitehole.smg.object;

import whitehole.swing.PropertyGrid;
import whitehole.smg.Bcsv;
import whitehole.smg.ZoneArchive;
import whitehole.vectors.Vector3;

public class DebugObj extends TObject {
    public DebugObj(ZoneArchive zone, String layer, Bcsv.Entry entry) {
        this.zone = zone;
        this.layer = layer;
        
        renderer = null;
        uniqueID = -1;
        
        data = entry;
        name = (String)data.get("name");
        
        position = new Vector3((float)data.get("pos_x"), (float)data.get("pos_y"), (float)data.get("pos_z"));
        rotation = new Vector3((float)data.get("dir_x"), (float)data.get("dir_y"), (float)data.get("dir_z"));
        scale = new Vector3((float)data.get("scale_x"), (float)data.get("scale_y"), (float)data.get("scale_z"));
        
        loadDBInfo();
    }
    
    public DebugObj(ZoneArchive zone, String layer, String objname, Vector3 pos) {
        this.zone = zone;
        this.layer = layer;
        
        renderer = null;
        uniqueID = -1;
        
        name = "DebugMovePos";
        position = pos;
        rotation = new Vector3(0f, 0f, 0f);
        scale = new Vector3(1f, 1f, 1f);
        data = new Bcsv.Entry();
        
        data.put("name", name);
        data.put("l_id", 0);
        data.put("pos_x", position.x);
        data.put("pos_y", position.y);
        data.put("pos_z", position.z);
        data.put("dir_x", rotation.x);
        data.put("dir_y", rotation.y);
        data.put("dir_z", rotation.z);
        data.put("scale_x", scale.x);
        data.put("scale_y", scale.y);
        data.put("scale_z", scale.z);
        
        loadDBInfo();
    }
    
    @Override
    public void save() {
        data.put("name", name);
        data.put("pos_x", position.x);
        data.put("pos_y", position.y);
        data.put("pos_z", position.z);
        data.put("dir_x", rotation.x);
        data.put("dir_y", rotation.y);
        data.put("dir_z", rotation.z);
        data.put("scale_x", scale.x);
        data.put("scale_y", scale.y);
        data.put("scale_z", scale.z);
    }

    @Override
    public void getProperties(PropertyGrid panel) {
        panel.addCategory("obj_rendering", "Rendering");
        panel.addField("pos_x", "X position", "float", null, position.x, "Default");
        panel.addField("pos_y", "Y position", "float", null, position.y, "Default");
        panel.addField("pos_z", "Z position", "float", null, position.z, "Default");
        panel.addField("dir_x", "X rotation", "float", null, rotation.x, "Default");
        panel.addField("dir_y", "Y rotation", "float", null, rotation.y, "Default");
        panel.addField("dir_z", "Z rotation", "float", null, rotation.z, "Default");
        panel.addField("scale_x", "X scale", "float", null, scale.x, "Default");
        panel.addField("scale_y", "Y scale", "float", null, scale.y, "Default");
        panel.addField("scale_z", "Z scale", "float", null, scale.z, "Default");

        panel.addCategory("obj_settings", "Settings");
        panel.addField("l_id", "ID", "int", null, data.get("l_id"), "Default");
    }
    
    public ObjFile getFileInfo() {
        return ObjFile.DebugMove;
    }
    
    @Override
    public String toString() {
        return String.format("Debug No. %d + [%s]", data.get("l_id"), getLayerName());
    }
}