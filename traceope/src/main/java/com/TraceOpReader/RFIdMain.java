/**
 *
 */
package com.TraceOpReader;

/**
 * @author nfl
 */
public class RFIdMain {
    //static CommPort commPort;
//	static RFIDReader reader = new RFIDReader();

//	static void connect(String portName) throws IOException, PortInUseException, NoSuchPortException {
//
//			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
//	        if ( portIdentifier.isCurrentlyOwned() )
//	        {
//	            System.out.println("Error: Port is currently in use");
//	        }
//	        else
//	        {
//	            commPort = portIdentifier.open(portIdentifier.getName(),2000);
//	            
//	            if ( commPort instanceof SerialPort )
//	            {
//	                SerialPort serialPort = (SerialPort) commPort;
//	                
//	                reader.connect(serialPort.getInputStream(),serialPort.getOutputStream());
//	                
//	            }
//	            else
//	            {
//	                System.out.println("Error: Only serial ports are handled by this example.");
//	            }
//	        }     
//	}

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
        System.out.println("Ok");


        try {
//			connect("/dev/ttyUSB0");
//			reader.turnON_RADIO();
//			Vector <Tag> myTags = reader.GetTags();
//			Iterator <Tag> iterator= myTags.iterator();
//			Tag current_Tag = new Tag();
//			if (iterator.hasNext()) {
//				current_Tag = iterator.next();
//				reader.readTag(current_Tag);
//				System.out.println("Tag readed...");
//			}
//			
//			iterator = myTags.iterator();
//			if (iterator.hasNext()) {
//				current_Tag = iterator.next();
//				System.out.println(current_Tag.toString());
//			}
//			
//			byte[] Buffer = current_Tag.getContent().toByteArray();
//			java.util.Arrays.fill(Buffer, (byte) 0xcc);
//			current_Tag.setContent(Buffer);
//			
//			System.out.println(current_Tag.toString());
//			
//			reader.writeTag(current_Tag);
//			
//			reader.readTag(current_Tag);
//			
//			System.out.println(current_Tag.toString());
//			
//			reader.turnOFF_RADIO();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println(e.toString());

        }
        close();
    }


    private static void close() {
//			commPort.close();
    }

}
