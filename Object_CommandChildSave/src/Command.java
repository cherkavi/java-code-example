import java.io.Serializable;


/** класс, потомки которого должны выполнять поставленные задачи */
abstract public class Command implements Serializable{
	private final static long serialVersionUID=1L;
	private String commandName;
	
	public Command(String commandName){
		this.commandName=commandName;
	}
	
	/** попытаться выполнить основное действие */
	public abstract boolean action();
	/** получить результат выполнения основного действия */
	public abstract int getResult();
}
