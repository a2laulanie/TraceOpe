package com.TraceOpReader.Tag.Messages;


import com.TraceOpReader.ReaderMessage;
import com.TraceOpReader.ReaderQueryMessage;

public class Query_Status_Write_SRAM_To_Tags extends ReaderQueryMessage {

    public Query_Status_Write_SRAM_To_Tags() {
        super(ReaderMessage.Status_Write_TAGs_From_SRAM);
        this.Content.write(0);
        super.addCRC();
    }

}
