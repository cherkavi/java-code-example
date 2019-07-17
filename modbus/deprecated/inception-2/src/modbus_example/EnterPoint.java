package modbus_example;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.ModbusCoupler;

import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.msg.ModbusRequest;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.util.SerialParameters;


public class EnterPoint {
	  public static void main(String[] args) {
			args=new String[]{"COM4","1","0","1","1"};
		    SerialConnection con = null;
		    ModbusSerialTransaction trans = null;
		    ModbusRequest req = null;
		    ReadInputRegistersResponse res = null;

		    String portname = null;
		    int unitid = 0;
		    int ref = 0;
		    int count = 0;
		    int repeat = 1;

		    try {

		      //1. Setup the parameters
		      if (args.length < 4) {
		        printUsage();
		        System.exit(1);
		      } else {
		        try {
		          portname = args[0];
		          unitid = Integer.parseInt(args[1]);
		          ref = Integer.parseInt(args[2]);
		          count = Integer.parseInt(args[3]);
		          if (args.length == 5) {
		            repeat = Integer.parseInt(args[4]);
		          }
		        } catch (Exception ex) {
		          ex.printStackTrace();
		          printUsage();
		          System.exit(1);
		        }
		      }

		      //2. Set slave identifier for master response parsing
		      // ModbusCoupler.getReference().setMaster(true);
		      ModbusCoupler.getReference().setUnitID(unitid);
		      System.setProperty("net.wimpi.modbus.debug", "true");

		      //System.setProperty("net.wimpi.true", "true");
		      System.out.println("net.wimpi.true set to: " +
		                         System.getProperty("net.wimpi.true"));

		      //3. Setup serial parameters
		      SerialParameters params = new SerialParameters();
		      params.setPortName(portname);
		      params.setBaudRate(9600);
		      params.setDatabits(7);
		      params.setParity("none");
		      params.setStopbits(2);
		      params.setEncoding(Modbus.SERIAL_ENCODING_RTU);
		      params.setEcho(false);
		      
		      if (true) System.out.println("Encoding [" + params.getEncoding() + "]");

		      //4. Open the connection
		      con = new SerialConnection(params);
		      con.open();

		      //5. Prepare a request
		      req = new ReadMultipleRegistersRequest(ref, count);
		      req.setUnitID(unitid);
		      req.setHeadless();
		      if (true) System.out.println("Request: " + req.getHexMessage());

		      //6. Prepare the transaction
		      trans = new ModbusSerialTransaction(con);
		      trans.setRequest(req);
		      //7. Execute the transaction repeat times
		      int k = 0;
		      do {
		        trans.execute();

		        res = (ReadInputRegistersResponse) trans.getResponse();
		        if (true)
		          System.out.println("Response: " + res.getHexMessage());
		        for (int n = 0; n < res.getWordCount(); n++) {
		          System.out.println("Word " + n + "=" + res.getRegisterValue(n));
		        }
		        k++;
		      } while (k < repeat);

		    } catch (Exception ex) {
		      ex.printStackTrace();
		    } finally{
		    	// Close the connection
		    	con.close();
		    }
		    System.exit(0);
		  }//main

		  private static void printUsage() {
		    System.out.println(
		        "java net.wimpi.modbus.cmd.SerialAITest <portname [String]>  <Unit Address [int8]> <register [int16]> <wordcount [int16]> {<repeat [int]>}"
		    );
		  }//printUsage
}
