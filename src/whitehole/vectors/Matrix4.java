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

public class Matrix4 {
    public float[] matrix;
    
    public Matrix4() {
        matrix = new float[16];
        matrix[0] = 1f; matrix[1] = 0f; matrix[2] = 0f; matrix[3] = 0f;
        matrix[4] = 0f; matrix[5] = 1f; matrix[6] = 0f; matrix[7] = 0f;
        matrix[8] = 0f; matrix[9] = 0f; matrix[10] = 1f; matrix[11] = 0f;
        matrix[12] = 0f; matrix[13] = 0f; matrix[14] = 0f; matrix[15] = 1f;
    }
    
    public Matrix4(float m0, float m1, float m2, float m3, 
            float m4, float m5, float m6, float m7,
            float m8, float m9, float m10, float m11,
            float m12, float m13, float m14, float m15) {
        matrix = new float[16];
        matrix[0] = m0; matrix[1] = m1; matrix[2] = m2; matrix[3] = m3;
        matrix[4] = m4; matrix[5] = m5; matrix[6] = m6; matrix[7] = m7;
        matrix[8] = m8; matrix[9] = m9; matrix[10] = m10; matrix[11] = m11;
        matrix[12] = m12; matrix[13] = m13; matrix[14] = m14; matrix[15] = m15;
    }
    
    public static Matrix4 scale(float factor) {
        return new Matrix4(
                factor, 0f, 0f, 0f,
                0f, factor, 0f, 0f,
                0f, 0f, factor, 0f,
                0f, 0f, 0f, 1f);
    }
    
    public static Matrix4 scale(Vector3 factor) {
        return new Matrix4(
                factor.x, 0f, 0f, 0f,
                0f, factor.y, 0f, 0f,
                0f, 0f, factor.z, 0f,
                0f, 0f, 0f, 1f);
    }
    
    public static Matrix4 createRotationX(float angle) {
        float cos = (float)Math.cos(angle);
        float sin = (float)Math.sin(angle);
        
        return new Matrix4(
                1f, 0f, 0f, 0f,
                0f, cos, sin, 0f,
                0f, -sin, cos, 0f,
                0f, 0f, 0f, 1f);
    }
    
    public static Matrix4 createRotationY(float angle) {
        float cos = (float)Math.cos(angle);
        float sin = (float)Math.sin(angle);
        
        return new Matrix4(
                cos, 0f, -sin, 0f,
                0f, 1f, 0f, 0f,
                sin, 0f, cos, 0f,
                0f, 0f, 0f, 1f);
    }
    
    public static Matrix4 createRotationZ(float angle) {
        float cos = (float)Math.cos(angle);
        float sin = (float)Math.sin(angle);
        
        return new Matrix4(
                cos, sin, 0f, 0f,
                -sin, cos, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f);
    }
    
    public static Matrix4 createTranslation(Vector3 trans) {
        return new Matrix4(
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                trans.x, trans.y, trans.z, 1f);
    }
    
    public static Matrix4 SRTToMatrix(Vector3 scale, Vector3 rot, Vector3 trans) {
        Matrix4 ret = new Matrix4();

        Matrix4 mscale = Matrix4.scale(scale);
        Matrix4 mxrot = Matrix4.createRotationX(rot.x);
        Matrix4 myrot = Matrix4.createRotationY(rot.y);
        Matrix4 mzrot = Matrix4.createRotationZ(rot.z);
        Matrix4 mtrans = Matrix4.createTranslation(trans);

        Matrix4.mult(ret, mscale, ret);
        Matrix4.mult(ret, mxrot, ret);
        Matrix4.mult(ret, myrot, ret);
        Matrix4.mult(ret, mzrot, ret);
        Matrix4.mult(ret, mtrans, ret);

        return ret;
    }
    
