package com.TraceOpReader.Tag.Messages;

import com.TraceOpReader.ReaderAnswerMessage;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class Answer_Read_Tag_IDs extends ReaderAnswerMessage {

    public Answer_Read_Tag_IDs(int message_id) {
        super(message_id);
        // TODO Auto-generated constructor stub
    }

    public Answer_Read_Tag_IDs(int message_id, byte[] content) {
        super(message_id, content);
        // TODO Auto-generated constructor stub
    }

    public Answer_Read_Tag_IDs(ByteArrayOutputStream content) {
        super(content);
        // TODO Auto-generated constructor stub
    }

    public Answer_Read_Tag_IDs() {
        // TODO Auto-generated constructor stub
    }

    public Answer_Read_Tag_IDs(ReaderAnswerMessage answer) {
        this.Content = answer.Content;
        // TODO Auto-generated constructor stub
    }

    public int getNumberOfTags() {
        return this.Content.toByteArray()[2];
    }

    public int getNumberOfChunks() {
        return (this.Content.toByteArray()[3] + 1) * 8;
    }

    public byte[] getTagID(int number) {
        return Arrays.copyOfRange(this.Content.toByteArray(), (number * 8) + 4, (number * 8) + 4 + 8);
    }

}
