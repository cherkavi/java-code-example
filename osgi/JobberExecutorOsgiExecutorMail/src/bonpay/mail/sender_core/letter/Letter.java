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
	/** полный путь к файлу HTML с текстом  */
	private LetterAttachment textAttachment;
	/** ѕуть к прикрепленным файлам  */
	private ArrayList<LetterAttachment> pathToAttachment=new ArrayList<LetterAttachment>();
	/** адресаты  */
	private ArrayList<String> recipient=new ArrayList<String>();
	/** флаг ошибочных передач */
	private int errorCounter=0;
	/** €вл€етс€ ли письмо HTML текстом  */
	private boolean htmlFormat=true;
	
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
	 * @param attachment - присоедин€емый файл  
	 * @throws если не найден файл на диске, либо же нет оригинального имени файла дл€ добавлени€ 
	 */
	public void addAttachmentFile(LetterAttachment attachment) throws Exception {
		File f=new File(attachment.getPathToFile());
		if(f.exists()==false){
			throw  new Exception("file not found");
		}
		if(attachment.getOriginalFileName()==null){
			attachment.setOriginalFileName(f.getName());
		}
		this.pathToAttachment.add(attachment);
	}
	
	/** получить кол-во прикрепленных файлов */
	public int getAttachmentSize(){
		return this.pathToAttachment.size();
	}
	
	/** получить путь к прикрепленному файлу по индексу  */
	public LetterAttachment getAttachment(int index){
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

	/** получить флаг, который показывает кол-во передач с ошибками */
	public int getErrorCounter() {
		return errorCounter;
	}

	/** установить флаг, который показывает кол-во передач с ошибками */
	public void setErrorCounter(int errorCounter) {
		this.errorCounter = errorCounter;
	}

	/** 
	 * <ul>
	 * <li><b>true</b> - установить формат письма как HTML </li>
	 * <li><b>false</b> - (default) установить формат как текст </li>
	 */
	public void setHtml(boolean value){
		this.htmlFormat=value;
	}
	
	/** 
	 * <ul>
	 * <li><b>true</b> - формат письма как HTML </li>
	 * <li><b>false</b> - (default) формат как текст </li>
	 */
	public boolean isHtml(){
		return this.htmlFormat;
	}

	/** получить полный путь к файлу, который содержит HTML код, который нужно вставить в текст   */
	public LetterAttachment getAttachmentHtmlText() {
		return this.textAttachment;
	}

	/** установить полный путь к файлу HTML который содержит HTML код дл€ отправки  */
	public void setAttachmentHtmlText(LetterAttachment attachment) {
		this.textAttachment=attachment;
	}
	
}
