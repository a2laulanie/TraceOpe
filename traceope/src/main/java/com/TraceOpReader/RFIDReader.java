package com.TraceOpReader;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.TraceOpReader.Beacon.Messages.Answer_Beacon_GetTable;
import com.TraceOpReader.Beacon.Messages.Beacon_Record;
import com.TraceOpReader.Beacon.Messages.Query_Beacon_GetTable;
import com.TraceOpReader.Beacon.Messages.Query_Beacon_WakeUP;
import com.TraceOpReader.Service.Answer_GetVoltage;
import com.TraceOpReader.Service.DFU_Valider;
import com.TraceOpReader.Service.Query_Calibrate_Compass;
import com.TraceOpReader.Service.Query_GetVoltage;
import com.TraceOpReader.Service.Query_Switch_To_DFU_Mode;
import com.TraceOpReader.Service.Query_Upgrade_FW;
import com.TraceOpReader.Service.Query_Write_Upgrade_To_SRAM;
import com.TraceOpReader.Tag.*;
import com.TraceOpReader.Tag.Messages.*;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.UUID;
import java.util.Vector;


public class RFIDReader {

    static int RADIO_OFF = 0x0, RADIO_ON = 0x1, PENDING = 0x2;
    ReaderQueryMessage query;
    ReaderAnswerMessage answer;
    int STATE;
    private boolean ATTACHED = false;
    private BluetoothDevice readerDevice;// le périphérique (le module bluetooth)
    private BluetoothSocket socket = null;
    private InputStream receiveStream = null;// Canal de réception
    private OutputStream sendStream = null;// Canal d'émission


    public RFIDReader(BluetoothDevice device) {
        this.readerDevice = device;
		try {
		socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
		this.receiveStream = socket.getInputStream();
		this.sendStream = socket.getOutputStream();			
		}
		catch (IOException e) {
			Log.d("RFIDREADER_Start", e.toString());
		}
		// TODO Auto-generated constructor stub
	}

	public void send(ReaderMessage message) {
		////serie 00001103-0000-1000-8000-00805F9B34FB
		try {
		
		Log.d("RFIDReader_WRITE", "message sended : " + message.toString());
			
		sendStream.write(message.getMessageBytes());
		}
		catch (IOException e) {
			System.err.println(e.toString());
		}
		
	}


	public ReaderAnswerMessage getResponse() throws InterruptedException {
		// TODO Auto-generated method stub

		ByteArrayOutputStream Buffer = new ByteArrayOutputStream();
		
		byte[] b = new ByteArrayBuffer(512).toByteArray();
		
/*
 * ce code "attends" jusqu'à 1s le retour de la fonction... le code suivant lis la longueur du message et 
 * prends la longueur déclarée dans le message : permets de diminuer la boucle d'attente (0,8s max vs 8s)
 * 
 * 		try {
			int tries = 0;
			while ( tries < 81 ) {
				if ( receiveStream.available() > 0) {
					Log.w("RFIDReader_READFROM_BT", "There's data "+ receiveStream.available() );
					byte buffer[] = new byte[512];
					int k = receiveStream.read(buffer, 0, 512);

					if(k > 0) {
						byte rawdata[] = new byte[k];
						for(int i=0;i<k;i++)
							rawdata[i] = buffer[i];
						Buffer.write(rawdata,0,k);
					}
					tries = 99;
				}
				tries++;
				if (tries < 80 )
				{
					Thread.sleep(1000);
					Log.e("RFIDREADER_ReceiveLoop", "Retries:" + tries);
				}
			}
		}*/
		try {
			int tries = 0;
			boolean message_started=false;
			int expected_size = 0;
			byte buffer[] = new byte[512];
			int bytes_received = 0;
			int k = 0;
			
			while ( tries < 81 ) {
				tries++;
				if (( receiveStream.available() > 2) && ( message_started == false )){
					Log.d("RFIDReader_READFROM_BT", "There's data message ..." + receiveStream.available());
					k = receiveStream.read(buffer, 0, receiveStream.available());
					expected_size = buffer[1] & 0xFF;
					Buffer.write(buffer,0,k);
					Log.d("RFIDReader_READFROM_BT", "New message started with expected size of : " + expected_size + " , actualy " + k + "bytes readed");
					if ( k == expected_size + 4 ){
						Log.d("RFIDReader_READFROM_BT", "Data received with expected size of : " + expected_size + " Buffer lenght = " + k);
						return new ReaderAnswerMessage(Buffer);
					}
					message_started = true;
					bytes_received = k;
					
				}
				
				if ((message_started == true) && (receiveStream.available() > 0 )) {
						Log.d("RFIDReader_READFROM_BT", "Expected lenght received, get datas");
						k = receiveStream.read(buffer, 0, receiveStream.available() );
						Buffer.write(buffer,bytes_received,k);
						bytes_received += k;
					} else
					{
						Log.d("RFIDReader_READFROM_BT", "All datas are not ready , received : " + receiveStream.available());
					}
				
				if ( bytes_received == expected_size + 4 ) {
					return new ReaderAnswerMessage(Buffer);
				}
				
				if (tries < 80 )
				{
					Thread.sleep(100);
					Log.d("RFIDREADER_ReceiveLoop", "Retries:" + tries);
				}
				
			}
			
		}
		catch (IOException e) {
			System.err.println(e.toString());
		}
		ReaderAnswerMessage Answer = new ReaderAnswerMessage(Buffer);
		Log.d("RFIDReader_ANSWER", "Answer : " + Answer.toString());
		return (Answer);
	}
	
