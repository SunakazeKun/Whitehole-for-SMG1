package whitehole.swing;

public class GalaxyTreeNode extends SimpleTreeNode {
    private final String file, name;
    
    public GalaxyTreeNode(String file, String name) {
        parent = null;
        this.file = file;
        this.name = name;
    }
    
    public String toString() {
        return name + " (" + file + ')';
    }
    
    public String getFile() {
        return file;
    }
    
    public String getName() {
        return name;
    }
}
