package com.TraceOpReader.Service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;


/**
 * Created by nfl on 25/08/14.
 */
public class DFU_Valider {

    static byte DFU_File_Signature[] = {(byte) 'D', (byte) 'f', (byte) 'u', (byte) 'S', (byte) 'e'};
    static byte DFU_File_Footer_Signature[] = {(byte) 0x1A, (byte) 0x1, (byte) 'U', (byte) 'F', (byte) 'D'};
    static byte DFU_Signature_103[] = {(byte) 0x03, (byte) 0x01, (byte) 0x0, (byte) 0x0, (byte) 0x07, (byte) 0x0, (byte) 0x0, (byte) 0x0};
    static byte DFU_Signature_108[] = {(byte) 0x08, (byte) 0x01, (byte) 0x0, (byte) 0x0, (byte) 0x1a, (byte) 0x0, (byte) 0x0, (byte) 0x0};
    static int DFU_File_Signature_Size = 0xa;
    static int DFU_Footer_Signature_Size = 0x10;
    static int ImageHeader_Size = 270;
    byte FileContent[];

    public DFU_Valider(String FileName) {
        FileContent = getFileContent(FileName);
    }

    public DFU_Valider(byte[] Content) {
        FileContent = Content;
    }

    public byte[] getTargetHeader_n(int n) {
        // Get da first header (to work on first image)
        byte ImageHeader[] = Arrays.copyOfRange(this.FileContent, DFU_File_Signature_Size + 1, DFU_File_Signature_Size + 1 + ImageHeader_Size);
        // Start after the file signature
        int Cursor = DFU_File_Signature_Size;
        for (int i = 1; i < n; i++) {
            Cursor += getTargetSize(ImageHeader);
            ImageHeader = Arrays.copyOfRange(this.FileContent, Cursor, Cursor + 1 + ImageHeader_Size);
        }
        return ImageHeader;
    }

    public byte[] getTargetContent_n(int n) {
        // Get da first header (to work on first image)
        byte ImageContent[] = Arrays.copyOfRange(this.FileContent, DFU_File_Signature_Size + ImageHeader_Size + 1, DFU_File_Signature_Size + ImageHeader_Size + 1 + getTargetSize(getTargetHeader_n(1)));
        // Start after the file signature
        return ImageContent;
    }

    public boolean is108image(byte Image[]) {
        return Arrays.equals(Arrays.copyOfRange(Image, 524, 524 + 8), DFU_Signature_108);
    }

    public boolean is103image(byte Image[]) {
        return Arrays.equals(Arrays.copyOfRange(Image, 524, 524 + 8), DFU_Signature_103);
    }

    public int getTargetSize(byte Image_Header[]) {
        return (((Image_Header[266] & 0xFF)) + ((Image_Header[267] & 0xFF) << 8) + ((Image_Header[268] & 0xFF) << 16) + ((Image_Header[269] & 0xFF) << 24));
    }

    public String getTargetName(byte Image_Header[]) {
        return String.valueOf(Arrays.copyOfRange(Image_Header, 6, 11));
    }

    public String getTargetImageName(byte Image_Header[]) {
        byte Image_Name[] = Arrays.copyOfRange(Image_Header, 11, 266);
        int Cursor = 0;
        for (; Cursor < Image_Name.length; Cursor++) {
            if (Image_Name[Cursor] == 0) break;
        }
        return new String(Arrays.copyOfRange(Arrays.copyOfRange(Image_Header, 11, 266), 0, Cursor));
    }

