package com.TraceOpReader.Tag.Messages;

import com.TraceOpReader.ReaderMessage;
import com.TraceOpReader.ReaderQueryMessage;

import java.io.IOException;

public class Query_Write_SRAM extends ReaderQueryMessage {

    public Query_Write_SRAM(byte[] content, int offset) throws IOException {
        super(ReaderMessage.Write_SRAM);
        int size = content.length + 4;
        this.Content.write(size & 0xFF);
        this.Content.write(offset & 0xFF);
        this.Content.write(((offset & 0xFF00) >> 8) & 0xFF);
        this.Content.write(content.length & 0xFF);
        this.Content.write(((content.length & 0xFF00) >> 8) & 0xFF);
        this.Content.write(content);
        this.addCRC();
    }


}
