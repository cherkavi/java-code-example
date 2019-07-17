package svn_xml_to_db.transit_format;

import java.util.Date;
import java.util.List;

public class LogEntry {
	/** SVN revision */
	private String revision;
	/** author of changes  */
	private String author;
	/** date of changes  */
	private Date date;
	/** comments in SVN  */
	private String message;
	/** task number from SVN - get from {@link #message} */
	private String task;
	/** list of files */
	private List<LogEntryFile> listOfFile=null;
	
	/**
	 * @return the revision
	 */
	public String getRevision() {
		return revision;
	}
	/**
	 * @param revision the revision to set
	 */
	public void setRevision(String revision) {
		this.revision = revision;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the task
	 */
	public String getTask() {
		return task;
	}
	/**
	 * @param task the task to set
	 */
	public void setTask(String task) {
		this.task = task;
	}
	/**
	 * @return the listOfFile
	 */
	public List<LogEntryFile> getListOfFile() {
		return listOfFile;
	}
	/**
	 * @param listOfFile the listOfFile to set
	 */
	public void setListOfFile(List<LogEntryFile> listOfFile) {
		this.listOfFile = listOfFile;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LogEntry [author=" + author + ", date=" + date
				+ ", listOfFile=" + listOfFile + ", message=" + message
				+ ", revision=" + revision + ", task=" + task + "]";
	}
	
	
}
