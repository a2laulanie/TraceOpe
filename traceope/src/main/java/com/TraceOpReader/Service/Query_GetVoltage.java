package com.TraceOpReader.Service;

import com.TraceOpReader.ReaderMessage;
import com.TraceOpReader.ReaderQueryMessage;

/**
 * Created by nfl on 23/08/14.
 */
public class Query_GetVoltage extends ReaderQueryMessage {
    public Query_GetVoltage() {
        super(ReaderMessage.GetVoltage);
        this.Content.write(0);
        super.addCRC();
    }
}
