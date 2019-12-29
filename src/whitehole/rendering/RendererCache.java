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

package whitehole.rendering;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import whitehole.Whitehole;
import whitehole.io.RarcFilesystem;
import whitehole.rendering.object.PlanetRenderer;
import whitehole.smg.Bcsv;
import whitehole.smg.object.TObject;
import javax.media.opengl.*;
import whitehole.vectors.Color4;

public class RendererCache {
    public static class CacheEntry {
        public GLRenderer renderer;
        public int refCount;
    }
    
    public static HashMap<String, CacheEntry> cache;
    public static List<String> planetList;
    public static GLContext refContext;
    public static int contextCount;
    
    public static void init() {
        cache = new HashMap();
        refContext = null;
        contextCount = 0;
        
        loadPlanetList();
    }
    
    public static void loadPlanetList() {
        if (planetList != null)
            return;
        
        planetList = new ArrayList();
        
        try {
            RarcFilesystem arc = new RarcFilesystem(Whitehole.game.filesystem.openFile("ObjectData/PlanetMapDataTable.arc"));
            Bcsv planetmap = new Bcsv(arc.openFile("/PlanetMapDataTable/PlanetMapDataTable.bcsv"));
            
            for (Bcsv.Entry entry : planetmap.entries) {
                if ((int)entry.get("WaterFlag") != 0)
                    planetList.add((String) entry.get("PlanetName"));
            }
            
            planetmap.close();
            arc.close();
        }
        catch (IOException ex) {}
    }
    
    public static GLRenderer getObjectRenderer(GLRenderer.RenderInfo info, TObject obj) {
        String modelname = Substitutor.substituteModelName(obj, obj.name);
        String key = Substitutor.substituteObjectKey(obj, "object_" + obj.name);
        
        CacheEntry entry;
        
        if (cache.containsKey(key)) {
            entry = cache.get(key);
            entry.refCount++;
        }
        else {
            entry = new CacheEntry();
            entry.refCount = 1;
            entry.renderer = Substitutor.substituteRenderer(obj, info);

            if (entry.renderer == null) {
                try {
                    if (planetList.contains(modelname))
                        entry.renderer = new PlanetRenderer(info, modelname);
                    else {
                        int modelno = (short)obj.data.getOrDefault("ShapeModelNo", (short)-1);

                        if (modelno >= 0)
                            modelname = String.format("%s%02d", modelname, modelno);

                        entry.renderer = new BmdRenderer(info, modelname);
                    }
                }
                catch (GLException ex) {
                    entry.renderer = null;
                    System.out.println(ex);
                }
            }
            
            cache.put(key, entry);
        }
        
        if (entry.renderer == null)
            entry.renderer = new ColorCubeRenderer(100F, new Color4(0.5F, 0.5F, 1F, 1F), new Color4(0F, 0F, 0.8F, 1F), true);
        
        return entry.renderer;
    }
    
    public static void closeObjectRenderer(GLRenderer.RenderInfo info, TObject obj) {
        String key = Substitutor.substituteObjectKey(obj, "object_" + obj.oldname);
        
        if (!cache.containsKey(key))
            return;
        
        CacheEntry entry = cache.get(key);
        entry.refCount--;
        
        if (entry.refCount < 1) {
            entry.renderer.close(info);
            cache.remove(key);
        }
    }
    
    public static void setRefContext(GLContext ctx) {
        if (refContext == null)
            refContext = ctx;
        
        contextCount++;
    }
    
    public static void clearRefContext() {
        contextCount--;
        
        if (contextCount < 1)
            refContext = null;
    }
}