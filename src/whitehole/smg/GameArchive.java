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

import whitehole.io.ExternalFilesystem;
import java.io.IOException;

public class GameArchive {
    public ExternalFilesystem filesystem;
    
    public GameArchive(ExternalFilesystem f) {
        filesystem = f;
    }
    
    public boolean isValid() {
        return filesystem.fileExists("StageData/ObjNameTable.arc");
    }
    
    public boolean galaxyExists(String name) {
        return filesystem.fileExists("StageData/" + name + '/' + name + "Scenario.arc");
    }
    
    public GalaxyArchive openGalaxy(String name) throws IOException {
        if (!galaxyExists(name))
            return null;
        return new GalaxyArchive(this, name);
    }
}