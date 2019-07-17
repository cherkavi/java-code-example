package bonpay.jobber.executor;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

import bonpay.mail.sender_core.MailExecutor;
import bonpay.osgi.service.interf.IExecutor;
import bonpay.osgi.service.interf.IModuleExecutor;

public class ExecutorActivator implements BundleActivator, ServiceListener{
	private Logger logger=Logger.getLogger(this.getClass());
	private IModuleExecutor moduleExecutor;
	private BundleContext context;
	private IExecutor executor;
	private String executorName="MAIL_SENDER";

	@Override
	public void start(BundleContext context) throws Exception {
		logger.debug("start begin:");
		try{
			this.context=context;
			this.executor=new MailExecutor("MailExecutor.properties");
			logger.debug("�������� ��������� �������");
			context.addServiceListener(this);

			logger.debug("��������� ����������� �������");
			synchronized(this){
				moduleExecutor=(IModuleExecutor)context.getService(context.getServiceReference(IModuleExecutor.class.getName()));
				this.registerExecutor();
			}
		}catch(Exception ex){
			logger.warn("service does not register yet");
		}
		logger.debug("start end:");
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		logger.debug("stop begin");
		try{
			this.executor.stop();
		}catch(Exception ex){};
		this.unRegisterExecutor();
		logger.debug("stop end");
	}

	/** ������ �� ������ */
	private ServiceReference serviceReference;
	
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
		this.moduleExecutor.addModuleExecutor(this.executorName, this.executor);
	}

	private void unRegisterExecutor(){
		this.moduleExecutor.removeModuleExecutor(this.executor);
	}
	
}
