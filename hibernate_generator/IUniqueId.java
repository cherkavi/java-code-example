package ua.cetelem.helpers.sequence_generator;

/** marker interface for {@link DefaultSequenceGenerator} for identify all entity with getter and setter for Id */
public interface IUniqueId {
	public void setId(long id);
	public long getId();
}
