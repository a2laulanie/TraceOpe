package com.TraceOpReader.Tag.Messages;

import com.TraceOpReader.ReaderQueryMessage;

import java.io.IOException;

public class Query_Start_Read_Tag_Memory extends ReaderQueryMessage {

    public Query_Start_Read_Tag_Memory(byte[] tagID, int chunk) throws IOException {
        super(ReaderQueryMessage.Start_Read_Tag_Memory);
        this.Content.write(0xb);
        this.Content.write(tagID);
        this.Content.write((chunk * 128) & 0x00FF);
        this.Content.write(((chunk * 128) & 0xFF00) >> 8);
        this.Content.write(128);
        this.Content.toByteArray()[1] = (byte) this.Content.size();
        super.addCRC();
    }

}
