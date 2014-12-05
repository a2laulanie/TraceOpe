package com.TraceOpReader.Beacon.Messages;

import com.TraceOpReader.ReaderMessage;
import com.TraceOpReader.ReaderQueryMessage;

/**
 * Created by nfl on 19/07/14.
 */
public class Query_Beacon_WakeUP extends ReaderQueryMessage {
    public Query_Beacon_WakeUP(String ID) {
        super(ReaderMessage.Beacon_WakeUP);

        Long Hex_Id = Long.parseLong(ID, 16);
        this.Content.write(0x4);
        this.Content.write((int) (Hex_Id & 0xFF));
        this.Content.write((int) (Hex_Id & 0xFF00) >> 8);
        this.Content.write((int) (Hex_Id & 0xFF0000) >> 16);
        this.Content.write((int) (Hex_Id & 0xFF000000) >> 24);
        super.addCRC();
    }
}
