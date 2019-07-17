package svn_xml_to_db.transit_format;

/** 
 * reflection of /log/logentry/paths/path
 */
public class LogEntryFile {
	/** attribute kind */
	private String kind;
	/** attribute action  */
	private String action;
	/** text content path  */
	private String path;
	/**
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}
	/**
	 * @param kind the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

	@Override
	public String toString() {
		return "LogEntryFile [action=" + action + ", kind=" + kind + ", path="
				+ path + "]";
	}
	
	
}
