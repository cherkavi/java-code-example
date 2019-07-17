package bonpay.jobber.launcher.executor;

/** методы по получению очередных задач для выполнения */
public interface IExecutorAware {
	/** получить исполнителей для запуска */
	public IExecutor[] getExecutors();
}
