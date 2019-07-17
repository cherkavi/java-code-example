package snmp_protocol;

import org.snmp4j.CommunityTarget; 
import org.snmp4j.PDU; 
import org.snmp4j.Snmp; 
import org.snmp4j.TransportMapping; 
import org.snmp4j.event.ResponseEvent; 
import org.snmp4j.mp.SnmpConstants; 
import org.snmp4j.smi.*; 
import org.snmp4j.transport.DefaultUdpTransportMapping; 

public class SnmpSetGet {
	private final static String HOST="192.168.0.10";
	private final static int SNMP_PORT=161;
	public static final String PUBLIC_COMMUNITY = "public"; 
    public static final String WRITE_COMMUNITY= "private";
    
    // 1.3.6.1.2.1.1.1.0
    public final static String SysDescr="1.3.6.1.2.1.1.1.0";
    public final static String SysUpTime="1.3.6.1.2.1.1.3.0";
    public final static String SysContact="1.3.6.1.2.1.1.4.0";
    public final static String SysMACAddress="1.3.6.1.4.1.17484.2.1.2.0";
    public final static String SysIPAddress="1.3.6.1.4.1.17484.2.1.3.0";
    public final static String SysIPmask="1.3.6.1.4.1.17484.2.1.4.0";
    public final static String SysGateIPaddress="1.3.6.1.4.1.17484.2.1.5.0";
    public final static String SysTrapIPaddress="1.3.6.1.4.1.17484.2.1.6.0";
    public final static String SysSnmpRdComm="1.3.6.1.4.1.17484.2.1.8.0";
    public final static String SysSnmpWrComm="1.3.6.1.4.1.17484.2.1.9.0";
    public final static String SysSnmpTrapComm="1.3.6.1.4.1.17484.2.1.10.0";
    
	public static void main(String[] args){
		System.out.println("begin");
		/*
		System.out.println("before:"+snmpGet(PUBLIC_COMMUNITY, 
									SysTrapIPaddress
						   			)
						   );
		System.out.println("set Trap message IP address:"+snmpSet(PUBLIC_COMMUNITY, SysTrapIPaddress, new IpAddress("192.168.0.7")));
		System.out.println("after:"+snmpGet(PUBLIC_COMMUNITY, 
										    SysTrapIPaddress
	   										)
	   );*/
		System.out.println("Value:"+snmpGet(PUBLIC_COMMUNITY, 
										    SysSnmpTrapComm
	   										)
						   );
		
		System.out.println("end");
	}
	
	
	/**
	 * @param community
	 * @param strOID
	 * @param value
	 * @return
	 * <ul>
	 * 	<li></li>
	 * </ul>
	 */
	public static boolean snmpSet(String community, String strOID, String value){
		return snmpSet( community, strOID, new OctetString(value));
	}
	public static boolean snmpSet(String community, String strOID, int value){
		return snmpSet(community, strOID, new Integer32(value));
	}
	
