package com.TraceOpReader.Service;

import com.TraceOpReader.ReaderMessage;
import com.TraceOpReader.ReaderQueryMessage;

public class Query_Upgrade_FW extends ReaderQueryMessage {

    public Query_Upgrade_FW(int target) {
        super(ReaderMessage.Update_FW);
        this.Content.write(1);
        this.Content.write(target);
        this.addCRC();
    }

}
