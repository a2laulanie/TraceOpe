package com.TraceOpReader.Tag.Messages;

import com.TraceOpReader.ReaderQueryMessage;
import com.TraceOpReader.Tag.Tag;

import java.io.IOException;
import java.util.Vector;


public class Query_Start_Read_Tags_To_SRAM extends ReaderQueryMessage {

    public Query_Start_Read_Tags_To_SRAM(Vector<Tag> tags) throws IOException {
        super(ReaderQueryMessage.Start_Read_TAGs_To_SRAM);
        this.Content.write(0x31);
        this.Content.write(0x4);

        for (int i = 0; i < tags.size(); i++) {
            this.Content.write(tags.get(i).getID());
            this.Content.write(0x00);
            this.Content.write(0x00);
            this.Content.write((8 * 1024) & 0x00FF);
            this.Content.write(((8 * 1024) & 0xFF00) >> 8);
        }
        super.addCRC();
    }

}
