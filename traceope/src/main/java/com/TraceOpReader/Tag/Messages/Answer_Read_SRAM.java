package com.TraceOpReader.Tag.Messages;

import com.TraceOpReader.ReaderAnswerMessage;

import java.util.Arrays;

public class Answer_Read_SRAM extends ReaderAnswerMessage {

    public Answer_Read_SRAM() {
    }

    public Answer_Read_SRAM(ReaderAnswerMessage answer) {
        this.Content = answer.Content;
    }

    public byte[] getMemoryChunk() {
        return Arrays.copyOfRange(this.Content.toByteArray(), 2, (this.getDataSize() & 0xFF) + 2);
    }

}