	public void turnON_RADIO() throws InterruptedException {
		query = new Query_Turn_On_RFID();
		send(query);
		Thread.sleep(1000);
		answer = getResponse();
		if ( answer.isSucces())
		{
					System.out.println("Radio is on");
					STATE = RADIO_ON;
		}
		else
		{
				System.out.println("Radio is not turning on...");
				System.err.println(answer.toString());
		}
		
	}
	
	public void turnOFF_RADIO() throws InterruptedException {
		query = new Query_Turn_Off_RFID();
		send(query);
		Thread.sleep(1000);
		answer = getResponse();
		if ( answer.isSucces())
		{
					System.out.println("Radio is off");
					STATE = RADIO_OFF;
		}
		else
		{
				System.out.println("Radio is not turning off...");
				System.err.println(answer.toString());
		}
	}

	public Vector<Tag> GetTags() throws InterruptedException {
		Vector<Tag> ret = new Vector<Tag>();
		// Start tag discovery
		query = new Query_Start_Read_Tag_ID();
		send(query);		
		Thread.sleep(1000);
		answer = getResponse();
		if ( ! answer.isSucces())
		{
					System.out.println("Problem while lauching Tag search ");
					System.err.println(answer.toString());
					return null;
		}
		
		// Wait for completion
		query = new Query_Status_Read_Tag_ID();
		send(query);		
		Thread.sleep(1000);
		answer = getResponse();
		while ( ! answer.isSucces()  )
		{
			if ( answer.getStatus() == Answer_Read_Tag_IDs.PROCESSING )
			{
				System.out.println("Discovery in progress...");
				send(query);		
				Thread.sleep(1000);
				answer = getResponse();
			}
			else {
				System.out.println("Problem while lauching Tag search ");
				System.err.println(answer.toString());
				return null;
			}
		}
		
		// Get Tag IDs and create it
		query = new Query_Read_Tag_ID();
		send(query);		
		Thread.sleep(1000);
		answer = getResponse();
		
		if (answer.isSucces()) {
			Answer_Read_Tag_IDs TagIDs = new Answer_Read_Tag_IDs(answer);
			for (int i = 0; i < TagIDs.getNumberOfTags() ; i++) {
				Tag Current_Tag = new Tag();
				Current_Tag.setID(TagIDs.getTagID(i));
				Current_Tag.setChunks(TagIDs.getNumberOfChunks());
				System.out.println("Tag Added : "+Current_Tag.printTAGID());
				ret.add(Current_Tag);
			}
		}
		
		return ret;
			
	}

