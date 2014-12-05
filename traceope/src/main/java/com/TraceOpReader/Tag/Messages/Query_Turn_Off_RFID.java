package com.TraceOpReader.Tag.Messages;

import com.TraceOpReader.ReaderMessage;
import com.TraceOpReader.ReaderQueryMessage;

public class Query_Turn_Off_RFID extends ReaderQueryMessage {

    public Query_Turn_Off_RFID() {
        super(ReaderMessage.Turn_Off_RFID);
        this.Content.write(0);
        super.addCRC();
        // TODO Auto-generated constructor stub
    }


}
