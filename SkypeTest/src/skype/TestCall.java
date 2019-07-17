package skype;


import com.skype.Call;
import com.skype.CallListener;
import com.skype.CallStatusChangedListener;
import com.skype.Skype;
import com.skype.SkypeException;
import com.skype.Call.Status;

public class TestCall {
	public static void main(String[] args){
		System.out.println("begin");
		try {
			Skype.setDaemon(false);
			// get version 
			System.out.println("Skype Version: "+Skype.getVersion());
			// add call listener
			Skype.addCallListener(new CallListener(){
				@Override
				public void callMaked(Call call) throws SkypeException {
					final Call skypeCall=call;
					System.out.println(" callMaked ");
					System.out.println("Status: "+call.getStatus()); // ROUTING - звонок
					System.out.println("Type: "+call.getType()); // OUTGOING_PSTN - исходящий голосовой 
					System.out.println("Call Id: "+call.getId()); // идентификатор звонка - ( Номер, например: 361261 )
					System.out.println("DisplayName:"+call.getPartnerDisplayName()); // DisplayName of partner:+61396228888 ( при звонке на номер )
					call.addCallStatusChangedListener(new CallStatusChangedListener(){
						@Override
						public void statusChanged(Status status) throws SkypeException {
							System.out.println("New Status: "+status);
							if(Status.INPROGRESS.equals(status)){
								System.out.println("Drop the call ");
								// skypeCall.cancel(); // кладет трубку 
								skypeCall.finish(); // кладет трубку   
							}
						}
					});
					/*
					
					ScheduledExecutorService thread = Executors.newSingleThreadScheduledExecutor();
					Runnable cancelSkypeCall=new Runnable(){
						@Override
						public void run() {
							try{
								skypeCall.cancel();
							}catch(Exception ex){
								System.out.println("Cancel Exception:"+ex.getMessage());
							}
						}
					};
					thread.schedule(cancelSkypeCall, 15, TimeUnit.SECONDS);
					*/
				}

				@Override
				public void callReceived(Call call) throws SkypeException {
					System.out.println(" callReceived ");
					call.cancel();
				}
				
			});
			// make a call 
			// Skype.call("+380954671434");
			// Skype.call("+61396228888");
			Skype.call("ganulyak_i");
		} catch (SkypeException ex) {
			ex.printStackTrace();
			System.out.println("Call to Skype Exception: "+ex.getMessage());
		}
		System.out.println("-end-");
	}
}

/*
 * Алгоритм работы со Skype:
 * добавить слушателя для звонков 
 * 	Skype.addCallListener(new CallListener(){
 		-- при возникновении исходящео звонка 
  		call.getStatus(): ROUTING
  		call.getType(): OUTGOING_PSTN
  		
  		-- проверить номер исходящего звонка:
  		call.getId()
  		call.getPartnerDisplayName()
  			-- добавить слушателя для изменения статуса по данному звонку:
  			call.addCallStatusChangedListener(new CallStatusChangedListener()
  			статусы:
  				"ROUTING" - набор номера 
				"EARLYMEDIA" - ожидание соединения для звонка на стационар
				"RINGING" - ожидание соединения на звонки в Skype 
				"REFUSED" - удаленный абонент сбросил соединение  
				"INPROGRESS" - пользователь ответил на звонок 
				"FINISHED" - завершение звонка со стороны инициатора ( инициатор - данное ПО ) 
 */