	public void readTag(Tag current_Tag) throws InterruptedException, IOException {
		System.out.println("\nFlushing Tag");
		current_Tag.flushContent();
		System.out.println("\nReading chunks : ");
		for (int i = 0 ; i < current_Tag.getChunks() ; i ++) {
				System.out.print(" " + i);
				query = new Query_Start_Read_Tag_Memory(current_Tag.getID(), i);
				send(query);		
				Thread.sleep(100);
				answer = getResponse();
				if ( ! answer.isSucces())
				{
						System.out.println("Problem while lauching Tag Read ");
						System.err.println(answer.toString());
						return;
				}
		
				// Wait for completion
				query = new Query_Status_Read_Tag_Memory();
				send(query);		
				Thread.sleep(100);
				answer = getResponse();
				while ( ! ( answer.getStatus() == ReaderMessage.SUCCESS ) )
				{
					if ( answer.getStatus() == ReaderMessage.PROCESSING )
					{
						send(query);		
						Thread.sleep(100);
						answer = getResponse();
					}
					else {
						System.out.println("Problem while reading Tag");
						System.err.println(answer.toString());
					}
				}
				
				query = new Query_Read_Tag_Memory();
				send(query);
				Thread.sleep(100);
				answer = getResponse();
				if ( ! answer.isSucces())
				{
					System.out.println("Problem while Memory Read ");
					System.err.println(answer.toString());
				}
				else {
					Answer_Read_Tag_Memory PDU = new Answer_Read_Tag_Memory(answer);
					current_Tag.addContent(PDU.getMemoryChunk());
				}
		}
		
	}
	
	
	public void readTags(Vector<Tag> Tags) throws InterruptedException, IOException {
		query = new Query_Start_Read_Tags_To_SRAM(Tags);
		send(query);		
		Thread.sleep(100);
		answer = getResponse();
		if ( ! answer.isSucces())
		{
				System.out.println("Problem while lauching Tag Read ");
				System.err.println(answer.toString());
				return;
		}

		// Wait for completion
		query = new Query_Status_Read_Tags_To_SRAM();
		send(query);		
		Thread.sleep(100);
		answer = getResponse();
		while ( ! ( answer.getStatus() == ReaderMessage.SUCCESS ) )
		{
			if ( answer.getStatus() == ReaderMessage.PROCESSING )
			{
				send(query);		
				Thread.sleep(150);
				answer = getResponse();
			}
			else {
				System.out.println("Problem while reading Tag");
				System.err.println(answer.toString());
			}
		}

	}

	public void readTagsValue(Vector<Tag> myTags) throws IOException, InterruptedException {
        int Tag_Offset_in_SRAM = 0;
		for (int i = 0 ; i < myTags.size(); i++) {
			Tag currentTag = myTags.get(i);
			currentTag.flushContent();
			for (int j = 0 ; j < currentTag.getChunks() ; j++) {
					query = new Query_Read_SRAM(((j*128) + Tag_Offset_in_SRAM) & 0xFFFF,128);
					send(query);		
					Thread.sleep(200);
					answer = getResponse();
					Answer_Read_SRAM PDU = new Answer_Read_SRAM(answer);
					currentTag.addContent(PDU.getMemoryChunk());
			}
            Tag_Offset_in_SRAM += currentTag.getChunks() * 128 & 0xFFFF;
		}
	}
	
	public void writeTagsDataToReader(Vector<Tag> myTags) throws IOException, InterruptedException
	{
		Tag current_Tag = new Tag();
        int local_offset = 0 ; int global_offset = 0 ;
		for (int tag_number = 0; tag_number < myTags.size() ; tag_number++)
		{
			current_Tag = myTags.get(tag_number);

			{
				int size = 0;
				if ((current_Tag.getChunks() * 128 ) < current_Tag.getContent().size() )
				{
					System.err.println("Error : more data than chunks... : writing only first data ");
					size = current_Tag.getChunks();
				}
				else
				{
					size = (current_Tag.getContent().size()/128);
				}

				System.out.println("\nWriting chunks to SRAM: ");
				for (int i = 0 ; i < (current_Tag.getContent().size()/128) ; i ++) {
					Log.e("RFREADER_WRITECHUNK_SRAM", " Current_tag :" + tag_number + " Chunk : " + i + " local from :  " + Integer.toHexString(local_offset) + "to " + Integer.toHexString(local_offset + 128) + " / total offset : " + Integer.toHexString(global_offset));
					// TODO : si le block n'est pas égale à 128, faire l'ecriture du block, plus petit...
                    local_offset = (i*128) & 0xFFFF;
					query = new Query_Write_SRAM(Arrays.copyOfRange(current_Tag.getContent().toByteArray(), local_offset & 0xffff, (local_offset + 128) & 0xffff), global_offset );
                    global_offset += (128 & 0xFFFF) ;
					send(query);		
					Thread.sleep(100);
					answer = getResponse();
					if ( ! answer.isSucces())
					{
						System.out.println("Problem while lauching SRAM Write ");
						System.err.println(answer.toString());
					}

				}
			}	
		}
	}
	
