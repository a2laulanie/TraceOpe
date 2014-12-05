package com.TraceOpReader.Tag.Messages;


import com.TraceOpReader.ReaderMessage;
import com.TraceOpReader.ReaderQueryMessage;

public class Query_Status_Read_Tags_To_SRAM extends ReaderQueryMessage {

    public Query_Status_Read_Tags_To_SRAM() {
        super(ReaderMessage.Status_Read_TAGs_To_SRAM);
        this.Content.write(0);
        super.addCRC();
    }

}
