package com.TraceOpReader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class ReaderMessage {
    public static int SUCCESS = 0x0, UNDEFINED = 0x1, UNKNOWN_CMD = 0x2, PARAMETERS_INCORRECT = 0x3, REQ_PENDING = 0x4;
    public static int PROCESSING = 0x5;
    public static int NOT_RUNNING = 0x6;
    public static int ERROR_NO_TAG = 0x7;
    public static int ERROR_TIMEOUT = 0x8;
    public static int CRC_ERROR = 0x9;
    public static int BAD_MODE = 0x0a;
    public static int GEOPROT_UPDATE_FW_PROCESSING = 0x0b;
    public static int Micro_103 = 0x0;
    public static int Micro_108 = 0x1;
    public static int Compass_Calibration_Start = 1;
    public static int Compass_Calibration_Stop = 0;
    /**
     *
     */

    protected static int Turn_On_RFID = 0x00;
    protected static int Turn_Off_RFID = 0x01;
    protected static int Start_Read_Tag_ID = 0x02;
    protected static int Status_Read_Tag_ID = 0x03;
    protected static int Read_Tag_ID = 0x04;
    protected static int Start_Read_Tag_Memory = 0x05;
    protected static int Status_Read_Tag_Memory = 0x06;
    protected static int Read_Tag_Memory = 0x07;
    protected static int Start_Write_Tag_Memory = 0x08;
    protected static int Status_Write_Tag_Memory = 0x09;
    protected static int Update_FW_CMD = 0x0a;
    protected static int Update_FW = 0x0b;
    protected static int Update_DATA = 0x0c;
    protected static int Start_Read_TAGs_To_SRAM = 0xd;
    protected static int Status_Read_TAGs_To_SRAM = 0xe;
    protected static int Read_SRAM = 0xf;
    protected static int Write_SRAM = 0x10;
    protected static int Start_Write_TAGs_From_SRAM = 0x11;
    protected static int Status_Write_TAGs_From_SRAM = 0x12;
    protected static int Beacon_WakeUP = 0x14;
    protected static int Beacon_GetTables = 0x17;
    protected static int GetVoltage = 0x18;
    protected static int Compass_Calibration = 0x19;

    static String[] short_QueryString = {
            "Turn_On_RFID",
            "Turn_Off_RFID",
            "Start_Read_Tag_ID",
            "Status_Read_Tag_ID",
            "Read_Tag_ID",
            "Start_Read_Tag_Memory",
            "Status_Read_Tag_Memory",
            "Read_Tag_Memory",
            "Start_Write_Tag_Memory",
            "Status_Write_Tag_Memory",
            "Update_FW_Cmd",
            "Update_FW",
            "Update_DATA",
            "Start_Read_TAGs_To_SRAM",
            "Status_Read_TAGs_To_SRAM",
            "Read_SRAM",
            "Write_SRAM",
            "Start_Write_TAGs_From_SRAM",
            "Status_Write_TAGs_From_SRAM",
            "None",
            "Beacon_Wakeup",
            "Beacon_GetRecords",
            "None",
            "None",
            "Get Voltage",
            "Compass_Calibration"
    };
    static String[] short_ResponseString = {
            "SUCCESS",
            "UNDEFINED",
            "UNKNOWN_CMD",
            "PARAMETERS_INCORRECT",
            "REQ_PENDING",
            "PROCESSING",
            "NOT_RUNNING",
            "ERROR_NO_TAG",
            "ERROR_TIMEOUT",
            "CRC_ERROR",
            "BAD_MODE",
            "GEOPROT_UPDATE_FW_PROCESSING"
    };
    static String[] long_ResponseString = {
            "The command has been successfully executed",
            "An internal command has not been properly executed,and return with an unknown error",
            "The command passed is unknown or not implemented",
            "The parameter passed in the command is out of range or incorrect",
            "The command cannot be executed, an other command access to the RFID reader, the current command is dropped.",
            "The command is currently running, tags has not been collected.",
            "The command was not been received.",
            "The command has been executed, but not tag was	detected.",
            "The command has been executed, but terminated on timeout.",
            "Error in received CRC",
            "The micro-controller is not in update mode",
            "The command cannot be executed, an update is in progress, the current command is dropped"
    };
    public ByteArrayOutputStream Content = new ByteArrayOutputStream();

    ReaderMessage(int message_id) {
        this.Content.write(message_id);
    }

    ReaderMessage(int message_id, byte[] content) {
        this.Content.write(message_id);
        this.Content.write(content.length);
        this.Content.write(content, 1, content.length);
    }

    ReaderMessage(ByteArrayOutputStream content) {
        this.Content = content;
    }

    ReaderMessage(byte[] content) throws IOException {
        this.Content = new ByteArrayOutputStream();
        this.Content.write(content);
    }

    public ReaderMessage() {
        // TODO Auto-generated constructor stub
    }

    protected void addCRC() {
        eewareCRC16 CRC = new eewareCRC16(this.Content.toByteArray());
        this.Content.write(CRC.getCRCLo());
        this.Content.write(CRC.getCRCHigh());
    }

    public byte[] getMessageBytes() {
        return this.Content.toByteArray();
    }

    public String toString() {
        String e = new String();
        byte[] content = Content.toByteArray();
        e += "Message content : VALUE = 0x";
        e += content[0];
        if (content[0] < short_ResponseString.length) {
            e += " : ";
            e += short_ResponseString[content[0]];
            e += " (";
            e += long_ResponseString[content[0]];
            e += ")";
        } else {
            e += " INCONNU AU BATAILLON ";
        }
        e += " , Length = '";
        e += content[1];
        e += "' , CRC = 0x";
        e += Integer.toHexString(content[2] & 0xFF);
        e += ",0x";
        e += Integer.toHexString(content[3] & 0xFF);
        return e;
    }

}

