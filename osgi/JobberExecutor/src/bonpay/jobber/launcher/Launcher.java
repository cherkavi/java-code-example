package bonpay.jobber.launcher;

import org.apache.log4j.Logger;

import bonpay.jobber.launcher.executor.IExecutor;
import bonpay.jobber.launcher.executor.IExecutorAware;
import bonpay.jobber.launcher.settings.ISettingsAware;
import bonpay.jobber.launcher.settings.Settings;
import bonpay.jobber.launcher.settings.SettingsEnum;

/** объект, который запускает по расписанию Задания  */
public class Launcher extends Thread implements ISettingsChangeNotify{
	private Logger logger=Logger.getLogger(this.getClass());
	/** объект, который владеет {@link Settings}*/
	private ISettingsAware settingsAware;
	/** объект, который говорит о том что произошли изменения в настройках для модуля */
	private Boolean flagSettingsChange=false;
	/** время ожидания перед очередным опросом в милисекундах */
	private int timeWait;
	
	/** объект, который ведает исполнителями для запуска */
	private IExecutorAware executorAware;
	
	/** объект, который запускает задания
	 * @param settingsAware - генератов для {@link Settings}
	 * @param executorAware - генератор для 
	 */
	public Launcher(ISettingsAware settingsAware, IExecutorAware executorAware){
		logger.debug("start Launcher");
		this.settingsAware=settingsAware;
		this.executorAware=executorAware;
	}
	
	public void run(){
		this.flagSettingsChange=true;
		while(true){
			// проверить на обновление параметров 
			if(this.flagSettingsChange==true){
				this.updateSettings();
			}
			// получить список объектов, которые нужно запустить 
			IExecutor[] executors=this.executorAware.getExecutors();
			if((executors!=null)&&(executors.length>0)){
				// запуск объектов 
				for(int index=0;index<executors.length;index++){
					if(executors[index]!=null){
						executors[index].execute();
					}
				}
			}else{
				// нет executors для выполнения
				// logger.debug("no executors for execute");
			}
			try{
				Thread.sleep(this.timeWait);
			}catch(Exception ex){};
		}
	}
	
	private void updateSettings(){
		synchronized(this.flagSettingsChange){
			// INFO место загрузки настроек для Launcher
			Settings settings=this.settingsAware.getSettings();
			this.timeWait=settings.getInteger(SettingsEnum.time_wait.toString());
			this.flagSettingsChange=false;
		}
	}

	@Override
	public void notifySettingsChanged() {
		synchronized(this.flagSettingsChange){
			this.flagSettingsChange=true;
		}
	}
}
