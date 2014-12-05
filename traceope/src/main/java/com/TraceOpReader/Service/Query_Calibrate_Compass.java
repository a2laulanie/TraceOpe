package com.TraceOpReader.Service;

import com.TraceOpReader.ReaderMessage;
import com.TraceOpReader.ReaderQueryMessage;

import java.util.Arrays;

/**
 * Created by nfl on 23/08/14.
 */
public class Query_Calibrate_Compass extends com.TraceOpReader.ReaderQueryMessage {
    final static char[] Axis_List = {'X', 'Y', 'Z'};

    public Query_Calibrate_Compass(char Axis, int state) throws Exception {
        super(ReaderMessage.Compass_Calibration);
        this.Content.write(2);
        this.Content.write(state);
        if (Arrays.binarySearch(Axis_List, Axis) < 0) {
            throw new Exception("Wrong AXIS");
        }
        this.Content.write(Axis);
        this.addCRC();
    }
}
