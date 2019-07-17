package bonpay.osgi.service.implementation;

import java.util.ArrayList;

import bonpay.osgi.service.interf.IExecutor;
import bonpay.osgi.service.interf.IModuleExecutor;

public class ModuleExecutorImpl implements IModuleExecutor{
	private ArrayList<String> listOfName=new ArrayList<String>();
	private ArrayList<IExecutor> listOfExecutor=new ArrayList<IExecutor>();
	
	private void debug(Object value){
		System.out.println("ModuleExecutorImpl DEBUG: "+value);
	}

	private void error(Object value){
		System.out.println("ModuleExecutorImpl ERROR: "+value);
	}
	
	@Override
	public boolean addModuleExecutor(String executorName, IExecutor executor) {
		debug("Register Service: "+executorName);
		boolean returnValue=false;
		if(this.listOfName.indexOf(executorName)<0){
			this.listOfName.add(executorName);
			this.listOfExecutor.add(executor);
			returnValue=true;
		}else{
			// данный объект уже существует
			returnValue=false;
		}
		return returnValue;
	}

	@Override
	public boolean removeModuleExecutor(String executorName) {
		boolean returnValue=false;
		int index=this.listOfName.indexOf(executorName);
		if(index<0){
			returnValue=false;
		}else{
			this.listOfName.remove(index);
			this.listOfExecutor.remove(index);
			returnValue=true;
		}
		return returnValue;
	}

	@Override
	public boolean removeModuleExecutor(IExecutor executor) {
		boolean returnValue=false;
		int index=this.listOfExecutor.indexOf(executor);
		if(index<0){
			returnValue=false;
		}else{
			this.listOfName.remove(index);
			this.listOfExecutor.remove(index);
			returnValue=true;
		}
		return returnValue;
	}

	@Override
	public IExecutor getExecutorByName(String executorName) {
		int index=this.listOfName.indexOf(executorName);
		if(index>=0){
			return this.listOfExecutor.get(index);
		}else{
			error("Service not found: "+executorName);
			return null;
		}
	}

	@Override
	public String[] getNameOfExecutors() {
		return this.listOfName.toArray(new String[]{});
	}

	
}
