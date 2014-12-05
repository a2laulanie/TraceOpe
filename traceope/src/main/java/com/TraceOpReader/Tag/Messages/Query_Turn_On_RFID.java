package com.TraceOpReader.Tag.Messages;

import com.TraceOpReader.ReaderMessage;
import com.TraceOpReader.ReaderQueryMessage;

public class Query_Turn_On_RFID extends ReaderQueryMessage {

    public Query_Turn_On_RFID() {
        super(ReaderMessage.Turn_On_RFID);
        this.Content.write(0);
        this.addCRC();
    }

}