	public void commitDataToTags(Vector<Tag> myTags) throws IOException, InterruptedException {
		query = new Query_Start_Write_SRAM_To_Tags(myTags);
		send(query);		
		Thread.sleep(100);
		answer = getResponse();
		if ( ! answer.isSucces())
		{
				System.out.println("Problem while lauching Tag Read ");
				System.err.println(answer.toString());
				return;
		}

		// Wait for completion
		query = new Query_Status_Write_SRAM_To_Tags();
		send(query);		
		Thread.sleep(100);
		answer = getResponse();
		while ( ! ( answer.getStatus() == ReaderMessage.SUCCESS ) )
		{
			if ( answer.getStatus() == ReaderMessage.PROCESSING )
			{
				send(query);		
				Thread.sleep(150);
				answer = getResponse();
			}
			else {
				System.out.println("Problem while reading Tag");
				System.err.println(answer.toString());
			}
		}
	}

	
	public void writeTag(Tag current_Tag) throws InterruptedException, IOException {
		int size = 0;
		if ((current_Tag.getChunks() * 128 ) < current_Tag.getContent().size() )
			{
				System.err.println("Error : more data than chunks... : writing only first data ");
				size = current_Tag.getChunks();
			}
		else
			{
				size = (current_Tag.getContent().size()/128);
			}
		
		System.out.println("\nWriting chunks : ");
		for (int i = 0 ; i < (current_Tag.getContent().size()/128) ; i ++) {
				System.out.print(" " + i);
				// TODO : si le block n'est pas égale à 128, faire l'ecriture du block, plus petit...
				System.out.print(" " + i);
				query = new Query_Start_Write_Tag_Memory(current_Tag.getID(), Arrays.copyOfRange(current_Tag.getContent().toByteArray(), i * 128, (i + 1) * 128), i*128 );
				send(query);		
				Thread.sleep(100);
				answer = getResponse();
				if ( ! answer.isSucces())
				{
						System.out.println("Problem while lauching Tag Write ");
						System.err.println(answer.toString());
						return;
				}
		
				// Wait for completion
				query = new Query_Status_Write_Tag_Memory();
				send(query);		
				Thread.sleep(100);
				answer = getResponse();
				while ( ! ( answer.getStatus() == ReaderMessage.SUCCESS ) )
				{
					if ( ( answer.getStatus() == ReaderMessage.PROCESSING ) || (answer.getStatus() == ReaderMessage.REQ_PENDING) )
					{
						Thread.sleep(500);
						send(query);
						Thread.sleep(100);
						answer = getResponse();
					}
					else {
						System.out.println("Problem while writing Tag");
						System.err.println(answer.toString());
					}
				}
				
		}
		
	}


    public void beacon_StartSearch(String Id) throws InterruptedException {
        query = new Query_Beacon_WakeUP(Id);
        send(query);
        Thread.sleep(100);
        answer = getResponse();
        if (answer.getStatus() == ReaderMessage.SUCCESS) {

            Log.d("TRACE_OP_WAKEUP", "Wakeup ok");
        } else {

            Log.d("TRACE_OP_WAKEUP", "Problem while Wakeup" + answer.toString());
        }

    }

    public void upgradeFW(byte[] FWData) throws InterruptedException, IOException {
        int target = ReaderMessage.Micro_103;
        DFU_Valider myFirmware = new DFU_Valider(FWData);
        byte[] Buffer = FWData;
        Log.d("DEBUG", "Start flashing FW");
        if (!myFirmware.ValidateCRC()) {
            return;
        }
        if (myFirmware.is108image(myFirmware.getTargetContent_n(1))) {
            Log.d("TRACE_OP_UPGRADEFW", "DFU Upgrade 108");
            Real_Upgrade_FW(Buffer, ReaderMessage.Micro_108);
        } else if (myFirmware.is103image(myFirmware.getTargetContent_n(1))) {
            Log.d("TRACE_OP_UPGRADEFW", "DFU Upgrade 103");
            Real_Upgrade_FW(Buffer, ReaderMessage.Micro_103);
        } else {
            Log.d("TRACE_OP_UPGRADEFW", "DFU Upgrade : wrong firmware!!");
            throw new IOException("WRONG FIRMWARE!");
        }
    }

