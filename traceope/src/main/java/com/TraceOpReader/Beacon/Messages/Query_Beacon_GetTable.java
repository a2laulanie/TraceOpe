package com.TraceOpReader.Beacon.Messages;

import com.TraceOpReader.ReaderMessage;
import com.TraceOpReader.ReaderQueryMessage;

/**
 * Created by nfl on 19/07/14.
 */
public class Query_Beacon_GetTable extends ReaderQueryMessage {
    public Query_Beacon_GetTable(int Table_Index) {
        super(ReaderMessage.Beacon_GetTables);
        this.Content.write(0x2);
        this.Content.write(Table_Index & 0xFF);
        this.Content.write((Table_Index & 0xFF00) >> 8);
        super.addCRC();
    }
}
