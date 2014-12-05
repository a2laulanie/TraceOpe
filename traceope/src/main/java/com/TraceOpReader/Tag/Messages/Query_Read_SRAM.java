package com.TraceOpReader.Tag.Messages;

import com.TraceOpReader.ReaderQueryMessage;

import java.io.IOException;


public class Query_Read_SRAM extends ReaderQueryMessage {

    public Query_Read_SRAM(int Offset, int Length) throws IOException {
        super(ReaderQueryMessage.Read_SRAM);
        this.Content.write(0x4);
        this.Content.write(Offset & 0xFF);
        this.Content.write((Offset & 0xFF00) >> 8);
        this.Content.write(Length & 0xFF);
        this.Content.write((Length & 0xFF00) >> 8);
        super.addCRC();
    }

}


