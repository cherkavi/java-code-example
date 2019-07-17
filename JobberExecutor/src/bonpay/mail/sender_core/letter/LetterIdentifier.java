package bonpay.mail.sender_core.letter;

/** уникальный идентификатор письма */
public class LetterIdentifier {
	private int id;
	/** уникальный идентификатор письма */
	public LetterIdentifier(int id){
		this.id=id;
	}
	/**
	 * @return получить уникальный идентификатор письма 
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param установить уникальный идентификатор письма 
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	
}
