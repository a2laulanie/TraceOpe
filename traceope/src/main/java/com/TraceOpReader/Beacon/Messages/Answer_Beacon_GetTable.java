package com.TraceOpReader.Beacon.Messages;

import com.TraceOpReader.ReaderAnswerMessage;

import java.util.Arrays;

/**
 * Created by nfl on 19/07/14.
 */
public class Answer_Beacon_GetTable extends ReaderAnswerMessage {
    public Answer_Beacon_GetTable() {

    }

    public Answer_Beacon_GetTable(ReaderAnswerMessage answer) {
        this.Content = answer.Content;
    }

    public Beacon_Record getRecord(int number) {
        return new Beacon_Record(Arrays.copyOfRange(this.Content.toByteArray(), 2, 11), number);
    }

}
