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

package whitehole.vectors;

public class Color4 {
    public float r, g, b, a;
    
    public Color4() {
        r = g = b = a = 0f;
    }
    
    public Color4(Color4 copy) {
        r = copy.r;
        g = copy.g;
        b = copy.b;
        a = copy.a;
    }
    
    public Color4(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        a = 1f;
    }
    
    public Color4(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    
    @Override
    public String toString() {
        return r + " | " + g + " | " + b + " | " + a;
    }
}