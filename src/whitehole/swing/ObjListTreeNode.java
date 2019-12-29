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

import whitehole.util.AdaptedEnumeration;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import whitehole.smg.object.TObject;
import whitehole.smg.object.PathObj;
import whitehole.smg.object.PathPointObj;

public class ObjListTreeNode extends ObjTreeNode {
    public final LinkedHashMap<Integer, TreeNode> children;
    
    public ObjListTreeNode() {
        children = new LinkedHashMap();
        object = null;
    }
    
    public ObjListTreeNode(PathObj obj) {
        object = obj;
        uniqueID = obj.uniqueID;
        children = new LinkedHashMap();
        
        for (PathPointObj ptobj : obj.points.values())
            addObject(ptobj);
    }
    
    @Override
    public void remove(int index) {
        int key = (int)children.keySet().toArray()[index];
        children.remove(key);
    }
    
    @Override
    public void remove(MutableTreeNode node) {
        children.remove(((ObjTreeNode)node).uniqueID);
    }
    
    @Override
    public TreeNode getChildAt(int childIndex) {
        return (TreeNode)children.values().toArray()[childIndex];
    }
    
    @Override
    public int getChildCount() {
        return children.size();
    }
    
    @Override
    public int getIndex(TreeNode node) {
        int uid = ((ObjTreeNode)node).uniqueID;
        int i = 0;
        for (TreeNode tn : children.values()) {
            if (((ObjTreeNode)tn).uniqueID == uid)
                return i;
            
            i++;
        }
        
        return -1;
    }
    
    @Override
    public boolean getAllowsChildren() {
        return true;
    }
    
    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }
    
    @Override
    public Enumeration children() {
        return new AdaptedEnumeration(children.values().iterator());
    }
    
    public TreeNode addObject(TObject obj) {
        ObjTreeNode tn = new ObjTreeNode(obj);
        children.put(obj.uniqueID, tn);
        tn.setParent(this);
        return tn;
    }
    
    public TreeNode addObject(PathObj obj) {
        ObjListTreeNode tn = new ObjListTreeNode(obj);
        children.put(obj.uniqueID, tn);
        tn.setParent(this);
        return tn;
    }
}
