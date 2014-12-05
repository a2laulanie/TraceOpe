package com.TraceOpReader.Tag.Messages;

import com.TraceOpReader.ReaderMessage;
import com.TraceOpReader.ReaderQueryMessage;

public class Query_Read_Tag_Memory extends ReaderQueryMessage {

    public Query_Read_Tag_Memory() {
        super(ReaderMessage.Read_Tag_Memory);
        this.Content.write(0);
        super.addCRC();
    }
}
