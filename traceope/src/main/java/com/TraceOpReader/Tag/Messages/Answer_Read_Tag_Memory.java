package com.TraceOpReader.Tag.Messages;

import com.TraceOpReader.ReaderAnswerMessage;

import java.util.Arrays;

public class Answer_Read_Tag_Memory extends ReaderAnswerMessage {

    public Answer_Read_Tag_Memory() {
    }

    public Answer_Read_Tag_Memory(ReaderAnswerMessage answer) {
        this.Content = answer.Content;
    }

    public byte[] getMemoryChunk() {
        return Arrays.copyOfRange(this.Content.toByteArray(), 2, (this.getDataSize() & 0xFF) + 2);
    }

}
