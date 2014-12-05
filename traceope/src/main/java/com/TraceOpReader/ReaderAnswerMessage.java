package com.TraceOpReader;

import java.io.ByteArrayOutputStream;

public class ReaderAnswerMessage extends ReaderMessage {

    public ReaderAnswerMessage(int message_id) {
        super(message_id);
        // TODO Auto-generated constructor stub
    }

    public ReaderAnswerMessage(int message_id, byte[] content) {
        super(message_id, content);
        // TODO Auto-generated constructor stub
    }

    public ReaderAnswerMessage(ByteArrayOutputStream content) {
        super(content);
        // TODO Auto-generated constructor stub
    }

    public ReaderAnswerMessage() {
        // TODO Auto-generated constructor stub
    }

    public boolean isSucces() {
        if (this.Content.toByteArray()[0] == SUCCESS) {
            return true;
        }
        return false;
    }

    protected int getDataSize() {
        return (this.Content.toByteArray()[1] & 0xFF);
    }

    public int getStatus() {
        if (this.Content.size() > 0) {
            return this.Content.toByteArray()[0];
        } else
            return 99;
    }

    public String toString() {
        if (this.Content.size() <= 0)
            return "NO ANSWER";
        int position = 0;
        String buffer_out = "RESPONSE MESSAGE : ";
        buffer_out += short_ResponseString[this.Content.toByteArray()[position]];
        buffer_out += " (";
        buffer_out += this.Content.toByteArray()[position];
        buffer_out += ") ";

        buffer_out += "/ ";
        position++;
        int size = getDataSize();
        if (size > this.Content.size() + 3) {
            buffer_out += "Wrong data : ";
            for (; position < this.Content.size(); position++) {
                buffer_out += " 0x" + Integer.toHexString(this.Content.toByteArray()[position] & 0xff);
            }

        } else {
            buffer_out += "Size=" + size + " / Data =";
            for (; position < size; position++) {
                buffer_out += " 0x" + Integer.toHexString(this.Content.toByteArray()[position] & 0xff);
            }
            buffer_out += " / Checksum : 0x";
            position++;
            buffer_out += Integer.toHexString(this.Content.toByteArray()[position] & 0xff);
            position++;
            buffer_out += Integer.toHexString(this.Content.toByteArray()[position] & 0xff);
        }
        return buffer_out;

    }

}
