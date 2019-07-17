package bonpay.jobber.executor.test;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

import bonpay.osgi.service.interf.IModuleExecutor;

/** активатор OSGi bundl-а*/
public class ExecutorActivator implements BundleActivator, 
										  ServiceListener{
	private Logger logger=Logger.getLogger(this.getClass());
	{
		// установить флаг не вывода дублирующих сообщений, при присоединении нескольких Appender-ов (например для bonpay.jobber.executor.test и для bonpay.jobber)
		Logger.getRootLogger().setAdditivity(false);
	}
	private IModuleExecutor moduleExecutor;
	private BundleContext context;
	private Executor executor;
	
	@Override
	public void start(BundleContext context) throws Exception {
		this.context=context;
		this.executor=new Executor();
		logger.debug("добавить слушатель сервиса");
		context.addServiceListener(this);
		logger.debug("проверить регистрацию сервиса");
		try{
			synchronized(this){
				moduleExecutor=(IModuleExecutor)context.getService(context.getServiceReference(IModuleExecutor.class.getName()));
				this.registerExecutor();
			}
		}catch(Exception ex){
			logger.debug("service does not register yet");
		}
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		this.unRegisterExecutor();
	}

	/** ссылка на ресурс */
	private ServiceReference serviceReference;
	
	/** получить индекс из массива */
	private int indexIntoArray(Object[] objects, Object value){
		if((objects==null)||(value==null)){
			return -1;
		}
		for(int counter=0;counter<objects.length;counter++){
			if(objects[counter].equals(value)){
				return counter;
			}
		}
		return -1;
	}
	@Override
	public void serviceChanged(ServiceEvent event) {
		String[] services=(String[])event.getServiceReference().getProperty("objectClass");
		if(indexIntoArray(services,IModuleExecutor.class.getName())>=0){
			if(event.getType()==ServiceEvent.REGISTERED){
				serviceReference=context.getServiceReference(IModuleExecutor.class.getName());
				if(serviceReference!=null){
					synchronized(this){
						moduleExecutor=(IModuleExecutor)context.getService(serviceReference);
						this.registerExecutor();
					}
				}else{
					moduleExecutor=null;
				}
			}else if(event.getType()==ServiceEvent.MODIFIED){
				serviceReference=context.getServiceReference(IModuleExecutor.class.getName());
				if(serviceReference!=null){
					synchronized(this){
						moduleExecutor=(IModuleExecutor)context.getService(serviceReference);
						this.registerExecutor();
					}
				}else{
					moduleExecutor=null;
				}
			}else if(event.getType()==ServiceEvent.UNREGISTERING){
				context.ungetService(serviceReference);
			}
		}
	}

	private void registerExecutor(){
		logger.debug("зарегестрировать Executor");
		this.moduleExecutor.addModuleExecutor("TEST EXECUTOR", this.executor);
	}

	private void unRegisterExecutor(){
		logger.debug("снять регистрацию Executor-a");
		this.moduleExecutor.removeModuleExecutor(this.executor);
	}
	
}
