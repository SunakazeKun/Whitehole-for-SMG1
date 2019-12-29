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

import java.io.IOException;
import whitehole.io.FileBase;

public class Bti {
    public Bti(FileBase f) throws IOException {
        file = f;
        file.setBigEndian(true);
        
        format = file.readByte();
        file.skip(0x1);
        width = file.readShort();
        height = file.readShort();
        wrapS = file.readByte();
        wrapT = file.readByte();
        file.skip(0x1);
        paletteFormat = file.readByte();
        paletteCount = file.readShort();
        paletteOffset = file.readInt();
        useMipmap = file.readByte() != 0x0;
        file.skip(0x3);
        minFilter = file.readByte();
        magFilter = file.readByte();
        minLod = file.readByte() * 0.125F;
        maxLod = file.readByte() * 0.125F;
        mipmapCount = file.readByte();
        file.skip(0x1);
        lodBias = file.readShort() * 0.01F;
        imageOffset = file.readInt();
        
        image = ImageUtils.decodeTextureData(file, imageOffset, mipmapCount, format, width, height);
    }
    
    public void save() throws IOException {
        file.save();
    }

    public void close() throws IOException {
        file.close();
    }
    
    private final FileBase file;
    
    public int paletteOffset, imageOffset;
    public float minLod, maxLod, lodBias;
    public boolean useMipmap;
    public short width, height, paletteCount;
    public byte format, paletteFormat, mipmapCount;
    public byte wrapS, wrapT, minFilter, magFilter;
    public byte[][] image;
}