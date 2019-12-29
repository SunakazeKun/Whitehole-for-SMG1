package whitehole.swing;

import java.util.Enumeration;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public abstract class SimpleTreeNode implements MutableTreeNode {
    protected TreeNode parent;
    
    @Override
    public void insert(MutableTreeNode child, int index) {}

    @Override
    public void remove(int index) {}

    @Override
    public void remove(MutableTreeNode node) {}

    @Override
    public void setUserObject(Object object) {}

    @Override
    public void removeFromParent() {
        parent = null;
    }

    @Override
    public void setParent(MutableTreeNode newParent) {
        parent = newParent;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return null;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return -1;
    }

    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public Enumeration children() {
        return null;
    }
}
