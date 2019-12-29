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

import whitehole.smg.object.ObjFile;
import whitehole.smg.object.StartObj;
import whitehole.smg.object.CameraObj;
import whitehole.smg.object.DebugObj;
import whitehole.smg.object.SoundObj;
import whitehole.smg.object.PathObj;
import whitehole.smg.object.CutsceneObj;
import whitehole.smg.object.MapPartObj;
import whitehole.smg.object.StageObj;
import whitehole.smg.object.GravityObj;
import whitehole.smg.object.ChildObj;
import whitehole.smg.object.AreaObj;
import whitehole.smg.object.LevelObj;
import whitehole.smg.object.PositionObj;
import whitehole.smg.object.TObject;
import whitehole.io.ExternalFilesystem;
import whitehole.io.RarcFilesystem;
import java.io.*;
import java.util.*;

public class ZoneArchive {
    public GameArchive game;
    public ExternalFilesystem filesystem;
    public String zoneName;
    
    public RarcFilesystem archive;
    public HashMap<String, List<TObject>> objects;
    public HashMap<String, List<StageObj>> zones;
    public List<PathObj> paths;
    
    public ZoneArchive(GameArchive arc, String name) throws IOException {
        game = arc;
        filesystem = game.filesystem;
        zoneName = name;
        
        loadZone();
    }
    
    private void loadZone() throws IOException {
        objects = new HashMap();
        zones = new HashMap();
        archive = new RarcFilesystem(filesystem.openFile("StageData/" + zoneName + ".arc"));
        loadObjects(ObjFile.Obj);
        loadObjects(ObjFile.MapParts);
        loadObjects(ObjFile.ChildObj);
        loadObjects(ObjFile.AreaObj);
        loadObjects(ObjFile.CameraCube);
        loadObjects(ObjFile.PlanetObj);
        loadObjects(ObjFile.DemoObj);
        loadObjects(ObjFile.Sound);
        loadObjects(ObjFile.Start);
        loadObjects(ObjFile.GeneralPos);
        loadObjects(ObjFile.DebugMove);
        loadObjects(ObjFile.StageObj);
        loadPaths();
    }
    
    private void loadObjects(ObjFile filedesc) {
        List<String> layers = archive.getDirectories("/Stage/Jmp/" + filedesc.getDirectory());
        
        if (layers == null)
            return;
        
        for (String layer : layers)
            addObjectsToList(filedesc, layer.toLowerCase());
    }

    public void save() throws IOException {
        saveObjects(ObjFile.Obj);
        saveObjects(ObjFile.MapParts);
        saveObjects(ObjFile.ChildObj);
        saveObjects(ObjFile.AreaObj);
        saveObjects(ObjFile.CameraCube);
        saveObjects(ObjFile.PlanetObj);
        saveObjects(ObjFile.DemoObj);
        saveObjects(ObjFile.Sound);
        saveObjects(ObjFile.Start);
        saveObjects(ObjFile.GeneralPos);
        saveObjects(ObjFile.DebugMove);
        savePaths();
        
        archive.save();
    }
    
    private void saveObjects(ObjFile filedesc) {
        List<String> layers = archive.getDirectories("/Stage/Jmp/" + filedesc.getDirectory());
        
        if (layers == null)
            return;
        
        for (String layer : layers)
            saveObjectList(filedesc, layer.toLowerCase());
    }
    
    public void close() throws IOException {
        archive.close();
    }
 
    private void addObjectsToList(ObjFile filedesc, String layer) {
        if (!objects.containsKey(layer))
            objects.put(layer, new ArrayList());
        
        if (!zones.containsKey(layer))
            zones.put(layer, new ArrayList());
        
        try {
            String path = filedesc.getPath(layer);
            if (!archive.fileExists(filedesc.getPath(layer)))
                return;
            Bcsv bcsv = new Bcsv(archive.openFile(path));
            
            for (Bcsv.Entry entry : bcsv.entries) {
                switch(filedesc) {
                    case StageObj:   zones.get(layer).add(new StageObj(this, layer, entry)); break;
                    case MapParts:   objects.get(layer).add(new MapPartObj(this, layer, entry)); break;
                    case ChildObj:   objects.get(layer).add(new ChildObj(this, layer, entry)); break;
                    case Obj:        objects.get(layer).add(new LevelObj(this, layer, entry)); break;
                    case Start:      objects.get(layer).add(new StartObj(this, layer, entry)); break;
                    case PlanetObj:  objects.get(layer).add(new GravityObj(this, layer, entry)); break;
                    case Sound:      objects.get(layer).add(new SoundObj(this, layer, entry)); break;
                    case AreaObj:    objects.get(layer).add(new AreaObj(this, layer, entry)); break;
                    case CameraCube: objects.get(layer).add(new CameraObj(this, layer, entry)); break;
                    case DemoObj:    objects.get(layer).add(new CutsceneObj(this, layer, entry)); break;
                    case GeneralPos: objects.get(layer).add(new PositionObj(this, layer, entry)); break;
                    case DebugMove:  objects.get(layer).add(new DebugObj(this, layer, entry)); break;
                }
            }
            
            bcsv.close();
            
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    private void saveObjectList(ObjFile filedesc, String layer) {
        if (!objects.containsKey(layer))
            return;
        
        try {
            Bcsv bcsv = new Bcsv(archive.openFile(filedesc.getPath(layer)));
            bcsv.entries.clear();
            
            for (TObject obj : objects.get(layer)) {
                if (obj.getFileInfo() != filedesc)
                    continue;
                
                obj.save();
                bcsv.entries.add(obj.data);
            }
            
            bcsv.save();
            bcsv.close();
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    private void loadPaths() {
        try {
            Bcsv bcsv = new Bcsv(archive.openFile("/Stage/jmp/Path/CommonPathInfo"));
            paths = new ArrayList<>(bcsv.entries.size());
            for (Bcsv.Entry entry : bcsv.entries)
                paths.add(new PathObj(this, entry));
            bcsv.close();
        }
        catch (IOException ex) {
            System.out.println(zoneName+": Failed to load paths: "+ex.getMessage());
        }
    }
    
    private void savePaths() {
        try {
            Bcsv bcsv = new Bcsv(archive.openFile("/Stage/jmp/Path/CommonPathInfo"));

            bcsv.entries.clear();
            for (PathObj pobj : paths) {
                pobj.save();
                bcsv.entries.add(pobj.data);
            }
            bcsv.save();
            bcsv.close();
        }
        catch (IOException ex) {
            System.out.println(zoneName+": Failed to save paths: "+ex.getMessage());
        }
    }
}
