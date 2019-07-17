package com.cherkashyn.vitalii.weapon.phone;

import com.skype.Call;
import com.skype.Call.Status;
import com.skype.CallMonitorListener;
import com.skype.Skype;
import com.skype.SkypeException;

/**
 * Hello world!
 *
 */
public class App 
{
	   public static void main(String[] args) throws Exception {
	        Skype.setDaemon(false);
	        
			// BUG !!! should be added
	        Skype.addCallMonitorListener(new CallMonitorListener(){
				@Override
				public void callMonitor(Call call, Status status)
						throws SkypeException {
			}
	        });
	        
	        Call currentCall=Skype.call("echo123");
	        currentCall.addCallMonitorListener(new CallMonitorListener(){
				@Override
				public void callMonitor(Call call, Status status)
						throws SkypeException {
					System.out.println("local status: "+status);
					if(Status.INPROGRESS.equals(status)){call.finish();}
			}
	        });
	    }
	   
}
