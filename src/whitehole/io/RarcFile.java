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

package whitehole.io;

import java.io.IOException;

public class RarcFile extends MemoryFile {
    public RarcFilesystem filesystem;
    public String fileName;
    
    public RarcFile(RarcFilesystem fs, String fullname) throws IOException {
        super(fs.getFileContents(fullname));
        
        filesystem = fs;
        fileName = fullname;
    }
    
    @Override
    public void save() throws IOException {
        filesystem.reinsertFile(this);
    }
}
