package com.TraceOpReader.Service;

import com.TraceOpReader.ReaderAnswerMessage;

/**
 * Created by nfl on 23/08/14.
 */
public class Answer_GetVoltage extends ReaderAnswerMessage {

    public Answer_GetVoltage() {
    }

    public Answer_GetVoltage(ReaderAnswerMessage answer) {
        this.Content = answer.Content;
    }

    public int getVoltage() {
        return ((this.Content.toByteArray()[3] << 8) & 0xFF00 | (this.Content.toByteArray()[2] & 0xFF));
    }

    public int getBatteryVoltage() {
        return ((this.Content.toByteArray()[4] << 8) & 0xFF00 | (this.Content.toByteArray()[5] & 0xFF));
    }

    public int getPercentBattery() {
        return ((this.Content.toByteArray()[6] & 0xFF));
    }


}