	/*
	 * PDU pdu = new ScopedPDU();
       USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
       SecurityModels.getInstance().addSecurityModel(usm);
       snmp = new Snmp(new DefaultUdpTransportMapping());
       snmp.getUSM().addUser(new OctetString(Username), new UsmUser(new OctetString(Username), AuthMD5.ID, new OctetString(Password), AuthMD5.ID, null));


       ScopedPDU scopedPDU = (ScopedPDU) pdu;
       scopedPDU.setType(PDU.SET);
       scopedPDU.addAll(bindings);
       UserTarget target = new UserTarget();
       target.setAddress(new UdpAddress(IPAddress + "/" + Port));
       target.setVersion(version); //SnmpConstants.version3
       target.setRetries(retries);
       target.setTimeout(timeout);
       target.setSecurityLevel(securityLevel); //SecurityLevel.AUTH_NOPRIV
       target.setSecurityName(new OctetString(Username));
       snmp.listen();
	 * 
	 * 
	 */
	public static boolean snmpSet(String community, String strOID, AbstractVariable value){
		String strAddress=HOST+"/"+SNMP_PORT; 
		Address targetAddress = GenericAddress.parse(strAddress); 
		Snmp snmp; 
		try{
			TransportMapping transport = new DefaultUdpTransportMapping(); 
			snmp = new Snmp(transport);
			// set synchronous communications
			transport.listen(); 
			
			CommunityTarget target = new CommunityTarget(); 
			target.setCommunity(new OctetString(community)); 
			target.setAddress(targetAddress); 
			target.setRetries(2); 
			target.setTimeout(5000); 
			target.setVersion(SnmpConstants.version1);
			      
			PDU pdu = new PDU(); 
			pdu.add(new VariableBinding(new OID(strOID), value)); 
			pdu.setType(PDU.SET);
			
			/*
			ResponseListener listener = new ResponseListener() { 
				public void onResponse(ResponseEvent event) {
					System.out.println("Event:"+event);
					System.out.println("UserObject:"+event.getUserObject());
					System.out.println("Request:"+event.getRequest());
					System.out.println("Response:"+event.getResponse());
					// Always cancel async request when response has been received 
					// otherwise a memory leak is created! Not canceling a request 
					// immediately can be useful when sending a request to a broadcast 
					// address. 
					// if(event.getResponse()==null)return;
					((Snmp)event.getSource()).cancel(event.getRequest(), this);
			        // System.out.println("Set Status is:   "+event.getResponse().getErrorStatusText()); 
				} 
			}; 
			snmp.send(pdu, target, null, listener);
			*/
			ResponseEvent response=snmp.set(pdu, target);
			if (response != null){
		      System.out.println("\nResponse:\nGot Snmp Set Response from Agent");
		      PDU responsePDU = response.getResponse();
		      if (responsePDU != null){
		        int errorStatus = responsePDU.getErrorStatus();
		        int errorIndex = responsePDU.getErrorIndex();
		        String errorStatusText = responsePDU.getErrorStatusText();
		        if (errorStatus == PDU.noError){
		          System.out.println("Snmp Set Response = " + responsePDU.getVariableBindings());
		        }
		        else{
		          System.out.println("Error: Request Failed");
		          System.out.println("Error Status = " + errorStatus);
		          System.out.println("Error Index = " + errorIndex);
		          System.out.println("Error Status Text = " + errorStatusText);
		        }
		      }
		      else{
		        System.out.println("Error: Response PDU is null");
		      }
		    }
		    else{
		      System.out.println("Error: Agent Timeout... ");
		    }			
			snmp.close();
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	
	 /* 
	  * The code is valid only SNMP version1. SnmpGet method  
	  * return Response for given OID from the Device. 
	  */   
	public static String snmpGet(String community, String strOID) {
		 String str=""; 
         try{ 
		     String strAddress= HOST+"/"+SNMP_PORT; 
		     Address targetaddress = new UdpAddress(strAddress); 
		     TransportMapping transport = new DefaultUdpTransportMapping();
		     // set synchronous communications
		     transport.listen(); 
		        
		     CommunityTarget comtarget = new CommunityTarget(); 
		     comtarget.setCommunity(new OctetString(community)); 
		     comtarget.setVersion(SnmpConstants.version1); 
		     comtarget.setAddress(targetaddress); 
		     comtarget.setRetries(2); 
		     comtarget.setTimeout(5000); 
		        
		     PDU pdu = new PDU(); 
		     pdu.add(new VariableBinding(new OID(strOID))); 
		     pdu.setType(PDU.GET); 
		     Snmp snmp = new Snmp(transport); 
		     ResponseEvent response = snmp.get(pdu,comtarget); 
		     if(response != null){    
		    	 if(response.getResponse().getErrorStatusText().equalsIgnoreCase("Success")){ 
		    		 PDU pduresponse=response.getResponse();
		    		 str=pduresponse.getVariableBindings().firstElement().toString(); 
		    		 if(str.contains("=")){ 
		    			 int len = str.indexOf("="); 
		    			 str=str.substring(len+1, str.length()); 
		    		 } 
		         } 
		     }else{  
		    	 System.out.println("Feeling like a TimeOut occured "); 
		     } 
		     snmp.close(); 
         }catch(Exception e)  {  
        	 e.printStackTrace();    
         } 
		 return str; 
	} 
	
}
