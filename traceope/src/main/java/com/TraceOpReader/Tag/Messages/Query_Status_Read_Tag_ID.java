package com.TraceOpReader.Tag.Messages;


import com.TraceOpReader.ReaderMessage;
import com.TraceOpReader.ReaderQueryMessage;

public class Query_Status_Read_Tag_ID extends ReaderQueryMessage {

    public Query_Status_Read_Tag_ID() {
        super(ReaderMessage.Status_Read_Tag_ID);
        this.Content.write(0);
        super.addCRC();
    }

}
