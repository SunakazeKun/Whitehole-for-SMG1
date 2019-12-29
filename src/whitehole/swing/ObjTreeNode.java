/*
    © 2012 - 2019 - Whitehole Team

    Whitehole is free software: you can redistribute it and/or modify it under
    the terms of the GNU General Public License as published by the Free
    Software Foundation, either version 3 of the License, or (at your option)
    any later version.

    Whitehole is distributed in the hope that it will be useful, but WITHOUT ANY 
    WARRANTY; See the GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along 
    with Whitehole. If not, see http://www.gnu.org/licenses/.
*/

package whitehole.swing;

import whitehole.smg.object.TObject;
import whitehole.smg.object.PathPointObj;

public class ObjTreeNode extends SimpleTreeNode {
    public Object object, userObject;
    public int uniqueID;
    
    public ObjTreeNode() {
        this.object = null;
        this.userObject = null;
        this.uniqueID = -1;
    }
    
    public ObjTreeNode(TObject obj) {
        this.object = obj;
        this.userObject = null;
        this.uniqueID = obj.uniqueID;
    }
    
    public ObjTreeNode(PathPointObj obj) {
        this.object = obj;
        this.userObject = null;
        this.uniqueID = obj.uniqueID;
    }
    
    @Override
    public String toString() {
        if (userObject != null)
            return userObject.toString();
        else if (object != null)
            return object.toString();
        else
            return "unknown node";
    }
    
    @Override
    public void setUserObject(Object object) {
        userObject = object;
    }
}