package skype_caller.manager;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import skype_caller.command_generator.GeneratorContainer;
import skype_caller.command_generator.ACommandGenerator;

public class GeneratorManager implements IGeneratorObserver, Runnable{
	
	/** command line can consists the name of the settings file  */
	public static void main(String[] args){
		// read Spring container
		AbstractApplicationContext context =null;
		String fileOfSpringConfiguration="settings.xml";
		if(args.length!=0){
			fileOfSpringConfiguration=args[0];
			if(args.length>=2){
				PropertyConfigurator.configure(args[1]);
			}else{
				BasicConfigurator.configure();
			}
		}else{
			fileOfSpringConfiguration="settings.xml";
			BasicConfigurator.configure();
		}
		context=new FileSystemXmlApplicationContext(new String[] { fileOfSpringConfiguration });
		
		GeneratorContainer container=context.getBean(GeneratorContainer.class);
		new Thread(new GeneratorManager(container.getListOfCommandGenerator())).start();
	}
	// -------------------------------------------------

	private Logger logger=Logger.getLogger(GeneratorManager.class);
	private LinkedList<ACommandGenerator> queueCommand= new LinkedList<ACommandGenerator>();
	
	
	public GeneratorManager(List<ACommandGenerator> listOfGenerators){
		logger.info("program begin ");
		// process all generators
		boolean needToExecute=true;
		for(ACommandGenerator generator : listOfGenerators){
			needToExecute&=this.addGeneratorForExecuteLater(generator);
		}
		if(needToExecute==false){
			logger.warn("all generator's is empty ");
			System.exit(0);
		}
		controlGeneratorCount=true;
	}
	private volatile int generatorCount=0;
	private volatile boolean controlGeneratorCount=false;
	private volatile boolean noCommandForAddToQueueInFuture=false;
	
	/** add the generator for execute in decoupled Runnable task 
	 * @param generator
	 * @return
	 * <ul>
	 * 	<li><b>true</b> - was add for execute in future</li>
	 * 	<li><b>false</b> - generator is empty </li>
	 * </ul>
	 */
	private boolean addGeneratorForExecuteLater(ACommandGenerator generator){
		logger.debug("create new executors for command:"+generator);
		Long delayBeforeLaunch=generator.getSecondsSleepTimeBeforeExecuteCommand();
		logger.debug("need to delay in seconds:"+delayBeforeLaunch);
		if((delayBeforeLaunch!=null)&&(delayBeforeLaunch>0)){
			generatorCount++;
			logger.debug("need to execute generator in future:"+generator); 
			Executors.newScheduledThreadPool(1).schedule(new LaterExecuteGenerator(this, generator), 
					 									 delayBeforeLaunch, 
					 									 TimeUnit.SECONDS);
			return true;
		}else{
			logger.debug("generator is done - don't need to execute in future:"+generator);
			if(controlGeneratorCount==true){
				if(generatorCount<=0){
					logger.info("all generator's was executed ");
					noCommandForAddToQueueInFuture=true;
				}
			}
			return false;
		}
	}

	@Override
	public void needToExecuteGenerator(ACommandGenerator generator) {
		generatorCount--;
		synchronized (this) {
			logger.debug("add to queue the generator:"+generator);
			this.queueCommand.addLast(generator);
			logger.debug("pulse for process the queue");
			this.notify();
		}
		logger.debug("add generator for execute later ");
		this.addGeneratorForExecuteLater(generator);
	}
	
	
	public void run(){
		while(true){
			this.logger.debug("process the queue");
			ACommandGenerator generator=null;
			synchronized(this){
				if(!this.queueCommand.isEmpty()){
					generator=this.queueCommand.removeFirst();
					this.logger.debug("generator was retrieved from queue:"+generator);
				}else{
					this.logger.debug("queue is empty ");
				}
			}
			if(generator!=null){
				logger.info("execute command from generator: "+generator.toString());
				generator.getCommand().execute();
			}
			synchronized (this) {
				if(this.queueCommand.isEmpty()){
					if(this.noCommandForAddToQueueInFuture==true){
						logger.info("all command was executed END OF PROGRAM");
						System.exit(0);
					}else{
						logger.debug("wait for add command to queue");
						try{
							this.wait();
						}catch(InterruptedException ie){};
					}
				}else{
					this.logger.debug("process the next command ");
					continue;
				}
			}
		}
	}
}

