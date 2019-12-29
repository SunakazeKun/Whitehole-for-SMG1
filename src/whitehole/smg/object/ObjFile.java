package whitehole.smg.object;

public enum ObjFile {
    Obj       ("Placement", "ObjInfo"),
    MapParts  ("MapParts", "MapPartsInfo"),
    ChildObj  ("ChildObj", "ChildObjInfo"),
    AreaObj   ("Placement", "AreaObjInfo"),
    CameraCube("Placement", "CameraCubeInfo"),
    DemoObj   ("Placement", "DemoObjInfo"),
    DebugMove ("Debug", "DebugMoveInfo"),
    PlanetObj ("Placement", "PlanetObjInfo"),
    GeneralPos("GeneralPos", "GeneralPosInfo"),
    Sound     ("Placement", "SoundInfo"),
    StageObj  ("Placement", "StageObjInfo"),
    Start     ("Start", "StartInfo");
    
    private final String dir;
    private final String file;
    
    private ObjFile(String dir, String file) {
        this.dir = dir;
        this.file = file;
    }
    
    public String getPath(String layer) {
        return "/Stage/Jmp/" + dir + '/' + layer + '/' + file;
    }
    
    public String getDirectory() {
        return dir;
    }
    
    public String getFile() {
        return file;
    }
}
