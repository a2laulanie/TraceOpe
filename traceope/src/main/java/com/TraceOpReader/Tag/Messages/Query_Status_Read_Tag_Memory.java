package com.TraceOpReader.Tag.Messages;


import com.TraceOpReader.ReaderMessage;
import com.TraceOpReader.ReaderQueryMessage;

public class Query_Status_Read_Tag_Memory extends ReaderQueryMessage {

    public Query_Status_Read_Tag_Memory() {
        super(ReaderMessage.Status_Read_Tag_Memory);
        this.Content.write(0);
        super.addCRC();
    }

}