    public byte[] getFileContent(String aInputFileName) {

        File file = new File(aInputFileName);
        byte[] result = new byte[(int) file.length()];
        try {
            InputStream input = null;
            try {
                int totalBytesRead = 0;
                input = new BufferedInputStream(new FileInputStream(file));
                while (totalBytesRead < result.length) {
                    int bytesRemaining = result.length - totalBytesRead;
                    //input.read() returns -1, 0, or more :
                    int bytesRead = input.read(result, totalBytesRead, bytesRemaining);
                    if (bytesRead > 0) {
                        totalBytesRead = totalBytesRead + bytesRead;
                    }
                }
        /*
         the above style is a bit tricky: it places bytes into the 'result' array;
         'result' is an output parameter;
         the while loop usually has a single iteration only.
        */

            } finally {
                if (input != null) {
                    input.close();
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Error : File not found");
        } catch (IOException ex) {
            System.err.println("Error : Problem while reading file");

        }
        ByteBuffer buf_out = ByteBuffer.allocate(result.length);
        buf_out.put(result);
        buf_out.order(ByteOrder.nativeOrder());
        return buf_out.array();
    }

    public boolean Validate_File_Signature_Header() {
        if (Arrays.equals(Arrays.copyOfRange(FileContent, 0, 5), DFU_File_Signature)) {
            return true;
        }
        return false;
    }

    public int Get_DFU_Version() {
        return this.FileContent[5];
    }

    public int Get_DFU_Length() {
        return ((this.FileContent[9] & 0xFF) << 24) + ((this.FileContent[8] & 0xFF) << 16) + ((this.FileContent[7] & 0xFF) << 8) + ((this.FileContent[6]) & 0xFF);
    }

    public int Get_Number_of_Images() {
        return this.FileContent[10];
    }

    public int Get_BCDDevice() {
        return this.FileContent[this.FileContent.length - 15] & (this.FileContent[this.FileContent.length - 14] & 0xFF) << 8;
    }

    public boolean Validate_File_Footer() {
        if (Arrays.equals(Arrays.copyOfRange(FileContent, FileContent.length - 10, FileContent.length - 5), DFU_File_Footer_Signature)) {
            return true;
        }
        return false;
    }

    public int getVendorId() {
        return ((FileContent[FileContent.length - 11] & 0xFF) << 8) + (FileContent[FileContent.length - 12] & 0xFF);
    }

    public int getProductId() {
        return ((FileContent[FileContent.length - 13] & 0xFF) << 8) + (FileContent[FileContent.length - 14] & 0xFF);
    }

    public int getDeviceId() {
        return ((FileContent[FileContent.length - 15] & 0xFF) << 8) + (FileContent[FileContent.length - 16] & 0xFF);
    }

    public boolean ValidateCRC() {
        long Expected_CRC = (FileContent[FileContent.length - 1] & 0xFF) << 24 | (FileContent[FileContent.length - 2] & 0xFF) << 16 | (FileContent[FileContent.length - 3] & 0xFF) << 8 | FileContent[FileContent.length - 4] & 0xFF;
        if (getCRC32(Arrays.copyOfRange(FileContent, 0, FileContent.length - 4)) == Expected_CRC) {
            return true;
        }
        return false;
    }

    private long getCRC32(byte[] data) {

        int[] table = {
                0x00000000, 0x77073096, 0xee0e612c, 0x990951ba, 0x076dc419, 0x706af48f, 0xe963a535, 0x9e6495a3,
                0x0edb8832, 0x79dcb8a4, 0xe0d5e91e, 0x97d2d988, 0x09b64c2b, 0x7eb17cbd, 0xe7b82d07, 0x90bf1d91,
                0x1db71064, 0x6ab020f2, 0xf3b97148, 0x84be41de, 0x1adad47d, 0x6ddde4eb, 0xf4d4b551, 0x83d385c7,
                0x136c9856, 0x646ba8c0, 0xfd62f97a, 0x8a65c9ec, 0x14015c4f, 0x63066cd9, 0xfa0f3d63, 0x8d080df5,
                0x3b6e20c8, 0x4c69105e, 0xd56041e4, 0xa2677172, 0x3c03e4d1, 0x4b04d447, 0xd20d85fd, 0xa50ab56b,
                0x35b5a8fa, 0x42b2986c, 0xdbbbc9d6, 0xacbcf940, 0x32d86ce3, 0x45df5c75, 0xdcd60dcf, 0xabd13d59,
                0x26d930ac, 0x51de003a, 0xc8d75180, 0xbfd06116, 0x21b4f4b5, 0x56b3c423, 0xcfba9599, 0xb8bda50f,
                0x2802b89e, 0x5f058808, 0xc60cd9b2, 0xb10be924, 0x2f6f7c87, 0x58684c11, 0xc1611dab, 0xb6662d3d,
                0x76dc4190, 0x01db7106, 0x98d220bc, 0xefd5102a, 0x71b18589, 0x06b6b51f, 0x9fbfe4a5, 0xe8b8d433,
                0x7807c9a2, 0x0f00f934, 0x9609a88e, 0xe10e9818, 0x7f6a0dbb, 0x086d3d2d, 0x91646c97, 0xe6635c01,
                0x6b6b51f4, 0x1c6c6162, 0x856530d8, 0xf262004e, 0x6c0695ed, 0x1b01a57b, 0x8208f4c1, 0xf50fc457,
                0x65b0d9c6, 0x12b7e950, 0x8bbeb8ea, 0xfcb9887c, 0x62dd1ddf, 0x15da2d49, 0x8cd37cf3, 0xfbd44c65,
                0x4db26158, 0x3ab551ce, 0xa3bc0074, 0xd4bb30e2, 0x4adfa541, 0x3dd895d7, 0xa4d1c46d, 0xd3d6f4fb,
                0x4369e96a, 0x346ed9fc, 0xad678846, 0xda60b8d0, 0x44042d73, 0x33031de5, 0xaa0a4c5f, 0xdd0d7cc9,
                0x5005713c, 0x270241aa, 0xbe0b1010, 0xc90c2086, 0x5768b525, 0x206f85b3, 0xb966d409, 0xce61e49f,
                0x5edef90e, 0x29d9c998, 0xb0d09822, 0xc7d7a8b4, 0x59b33d17, 0x2eb40d81, 0xb7bd5c3b, 0xc0ba6cad,
                0xedb88320, 0x9abfb3b6, 0x03b6e20c, 0x74b1d29a, 0xead54739, 0x9dd277af, 0x04db2615, 0x73dc1683,
                0xe3630b12, 0x94643b84, 0x0d6d6a3e, 0x7a6a5aa8, 0xe40ecf0b, 0x9309ff9d, 0x0a00ae27, 0x7d079eb1,
                0xf00f9344, 0x8708a3d2, 0x1e01f268, 0x6906c2fe, 0xf762575d, 0x806567cb, 0x196c3671, 0x6e6b06e7,
                0xfed41b76, 0x89d32be0, 0x10da7a5a, 0x67dd4acc, 0xf9b9df6f, 0x8ebeeff9, 0x17b7be43, 0x60b08ed5,
                0xd6d6a3e8, 0xa1d1937e, 0x38d8c2c4, 0x4fdff252, 0xd1bb67f1, 0xa6bc5767, 0x3fb506dd, 0x48b2364b,
                0xd80d2bda, 0xaf0a1b4c, 0x36034af6, 0x41047a60, 0xdf60efc3, 0xa867df55, 0x316e8eef, 0x4669be79,
                0xcb61b38c, 0xbc66831a, 0x256fd2a0, 0x5268e236, 0xcc0c7795, 0xbb0b4703, 0x220216b9, 0x5505262f,
                0xc5ba3bbe, 0xb2bd0b28, 0x2bb45a92, 0x5cb36a04, 0xc2d7ffa7, 0xb5d0cf31, 0x2cd99e8b, 0x5bdeae1d,
                0x9b64c2b0, 0xec63f226, 0x756aa39c, 0x026d930a, 0x9c0906a9, 0xeb0e363f, 0x72076785, 0x05005713,
                0x95bf4a82, 0xe2b87a14, 0x7bb12bae, 0x0cb61b38, 0x92d28e9b, 0xe5d5be0d, 0x7cdcefb7, 0x0bdbdf21,
                0x86d3d2d4, 0xf1d4e242, 0x68ddb3f8, 0x1fda836e, 0x81be16cd, 0xf6b9265b, 0x6fb077e1, 0x18b74777,
                0x88085ae6, 0xff0f6a70, 0x66063bca, 0x11010b5c, 0x8f659eff, 0xf862ae69, 0x616bffd3, 0x166ccf45,
                0xa00ae278, 0xd70dd2ee, 0x4e048354, 0x3903b3c2, 0xa7672661, 0xd06016f7, 0x4969474d, 0x3e6e77db,
                0xaed16a4a, 0xd9d65adc, 0x40df0b66, 0x37d83bf0, 0xa9bcae53, 0xdebb9ec5, 0x47b2cf7f, 0x30b5ffe9,
                0xbdbdf21c, 0xcabac28a, 0x53b39330, 0x24b4a3a6, 0xbad03605, 0xcdd70693, 0x54de5729, 0x23d967bf,
                0xb3667a2e, 0xc4614ab8, 0x5d681b02, 0x2a6f2b94, 0xb40bbe37, 0xc30c8ea1, 0x5a05df1b, 0x2d02ef8d,
        };


        byte[] bytes = data;
        int crc = 0xffffffff;


        for (byte b : bytes) {
            crc = (crc >>> 8) ^ table[(crc ^ b) & 0xff];
        }

        return crc;
    }
}
