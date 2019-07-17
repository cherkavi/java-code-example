package bonpay.osgi.launcher.executor;

import bonpay.osgi.service.interf.IExecutor;

/** методы по получению очередных задач для выполнения */
public interface IExecutorAware {
	/** получить исполнителей для запуска */
	public IExecutor[] getExecutors();
}
