package com.TraceOpReader.Tag.Messages;


import com.TraceOpReader.ReaderMessage;
import com.TraceOpReader.ReaderQueryMessage;

public class Query_Start_Read_Tag_ID extends ReaderQueryMessage {

    public Query_Start_Read_Tag_ID() {
        super(ReaderMessage.Start_Read_Tag_ID);
        this.Content.write(0);
        super.addCRC();
    }

}
