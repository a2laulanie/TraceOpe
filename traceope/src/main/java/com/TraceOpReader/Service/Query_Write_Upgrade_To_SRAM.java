package com.TraceOpReader.Service;

import com.TraceOpReader.ReaderMessage;
import com.TraceOpReader.ReaderQueryMessage;

import java.io.IOException;

public class Query_Write_Upgrade_To_SRAM extends ReaderQueryMessage {

    public Query_Write_Upgrade_To_SRAM(byte[] content, int offset, boolean last_packet) throws IOException {
        super(ReaderMessage.Update_DATA);
        int size = content.length + 4;
        this.Content.write(size & 0xFF);
        if (last_packet) {
            this.Content.write(0x01);
        } else {
            this.Content.write(0x0);
        }
        this.Content.write(content.length & 0xFF);
        this.Content.write(offset & 0xff);
        this.Content.write((offset & 0xff00) >> 8);
        this.Content.write(content);
        this.addCRC();
    }


}
