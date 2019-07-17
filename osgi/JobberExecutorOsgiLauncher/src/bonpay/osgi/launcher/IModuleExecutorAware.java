package bonpay.osgi.launcher;

import bonpay.osgi.service.interf.IModuleExecutor;

/** объект, который ведает IModuleExecutor */
public interface IModuleExecutorAware {
	
	/** @return получить IModuleExecutor */
	public IModuleExecutor getModuleExecutor();
}
