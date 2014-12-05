package com.TraceOpReader.Tag.Messages;

import com.TraceOpReader.ReaderMessage;
import com.TraceOpReader.ReaderQueryMessage;

import java.io.IOException;

public class Query_Start_Write_Tag_Memory extends ReaderQueryMessage {

    public Query_Start_Write_Tag_Memory(byte[] tagID, byte[] content, int offset) throws IOException {
        super(ReaderMessage.Start_Write_Tag_Memory);
        int size = content.length + tagID.length + 3;
        this.Content.write(size);
        this.Content.write(tagID);
        this.Content.write(offset & 0xFF);
        this.Content.write((offset & 0xFF00) >> 8);
        this.Content.write(content.length);
        this.Content.write(content);
        this.addCRC();
    }


}