    private void Real_Upgrade_FW(byte[] Buffer, int target) throws IOException, InterruptedException {

        // Sending DFU command
  /*      query = new Query_Switch_To_DFU_Mode(target);
        send(query);
        Thread.sleep(100);
        answer = getResponse();
        if (answer.getStatus() == ReaderMessage.SUCCESS) {

            Log.d("TRACE_OP_UPGRADEFW", "DFU Switch ok");
        } else {

            Log.d("TRACE_OP_UPGRADEFW", "Problem while DFU Upgrade" + answer.toString());
        }
*/

        query = new Query_Upgrade_FW(target);
        send(query);
        Thread.sleep(100);
        answer = getResponse();
        if ( ! answer.isSucces())
        {
            System.out.println("Problem while lauching upgrade from SRAM process ");
            System.err.println(answer.toString());
            return;
        }

        for (int i = 0; i < (Buffer.length / 128); i++) {
            query = new Query_Write_Upgrade_To_SRAM(Arrays.copyOfRange(Buffer, i * 128, (i + 1) * 128), i, false);
            send(query);
            Thread.sleep(100);
            answer = getResponse();
            while ( ! ( answer.getStatus() == ReaderMessage.SUCCESS ) )
            {
                if ( ( answer.getStatus() == ReaderMessage.PROCESSING ) || (answer.getStatus() == ReaderMessage.REQ_PENDING) )
                {
                    Thread.sleep(500);
                    send(query);
                    Thread.sleep(100);
                    answer = getResponse();
                }
                else {
                    System.out.println("Problem while writing packet " + i + " upgrade to SRAM ");
                    System.err.println(answer.toString());
                }
            }
            Log.d("TRACE_OP_UPGRADEFW", "Sending packet : " + i + " of " + (Buffer.length / 128) + "To Sram");
        }

        // Last packet send (cas particulier si pile poil 128)
        if (Buffer.length%128 == 0) {
            query = new Query_Write_Upgrade_To_SRAM(Arrays.copyOfRange(Buffer, Buffer.length - 128, Buffer.length), Buffer.length / 128, true);
        }
        else {
            query = new Query_Write_Upgrade_To_SRAM(Arrays.copyOfRange(Buffer, Buffer.length - (Buffer.length % 128), Buffer.length), Buffer.length / 128, true);
        }
        send(query);
        Thread.sleep(100);
        answer = getResponse();

        if (answer.getStatus() == ReaderMessage.SUCCESS) {
            Log.d("TRACE_OP_UPGRADEFW", "Sending LAST packet To Sram : OK");
        } else {
            Log.d("TRACE_OP_UPGRADEFW", "Problem while writing last packet upgrade to SRAM " + answer.toString());
        }

        query = new Query_Switch_To_DFU_Mode(target);
        send(query);
        Thread.sleep(100);
        answer = getResponse();
        if (answer.getStatus() == ReaderMessage.SUCCESS) {
            Log.d("TRACE_OP_UPGRADEFW", "DFU Command sended : OK / Wait 4 Reebooooooot!");
        } else {
            Log.d("TRACE_OP_UPGRADEFW", "Problem while Reboot to DFU mode " + answer.toString());
        }


    }

    public byte[] getFileContent(String aInputFileName) {
            Log.w("FW_READ", "Reading in binary file named : " + aInputFileName);
            File file = new File(aInputFileName);
            Log.w("FW_READ", "File size: " + file.length());
            byte[] result = new byte[(int)file.length()];
            try {
                InputStream input = null;
                try {
                    int totalBytesRead = 0;
                    input = new BufferedInputStream(new FileInputStream(file));
                    while(totalBytesRead < result.length){
                        int bytesRemaining = result.length - totalBytesRead;
                        //input.read() returns -1, 0, or more :
                        int bytesRead = input.read(result, totalBytesRead, bytesRemaining);
                        if (bytesRead > 0){
                            totalBytesRead = totalBytesRead + bytesRead;
                        }
                    }
        /*
         the above style is a bit tricky: it places bytes into the 'result' array;
         'result' is an output parameter;
         the while loop usually has a single iteration only.
        */
                    Log.w("FW_READ", "Num bytes read: " + totalBytesRead);
                }
                finally {
                    Log.w("FW_READ", "Closing input stream.");
                    input.close();
                }
            }
            catch (FileNotFoundException ex) {
                Log.w("FW_READ", "File not found.");
            }
            catch (IOException ex) {
                Log.w("FW_READ", ex);
            }
        ByteBuffer buf_out = ByteBuffer.allocate(result.length);
        buf_out.put(result);
        buf_out.order(ByteOrder.nativeOrder());
        return buf_out.array();
    }

