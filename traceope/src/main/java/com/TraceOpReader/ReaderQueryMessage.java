package com.TraceOpReader;

import java.io.ByteArrayOutputStream;


public class ReaderQueryMessage extends ReaderMessage {

    public ReaderQueryMessage(int message_id) {
        super(message_id);
        // TODO Auto-generated constructor stub
    }

    public ReaderQueryMessage(int message_id, byte[] content) {
        super(message_id, content);
        // TODO Auto-generated constructor stub
    }

    public ReaderQueryMessage(ByteArrayOutputStream content) {
        super(content);
        // TODO Auto-generated constructor stub
    }

    public ReaderQueryMessage() {
        // TODO Auto-generated constructor stub
    }

    public String toString() {
        int position = 0;
        String buffer_out = "QUERY MESSAGE : (";
        buffer_out += Integer.toHexString(this.Content.toByteArray()[position] & 0xFF);
        buffer_out += ") ";
        buffer_out += short_QueryString[this.Content.toByteArray()[position]];
        buffer_out += " / ";
        position++;
        int size = this.Content.toByteArray()[position] & 0xFF;
        buffer_out += "Lenght =" + Integer.toHexString(size) + " / ";
        if (size > 0) position++;
        for (; position <= size + 1; position++) {
            buffer_out += " 0x" + Integer.toHexString(this.Content.toByteArray()[position] & 0xff);
        }
        buffer_out += " / Checksum :";
        buffer_out += " 0x" + Integer.toHexString(this.Content.toByteArray()[position] & 0xff);
        position++;
        buffer_out += " 0x" + Integer.toHexString(this.Content.toByteArray()[position] & 0xff);
        return buffer_out;
    }

}
