package tools.transformer.core.instance;

/**
 *
 *instance of the next in queue from source ( may be 1 or more into source )
 */
public interface IInstance {
	/**
	 * @return - id of instance ( maybe name of table in JDBC, maybe filename .... )
	 */
	String getId();
}
