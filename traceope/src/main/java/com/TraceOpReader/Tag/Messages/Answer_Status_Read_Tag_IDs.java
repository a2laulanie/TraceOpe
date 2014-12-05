package com.TraceOpReader.Tag.Messages;

import com.TraceOpReader.ReaderAnswerMessage;

import java.io.ByteArrayOutputStream;


public class Answer_Status_Read_Tag_IDs extends ReaderAnswerMessage {

    public Answer_Status_Read_Tag_IDs(int message_id) {
        super(message_id);
        // TODO Auto-generated constructor stub
    }

    public Answer_Status_Read_Tag_IDs(int message_id, byte[] content) {
        super(message_id, content);
        // TODO Auto-generated constructor stub
    }

    public Answer_Status_Read_Tag_IDs(ByteArrayOutputStream content) {
        super(content);
        // TODO Auto-generated constructor stub
    }

    public Answer_Status_Read_Tag_IDs() {
        // TODO Auto-generated constructor stub
    }

}
