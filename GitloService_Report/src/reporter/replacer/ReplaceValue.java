package reporter.replacer;

/** класс, который служит потомком для всех значений, которые подлежат замене */
public abstract class ReplaceValue {
	/** получить значение для замены в HTML файле */
	public abstract String getReplaceValue();
}
