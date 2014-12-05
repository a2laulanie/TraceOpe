package com.TraceOpReader.Beacon.Messages;

import java.util.Date;

/**
 * Created by nfl on 04/10/14.
 */
public class Beacon_Record {
    byte content[];
    int Antenna_Number;
    int RSSI_Level;
    int Angle;
    int Number;
    Date time;

    Beacon_Record(byte Content[], int number) {
        this.content = Content;
        Antenna_Number = Content[0] & 0xFF;
        RSSI_Level = Content[1];
        Number = number;
        Angle = (Content[2] & 0xFF) | (Content[3] & 0xFF) << 8;
        time = new Date((Content[4] & 0xFF) | ((Content[5] & 0xFF) << 8) | ((Content[6] & 0xFF) << 16) | ((Content[7] & 0xFF) << 24));
    }

    @Override
    public String toString() {
        return Number + " ; " + Antenna_Number + " ; " + RSSI_Level + " ; " + Angle + " ; " + time.getTime();
    }
}
