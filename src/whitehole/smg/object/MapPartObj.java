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

public class MapPartObj extends TObject {
    public MapPartObj(ZoneArchive zone, String layer, Bcsv.Entry entry) {
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
    
    public MapPartObj(ZoneArchive zone, String layer, String objname, Vector3 pos) {
        this.zone = zone;
        this.layer = layer;
        
        renderer = null;
        uniqueID = -1;
        
        name = objname;
        position = pos;
        rotation = new Vector3(0f, 0f, 0f);
        scale = new Vector3(1f, 1f, 1f);
        data = new Bcsv.Entry();
        
        data.put("name", name);
        data.put("l_id", 0);
        data.put("MoveConditionType", 0);
        data.put("RotateSpeed", 0);
        data.put("RotateAngle", 0);
        data.put("RotateAxis", 0);
        data.put("RotateAccelType", 0);
        data.put("RotateStopTime", 0);
        data.put("RotateType", 0);
        data.put("ShadowType", 0);
        data.put("SignMotionType", 0);
        data.put("PressType", 0);
        data.put("CameraSetId", -1);
        data.put("FarClip", -1);
        data.put("Obj_arg0", -1);
        data.put("Obj_arg1", -1);
        data.put("Obj_arg2", -1);
        data.put("Obj_arg3", -1);
        data.put("SW_APPEAR", -1);
        data.put("SW_DEAD", -1);
        data.put("SW_A",  -1);
        data.put("SW_B", -1);
        data.put("SW_SLEEP", -1);
        data.put("pos_x", position.x);
        data.put("pos_y", position.y);
        data.put("pos_z", position.z);
        data.put("dir_x", rotation.x);
        data.put("dir_y", rotation.y);
        data.put("dir_z", rotation.z);
        data.put("scale_x", scale.x);
        data.put("scale_y", scale.y);
        data.put("scale_z", scale.z);
        data.put("CastId", -1);
        data.put("ViewGroupId", -1);
        data.put("ShapeModelNo", (short)-1);
        data.put("CommonPath_ID", (short)-1);
        data.put("ClippingGroupId", (short)-1);
        data.put("GroupId", (short)-1);
        data.put("DemoGroupId", (short)-1);
        
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
        panel.addField("ShapeModelNo", "Model No.", "int", null, data.get("ShapeModelNo"), "Default");
        panel.addField("CommonPath_ID", "Path ID", "int", null, data.get("CommonPath_ID"), "Default");
        panel.addField("CameraSetId", "Camera ID", "int", null, data.get("CameraSetId"), "Default");
        
        panel.addCategory("obj_mappartsinfo", "MapPart");
        panel.addField("MoveConditionType", "MoveConditionType", "int", null, data.get("MoveConditionType"), "Default");
        panel.addField("RotateSpeed", "RotateSpeed", "int", null, data.get("RotateSpeed"), "Default");
        panel.addField("RotateAngle", "RotateAngle", "int", null, data.get("RotateAngle"), "Default");
        panel.addField("RotateAxis", "RotateAxis", "int", null, data.get("RotateAxis"), "Default");
        panel.addField("RotateAccelType", "RotateAccelType", "int", null, data.get("RotateAccelType"), "Default");
        panel.addField("RotateStopTime", "RotateStopTime", "int", null, data.get("RotateStopTime"), "Default");
        panel.addField("RotateType", "RotateType", "int", null, data.get("RotateType"), "Default");
        panel.addField("ShadowType", "ShadowType", "int", null, data.get("ShadowType"), "Default");
        panel.addField("SignMotionType", "SignMotionType", "int", null, data.get("SignMotionType"), "Default");
        panel.addField("PressType", "PressType", "int", null, data.get("PressType"), "Default");
        panel.addField("FarClip", "FarClip", "int", null, data.get("FarClip"), "Default");
        
        panel.addCategory("obj_args", "Arguments");
        panel.addField("Obj_arg0", getFieldName(0), "int", null, data.get("Obj_arg0"), "Default");
        panel.addField("Obj_arg1", getFieldName(1), "int", null, data.get("Obj_arg1"), "Default");
        panel.addField("Obj_arg2", getFieldName(2), "int", null, data.get("Obj_arg2"), "Default");
        panel.addField("Obj_arg3", getFieldName(3), "int", null, data.get("Obj_arg3"), "Default");
        
        panel.addCategory("obj_switches", "Switches");
        panel.addField("SW_APPEAR", "SW_APPEAR", "int", null, data.get("SW_APPEAR"), "Default");
        panel.addField("SW_DEAD", "SW_DEAD", "int", null, data.get("SW_DEAD"), "Default");
        panel.addField("SW_A", "SW_A", "int", null, data.get("SW_A"), "Default");
        panel.addField("SW_B", "SW_B", "int", null, data.get("SW_B"), "Default");
        panel.addField("SW_SLEEP", "SW_SLEEP", "int", null, data.get("SW_SLEEP"), "Default");
        
        panel.addCategory("obj_groups", "Groups");
        panel.addField("GroupId", "Group ID", "int", null, data.get("GroupId"), "Default");
        panel.addField("ViewGroupId", "View Group ID", "int", null, data.get("ViewGroupId"), "Default");
        panel.addField("ClippingGroupId", "Clipping Group ID", "int", null, data.get("ClippingGroupId"), "Default");
        panel.addField("DemoGroupId", "Cutscene Group ID", "int", null, data.get("DemoGroupId"), "Default");
        panel.addField("CastId", "Cutscene Cast ID", "int", null, data.get("CastId"), "Default");
    }
    
    public ObjFile getFileInfo() {
        return ObjFile.MapParts;
    }
    
    @Override
    public String toString() {
        return getDescription() + " [" + getLayerName() + "]";
    }
}