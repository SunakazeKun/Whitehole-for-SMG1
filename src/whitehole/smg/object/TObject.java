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
import whitehole.rendering.GLRenderer;
import whitehole.rendering.RendererCache;
import whitehole.smg.Bcsv;
import whitehole.smg.ZoneArchive;
import whitehole.vectors.Vector3;
import javax.media.opengl.*;
import whitehole.ObjectDB;
import whitehole.ObjectDB.DbObject;

public abstract class TObject {
    public int uniqueID;
    public String name, layer, oldname;
    public Bcsv.Entry data;
    public DbObject dbInfo;
    public Vector3 position, rotation, scale;
    public GLRenderer renderer;
    public ZoneArchive zone;
    
    protected final String getLayerName() {
        return layer.equals("common") ? "Common" : "Layer" + layer.substring(5).toUpperCase();
    }
    
    public final void loadDBInfo() {
        if (ObjectDB.OBJECTS.containsKey(name))
            dbInfo = ObjectDB.OBJECTS.get(name);
        else
            dbInfo = null;
    }
    
    public final String getDescription() {
        return dbInfo == null ? name : dbInfo.name;
    }
    
    public final String getFieldName(int arg) {
        return dbInfo == null ? "Obj_arg" + arg : dbInfo.getFieldName(arg);
    }
    
    public void initRenderer(GLRenderer.RenderInfo info) {
        if (renderer != null)
            return;
        
        renderer = RendererCache.getObjectRenderer(info, this);
        renderer.compileDisplayLists(info);
        renderer.releaseStorage();
    }
    
    public void closeRenderer(GLRenderer.RenderInfo info) {
        if (renderer == null)
            return;
        
        RendererCache.closeObjectRenderer(info, this);
        renderer = null;
    }
    
    public void render(GLRenderer.RenderInfo info) {
        GL2 gl = info.drawable.getGL().getGL2();
        
        gl.glPushMatrix();
        
        gl.glTranslatef(position.x, position.y, position.z);
        gl.glRotatef(rotation.z, 0f, 0f, 1f);
        gl.glRotatef(rotation.y, 0f, 1f, 0f);
        gl.glRotatef(rotation.x, 1f, 0f, 0f);
        if (renderer.isScaled())
            gl.glScalef(scale.x, scale.y, scale.z);
        
        gl.glCallList(renderer.displayLists[info.renderMode.ordinal()]);
        gl.glPopMatrix();
    }
    
    public abstract void save();
    public abstract void getProperties(PropertyGrid panel);
    public abstract ObjFile getFileInfo();
    
    @Override
    public String toString() {
        return "LevelObject";
    }
}