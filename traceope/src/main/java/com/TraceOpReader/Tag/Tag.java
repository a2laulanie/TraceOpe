package com.TraceOpReader.Tag;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Tag {
    private byte[] ID;
    private int Chunks;
    private ByteArrayOutputStream Content = new ByteArrayOutputStream();

    public Tag() {
    }

    public ByteArrayOutputStream getContent() {
        return Content;
    }

    public void setContent(byte[] content) throws IOException {
        Content = new ByteArrayOutputStream();
        Content.write(content);
    }

    public void addContent(byte[] buffer) throws IOException {
        Content.write(buffer);
    }

    public void flushContent() {
        this.Content = new ByteArrayOutputStream();
    }

    public byte[] getID() {
        return ID;
    }

    public void setID(byte[] id) {
        this.ID = id;
    }

    public String printTAGID() {
        String ret = "";
        for (int i = 0; i < this.ID.length; i++) {
            ret += Integer.toHexString(this.ID[i] & 0xFF);
        }
        return ret;
    }

    public int getChunks() {
        return Chunks;
    }

    public void setChunks(int chunks) {
        Chunks = chunks;
    }

    public String toString() {
        String ret = "";
        ret += "Tag ID : " + this.printTAGID() + "\n";
        ret += "Number of chunks : " + this.getChunks() + "\n";
        ret += "===============\n";
        ret += "Content :";
        int Line_Count = 0;
        byte[] Buffer = this.Content.toByteArray();
        for (int i = 0; i < Buffer.length; i++) {
            if (i % 128 == 0) {
                ret += "\n Block : " + i / 128 + "\n";
                Line_Count = 0;
            }
            if (Line_Count == 32) {
                ret += "\n";
                Line_Count = 0;
            }
            ret += Integer.toHexString(Buffer[i] & 0xFF) + " ";
            Line_Count++;
        }
        return ret;
    }


}
