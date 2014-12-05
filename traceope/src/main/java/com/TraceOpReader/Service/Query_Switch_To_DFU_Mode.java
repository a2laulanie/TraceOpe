package com.TraceOpReader.Service;

import com.TraceOpReader.ReaderMessage;
import com.TraceOpReader.ReaderQueryMessage;

public class Query_Switch_To_DFU_Mode extends ReaderQueryMessage {

    public Query_Switch_To_DFU_Mode(int Target_Update) {
        super(ReaderMessage.Update_FW_CMD);
        this.Content.write(0x1);
        this.Content.write(Target_Update);
        this.addCRC();
    }

}
