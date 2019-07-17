package bonpay.jobber.launcher.executor;

/** интерфейс, который отвечает за запуск задач - классы должны его наследовать чтобы можно было их запускать по расписанию */
public interface IExecutor {
	/** запустить задачу */
	public void execute();
	/** остановить задачу */
	public void stop();
}
