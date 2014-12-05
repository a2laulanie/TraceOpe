package com.TraceOpReader.Beacon.Messages;

import com.TraceOpReader.ReaderAnswerMessage;

/**
 * Created by nfl on 19/07/14.
 */
public class Answer_Beacon_WakeUP extends ReaderAnswerMessage {
    public Answer_Beacon_WakeUP() {

    }

    public Answer_Beacon_WakeUP(ReaderAnswerMessage answer) {
        this.Content = answer.Content;
    }
}
