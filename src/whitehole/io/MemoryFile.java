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
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

public class MemoryFile implements FileBase {
    private byte[] buffer;
    private boolean isBigEndian;
    private int curPosition, logicalSize;
    
    public MemoryFile(byte[] buf) throws IOException {
        buffer = buf;
        isBigEndian = false;
        curPosition = 0;
        logicalSize = buffer.length;
    }
    
    @Override
    public void save() throws IOException {}
    
    @Override
    public void close() throws IOException {}
    
    @Override
    public void setBigEndian(boolean bigendian) {
        isBigEndian = bigendian;
    }
    
    @Override
    public int getLength() throws IOException {
        return logicalSize;
    }
    
    @Override
    public void setLength(int length) throws IOException {
        resizeBuffer((int)length);
        logicalSize = (int)length;
    }
    
    @Override
    public int position() throws IOException {
        return curPosition;
    }
    
    @Override
    public void position(int newpos) throws IOException {
        curPosition = (int)newpos;
    }
    
    @Override
    public void skip(int nbytes) throws IOException {
        curPosition += nbytes;
    }
    
    @Override
    public byte readByte() throws IOException {
        if (curPosition + 1 > logicalSize)
            return 0;
        return buffer[curPosition++];
    }
    
    @Override
    public short readShort() throws IOException {
        if (curPosition + 2 > logicalSize) return 0;
        if (isBigEndian)
            return (short)(((buffer[curPosition++] & 0xFF) << 8) | 
                    (buffer[curPosition++] & 0xFF));
        else
            return (short)((buffer[curPosition++] & 0xFF) | 
                    ((buffer[curPosition++] & 0xFF) << 8));
    }
    
    @Override
    public int readInt() throws IOException {
        if (curPosition + 4 > logicalSize)
            return 0;
        if (isBigEndian)
            return (int)(((buffer[curPosition++] & 0xFF) << 24) | 
                    ((buffer[curPosition++] & 0xFF) << 16) | 
                    ((buffer[curPosition++] & 0xFF) << 8) | 
                    (buffer[curPosition++] & 0xFF));
        else
            return (int)((buffer[curPosition++] & 0xFF) | 
                    ((buffer[curPosition++] & 0xFF) << 8) | 
                    ((buffer[curPosition++] & 0xFF) << 16) | 
                    ((buffer[curPosition++] & 0xFF) << 24));
    }
    
    @Override
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }
    
    @Override
    public String readString(String encoding, int length) throws IOException {
        if (!Charset.isSupported(encoding)) encoding = "ASCII";
        Charset charset = Charset.forName(encoding);
        CharsetDecoder dec = charset.newDecoder();
        ByteBuffer bin = ByteBuffer.wrap(buffer);
        CharBuffer bout = CharBuffer.allocate(1);
        String ret = "";
        
        bin.position(curPosition);
        for (int i = 0; length == 0 || i < length; i++)
        {
            CoderResult res = dec.decode(bin, bout, false);
            if (res != CoderResult.OVERFLOW)
                break;
            
            char ch = bout.get(0);
            if (ch == '\0') break;
            
            bout.clear();
            ret += ch;
        }
        
        return ret;
    }
    
    @Override
    public byte[] readBytes(int length) throws IOException {
        byte[] ret = new byte[length];
        for (int i = 0; i < length; i++)
            ret[i] = buffer[curPosition++];
        return ret;
    }
    
    @Override
    public void writeByte(byte val) throws IOException {
        autoExpand(curPosition + 1);
        buffer[curPosition++] = val;
    }

    @Override
    public void writeShort(short val) throws IOException {
        autoExpand(curPosition + 2);
        if (isBigEndian) {
            buffer[curPosition++] = (byte)((val >>> 8) & 0xFF);
            buffer[curPosition++] = (byte)(val & 0xFF);
        }
        else
        {
            buffer[curPosition++] = (byte)(val & 0xFF);
            buffer[curPosition++] = (byte)((val >>> 8) & 0xFF);
        }
    }

    @Override
    public void writeInt(int val) throws IOException {
        autoExpand(curPosition + 4);
        if (isBigEndian) {
            buffer[curPosition++] = (byte)((val >>> 24) & 0xFF);
            buffer[curPosition++] = (byte)((val >>> 16) & 0xFF);
            buffer[curPosition++] = (byte)((val >>> 8) & 0xFF);
            buffer[curPosition++] = (byte)(val & 0xFF);
        }
        else {
            buffer[curPosition++] = (byte)(val & 0xFF);
            buffer[curPosition++] = (byte)((val >>> 8) & 0xFF);
            buffer[curPosition++] = (byte)((val >>> 16) & 0xFF);
            buffer[curPosition++] = (byte)((val >>> 24) & 0xFF);
        }
    }
    
    @Override
    public void writeFloat(float val) throws IOException {
        writeInt(Float.floatToIntBits(val));
    }
    
    @Override
    public int writeString(String encoding, String val, int length) throws IOException {
        if (!Charset.isSupported(encoding)) encoding = "ASCII";
        Charset charset = Charset.forName(encoding);
        CharsetEncoder enc = charset.newEncoder();
        CharBuffer bin = CharBuffer.allocate(1);
        ByteBuffer bout = ByteBuffer.allocate(8);
        int len = 0;
        
        for (int i = 0; i < ((length > 0) ? length : val.length()); i++)
        {
            bin.put(val.charAt(i));
            bin.rewind();
            CoderResult res = enc.encode(bin, bout, false);
            if (res != CoderResult.UNDERFLOW)
                throw new IOException("Error while writing string");
            
            int bytesize = bout.position();
            len += bytesize;
            autoExpand(curPosition + bytesize);
            for (int j = 0; j < bytesize; j++)
                buffer[curPosition++] = bout.get(j);
            
            bin.clear();
            bout.clear();
        }
        
        if (length == 0 || val.length() < length)
        {
            bin.put('\0');
            bin.rewind();
            CoderResult res = enc.encode(bin, bout, false);
            if (res != CoderResult.UNDERFLOW)
                throw new IOException("Error while writing string");
            
            int bytesize = bout.position();
            len += bytesize;
            autoExpand(curPosition + bytesize);
            for (int j = 0; j < bytesize; j++)
                buffer[curPosition++] = bout.get(j);
        }
        
        return len;
    }
    
    @Override
    public void writeBytes(byte[] stuff) throws IOException {
        autoExpand(curPosition + stuff.length);
        for (byte b : stuff)
            buffer[curPosition++] = b;
    }
    
    @Override
    public byte[] getContents() {
        return buffer;
    }
    
    @Override
    public void setContents(byte[] buf) {
        buffer = buf;
    }
    
    private void resizeBuffer(int newsize) {
        byte[] newbuf = new byte[newsize];
        if (newsize > 0 && buffer.length > 0)
            System.arraycopy(buffer, 0, newbuf, 0, Math.min(newsize, buffer.length));
        buffer = newbuf;
    }
    
    private void autoExpand(int newend) {
        if (logicalSize < newend) logicalSize = newend;
        if (buffer.length < newend) resizeBuffer((buffer.length > 0) ? buffer.length * 2 : newend);
    }
}
