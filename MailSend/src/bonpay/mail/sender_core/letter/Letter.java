package bonpay.mail.sender_core.letter;

import java.io.File;
import java.util.ArrayList;

/** письмо, которое должно быть отправлено */
public class Letter {
	/** “ема письма */
	private String subject="";
	/** “екст письма */
	private String text="";
	/** текстовое состо€ние письма */
	private String state="";
	/** ѕуть к прикрепленным файлам  */
	private ArrayList<String> pathToAttachment=new ArrayList<String>();
	/** адресаты  */
	private ArrayList<String> recipient=new ArrayList<String>();
	
	/** письмо, которое должно быть отправлено */
	public Letter(){
	}

	/** добавить получател€ */
	public void addRecipient(String recipient){
		this.recipient.add(recipient);
	}
	
	/** получить общее кол-во получателей */
	public int getRecipientSize(){
		return this.recipient.size();
	}
	
	/** получить получател€ по индексу */
	public String getRecipient(int index){
		try{
			return this.recipient.get(index);
		}catch(Exception ex){
			return null;
		}
	}
	
	/** удалить получател€ из списка */
	public void removeRecipient(int index){
		try{
			this.recipient.remove(index);
		}catch(IndexOutOfBoundsException ex){
		}
	}
	
	/** добавить файл, который будет прикреплен к письму на отправке 
	 * @param pathToFile - путь к файлу 
	 */
	public void addAttachmentFile(String pathToFile) throws Exception {
		File f=new File(pathToFile);
		if(f.exists()==false){
			throw  new Exception("file not found");
		}
		this.pathToAttachment.add(pathToFile);
	}
	
	/** получить кол-во прикрепленных файлов */
	public int getAttachmentSize(){
		return this.pathToAttachment.size();
	}
	
	/** получить прикрепленный файл по индексу  */
	public String getAttachment(int index){
		try{
			return this.pathToAttachment.get(index);
		}catch(IndexOutOfBoundsException ex){
			return null;
		}
	}
	
	/** удалить прикрепленный файл по индексу */
	public void removeAttachment(int index){
		try{
			this.pathToAttachment.remove(index);
		}catch(IndexOutOfBoundsException ex){
		}
	}
	
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/** установить состо€ние письма 
	 * <li> <b>NEW</b> новое </li>
	 * */
	public void setState(String state) {
		this.state=state;
	}
	
	/** получить состо€ние письма */
	public String getString(){
		return this.state;
	}
}