    public static void mult(Matrix4 left, Matrix4 right, Matrix4 out) {
        float m0 = left.matrix[0] * right.matrix[0] + left.matrix[1] * right.matrix[4] + left.matrix[2] * right.matrix[8] + left.matrix[3] * right.matrix[12],
              m1 = left.matrix[0] * right.matrix[1] + left.matrix[1] * right.matrix[5] + left.matrix[2] * right.matrix[9] + left.matrix[3] * right.matrix[13],
              m2 = left.matrix[0] * right.matrix[2] + left.matrix[1] * right.matrix[6] + left.matrix[2] * right.matrix[10] + left.matrix[3] * right.matrix[14],
              m3 = left.matrix[0] * right.matrix[3] + left.matrix[1] * right.matrix[7] + left.matrix[2] * right.matrix[11] + left.matrix[3] * right.matrix[15],
                
              m4 = left.matrix[4] * right.matrix[0] + left.matrix[5] * right.matrix[4] + left.matrix[6] * right.matrix[8] + left.matrix[7] * right.matrix[12],
              m5 = left.matrix[4] * right.matrix[1] + left.matrix[5] * right.matrix[5] + left.matrix[6] * right.matrix[9] + left.matrix[7] * right.matrix[13],
              m6 = left.matrix[4] * right.matrix[2] + left.matrix[5] * right.matrix[6] + left.matrix[6] * right.matrix[10] + left.matrix[7] * right.matrix[14],
              m7 = left.matrix[4] * right.matrix[3] + left.matrix[5] * right.matrix[7] + left.matrix[6] * right.matrix[11] + left.matrix[7] * right.matrix[15],
                
              m8 = left.matrix[8] * right.matrix[0] + left.matrix[9] * right.matrix[4] + left.matrix[10] * right.matrix[8] + left.matrix[11] * right.matrix[12],
              m9 = left.matrix[8] * right.matrix[1] + left.matrix[9] * right.matrix[5] + left.matrix[10] * right.matrix[9] + left.matrix[11] * right.matrix[13],
              m10 = left.matrix[8] * right.matrix[2] + left.matrix[9] * right.matrix[6] + left.matrix[10] * right.matrix[10] + left.matrix[11] * right.matrix[14],
              m11 = left.matrix[8] * right.matrix[3] + left.matrix[9] * right.matrix[7] + left.matrix[10] * right.matrix[11] + left.matrix[11] * right.matrix[15],
                
              m12 = left.matrix[12] * right.matrix[0] + left.matrix[13] * right.matrix[4] + left.matrix[14] * right.matrix[8] + left.matrix[15] * right.matrix[12],
              m13 = left.matrix[12] * right.matrix[1] + left.matrix[13] * right.matrix[5] + left.matrix[14] * right.matrix[9] + left.matrix[15] * right.matrix[13],
              m14 = left.matrix[12] * right.matrix[2] + left.matrix[13] * right.matrix[6] + left.matrix[14] * right.matrix[10] + left.matrix[15] * right.matrix[14],
              m15 = left.matrix[12] * right.matrix[3] + left.matrix[13] * right.matrix[7] + left.matrix[14] * right.matrix[11] + left.matrix[15] * right.matrix[15];
        
        out.matrix[0] = m0; out.matrix[1] = m1; out.matrix[2] = m2; out.matrix[3] = m3;
        out.matrix[4] = m4; out.matrix[5] = m5; out.matrix[6] = m6; out.matrix[7] = m7;
        out.matrix[8] = m8; out.matrix[9] = m9; out.matrix[10] = m10; out.matrix[11] = m11;
        out.matrix[12] = m12; out.matrix[13] = m13; out.matrix[14] = m14; out.matrix[15] = m15;
    }
    
    
    public static Matrix4 lookAt(Vector3 eye, Vector3 target, Vector3 up) {
        Vector3 z = new Vector3(); Vector3.subtract(eye, target, z); Vector3.normalize(z, z);
        Vector3 x = new Vector3(); Vector3.cross(up, z, x); Vector3.normalize(x, x);
        Vector3 y = new Vector3(); Vector3.cross(z, x, y); Vector3.normalize(y, y);
        
        Matrix4 rot = new Matrix4(
                x.x, y.x, z.x, 0f,
                x.y, y.y, z.y, 0f,
                x.z, y.z, z.z, 0f,
                0f, 0f, 0f, 1f);
        Matrix4 trans = Matrix4.createTranslation(new Vector3(-eye.x, -eye.y, -eye.z));
        
        Matrix4.mult(trans, rot, trans);
        return trans;
    }
    
    // taken from OpenTK
    public static Matrix4 invert(Matrix4 mat) {
        int[] colIdx = { 0, 0, 0, 0 };
        int[] rowIdx = { 0, 0, 0, 0 };
        int[] pivotIdx = { -1, -1, -1, -1 };

        // convert the matrix to an array for easy looping
        Matrix4 inverse = new Matrix4(
                mat.matrix[0], mat.matrix[1], mat.matrix[2], mat.matrix[3],
                mat.matrix[4], mat.matrix[5], mat.matrix[6], mat.matrix[7],
                mat.matrix[8], mat.matrix[9], mat.matrix[10], mat.matrix[11],
                mat.matrix[12], mat.matrix[13], mat.matrix[14], mat.matrix[15]);
        
        int icol = 0;
        int irow = 0;
        for (int i = 0; i < 4; i++) {
            // Find the largest pivot value
            float maxPivot = 0.0f;
            for (int j = 0; j < 4; j++) {
                if (pivotIdx[j] != 0) {
                    for (int k = 0; k < 4; ++k) {
                        if (pivotIdx[k] == -1) {
                            float absVal = Math.abs(inverse.matrix[j*4 + k]);
                            if (absVal > maxPivot) {
                                maxPivot = absVal;
                                irow = j;
                                icol = k;
                            }
                        }
                        else if (pivotIdx[k] > 0) {
                            return mat;
                        }
                    }
                }
            }

            ++(pivotIdx[icol]);

            // Swap rows over so pivot is on diagonal
            if (irow != icol) {
                for (int k = 0; k < 4; ++k) {
                    float f = inverse.matrix[irow*4 + k];
                    inverse.matrix[irow*4 + k] = inverse.matrix[icol*4 + k];
                    inverse.matrix[icol*4 + k] = f;
                }
            }

            rowIdx[i] = irow;
            colIdx[i] = icol;

            float pivot = inverse.matrix[icol*4 + icol];
            // check for singular matrix
            if (pivot == 0.0f) {
                throw new IllegalArgumentException("Matrix is singular and cannot be inverted.");
            }

            // Scale row so it has a unit diagonal
            float oneOverPivot = 1.0f / pivot;
            inverse.matrix[icol*4 + icol] = 1.0f;
            for (int k = 0; k < 4; ++k)
                inverse.matrix[icol*4 + k] *= oneOverPivot;

            // Do elimination of non-diagonal elements
            for (int j = 0; j < 4; ++j) {
                // check this isn't on the diagonal
                if (icol != j) {
                    float f = inverse.matrix[j*4 + icol];
                    inverse.matrix[j*4 + icol] = 0.0f;
                    for (int k = 0; k < 4; ++k)
                        inverse.matrix[j*4 + k] -= inverse.matrix[icol*4 + k] * f;
                }
            }
        }

        for (int j = 3; j >= 0; --j) {
            int ir = rowIdx[j];
            int ic = colIdx[j];
            for (int k = 0; k < 4; ++k)
            {
                float f = inverse.matrix[k*4 + ir];
                inverse.matrix[k*4 + ir] = inverse.matrix[k*4 + ic];
                inverse.matrix[k*4 + ic] = f;
            }
        }

        return inverse;
    }
}