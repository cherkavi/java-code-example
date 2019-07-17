package modbus_example;

import net.wimpi.modbus.ModbusCoupler;
import net.wimpi.modbus.util.SerialParameters;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.net.SerialConnection;

public class EnterPoint2 {
	public static void main(String[] args){
		try{
			System.out.println("begin");
			SerialParameters params = new SerialParameters();
			params.setPortName("COM19");
			params.setBaudRate(9600);
			params.setDatabits(7);
			params.setParity("None");
			params.setStopbits(2);
			params.setEncoding("ascii");
			params.setEcho(false);

			int unitid = 1;

			// 2. Set master identifier
			//ModbusCoupler.createModbusCoupler(null);
			ModbusCoupler.getReference().setUnitID(unitid);

			// 4. Open the connection
			SerialConnection con = new SerialConnection(params);
			con.open();

			// 5. Prepare a request
			ReadInputRegistersRequest req = new ReadInputRegistersRequest(1, 0);
			req.setUnitID(unitid);
			// req.setWordCount(2);
			req.setHeadless();

			// 6. Prepare a transaction
			ModbusSerialTransaction trans = new ModbusSerialTransaction(con);
			trans.setRequest(req);
			System.out.println("end");
			trans.execute();
		}catch(Exception ex){
			System.err.println("Exception: "+ex.getMessage());
		}
		System.exit(0);
	}
}
