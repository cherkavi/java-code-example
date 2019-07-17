package skype_caller.command;

import org.apache.log4j.Logger;

import com.skype.Call;
import com.skype.CallListener;
import com.skype.CallStatusChangedListener;
import com.skype.Skype;
import com.skype.SkypeException;
import com.skype.Call.Status;
import com.skype.Call.Type;

public class CallAndDrop extends ASkypeCommand implements CallStatusChangedListener, CallListener{
	private Logger logger=Logger.getLogger(this.getClass());
	private Call skypeCall;
	private Long callTimeOut=null;
	
	@Override
	public void execute() {
		Skype.setDaemon(false);
		try{
			Skype.addCallListener(this);
			// logger.debug("try to add call listener");
			logger.debug("call to: "+this.getRecipient());
			Skype.call(this.getRecipient());
			synchronized (this) {
				if(this.callTimeOut!=null){
					logger.debug("wait for execute call or drop after ("+this.callTimeOut+")");
					this.wait(this.callTimeOut);
				}else{
					logger.debug("wait for execute call");
					this.wait();
				}
			}
			logger.debug("drop the call");
			if(isRefused==false){
				skypeCall.finish(); // кладет трубку
			}
			skypeCall.removeCallStatusChangedListener(this);
		}catch(Exception ex){
			logger.warn("Make a call Exception:"+ex.getMessage(), ex);
			try{
				skypeCall.finish();
			}catch(Exception ex2){};
		}finally{
			isRefused=false;
			try{
				Skype.removeCallListener(this);
			}catch(Exception ex){}
		}
	}
	
	private boolean isRefused=false;
	// TODO if Skype is dropped down
	@Override
	public void statusChanged(Status callStatus) throws SkypeException {
		logger.info("new call status: "+callStatus+"  for "+this);
		synchronized (this) {
			if(Status.REFUSED.equals(callStatus)){
				logger.info("REFUSED - remote user was broken the call ");
				this.skypeCall.removeCallStatusChangedListener(this);
				this.notify();
				this.isRefused=true;
			}

			if(Status.FINISHED.equals(callStatus)){
				logger.info("FINISHED - the call was dropped");
				this.skypeCall.removeCallStatusChangedListener(this);
				this.notify();
			}
			
			if(Status.INPROGRESS.equals(callStatus)){
				this.logger.info("INPROGRESS - remove user answer to call ");
				this.skypeCall.removeCallStatusChangedListener(this);
				logger.info("Drop the call for "+this.getRecipient());
				// skypeCall.cancel(); // кладет трубку 
				this.notify();
			}
			if(Status.CANCELLED.equals(callStatus)){
				logger.info("CANCELLED - skype was dropped the call");
				this.skypeCall.removeCallStatusChangedListener(this);
				this.notify();
			}
		}
	}

	/** set time out for the call in seconds */
	public void setCallTimeOut(Long value){
		if(value!=null){
			this.callTimeOut=value*1000;
		}
	}

	@Override
	public void callMaked(Call call) throws SkypeException {
		if(    Status.ROUTING.equals(call.getStatus()) 
			&& (  Type.OUTGOING_PSTN.equals(call.getType()) // call to NOT Skype
				||Type.OUTGOING_P2P.equals(call.getType())) // call to Skype
				){
			logger.debug("outgoing call detected ");
			this.skypeCall=call;
			skypeCall.addCallStatusChangedListener(this);
		}else{
			logger.debug("#callMaked :  Status:"+call.getStatus()+"   Type:"+call.getType());
		}
	}

	@Override
	public void callReceived(Call call) throws SkypeException {
		call.finish();
	}

}