    public void startCompass_Calibration(char Axis) throws Exception {
        query = new Query_Calibrate_Compass(Axis, ReaderMessage.Compass_Calibration_Start);
        send(query);
        Thread.sleep(100);
        answer = getResponse();
        if (answer.getStatus() == ReaderMessage.SUCCESS) {

            Log.d("TRACE_OP_CALIBRATE", "Starting Calibrate Axis :" + Axis + " ok");
        } else {

            Log.d("TRACE_OP_CALIBRATE", "Calibrate Axis failed with : " + answer.toString());
        }
    }

    public void stopCompass_Calibration(char Axis) throws Exception {
        query = new Query_Calibrate_Compass(Axis, ReaderMessage.Compass_Calibration_Stop);
        send(query);
        Thread.sleep(100);
        answer = getResponse();
        if (answer.getStatus() == ReaderMessage.SUCCESS) {

            Log.d("TRACE_OP_CALIBRATE", "Stopping Calibrating Axis :" + Axis + " ok");
        } else {

            Log.d("TRACE_OP_CALIBRATE", "Stopping Calibrate Axis failed with : " + answer.toString());
        }
    }

    public int getBatteryVoltage() throws Exception {
        query = new Query_GetVoltage();
        send(query);
        Thread.sleep(1000);
        answer = getResponse();
        if (answer.getStatus() == ReaderMessage.SUCCESS) {
            int Voltage = new Answer_GetVoltage(answer).getVoltage();
            Log.d("TRACE_OP_GETBATTERY", "GetBattery ok, value :" + Voltage);
            return Voltage;
        } else {
            Log.d("TRACE_OP_GETBATTERY", "Battery Failed  " + answer.toString());
            return -1;
        }
    }

	public void connect() {
        if (!this.ATTACHED) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        socket.connect();// Tentative de connexion
                        // Connexion réussie
                    } catch (IOException e) {
                        // Echec de la connexion
                        e.printStackTrace();
                    }
                }
            }.start();
            int count = 0;
            while (!socket.isConnected() && (count < 100)) {
                try {
                    Thread.sleep(100);
                    Log.d("TRACE_OP_CONNECT", "Waiting for socket to seattle, try :" + count);
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (count < 100) {
                Log.d("TRACE_OP_CONNECT", "device attached on try :" + count);
                this.ATTACHED = true;
            }
        } else {
            Log.d("TRACE_OP_CONNECT", "device already attached ");
        }

	}

    public void disConnect() {
        if (this.ATTACHED) {

            new Thread() {
                @Override
                public void run() {
                    try {
                        socket.close();// Tentative de deconnexion
                        // deconnexion réussie
                    } catch (IOException e) {
                        // Echec de la deconnexion
                        e.printStackTrace();
                    }
                }
            }.start();
            this.ATTACHED = false;
            Log.d("TRACE_OP_DISCONNECT", "Device disconnected");
        } else {
            Log.d("TRACE_OP_DISCONNECT", "No device connected");
        }
    }


    public Vector<Beacon_Record> getBeaconRecords() throws Exception {
        boolean EOR = false;
        int i = 0;
        Vector<Beacon_Record> Records = new Vector<Beacon_Record>();
        while (EOR != true) {

            query = new Query_Beacon_GetTable(i);
            send(query);
            Thread.sleep(100);
            answer = getResponse();
            if (answer.getStatus() == ReaderMessage.SUCCESS) {
                Beacon_Record record = new Answer_Beacon_GetTable(answer).getRecord(i);
                Log.d("TRACE_OP_GETBEACONRECORD", "GetBeaconRecord ok, value #" + i + " :" + record.toString());
                Records.add(record);
                i++;

            } else {
                Log.d("TRACE_OP_GETBEACONRECORD", "GetBeaconRecord Failed  " + answer.toString());
                EOR = true;
            }
        }
        return Records;
    }
}
    


