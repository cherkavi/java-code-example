package bonpay.mail.sender_core.manager;

/** уникальный идентификатор Sender-а, по которому можно понять какой именно записи он соответсвует */
public class SenderIdentifier {
	private int id;
	
	/** уникальный идентификатор Sender-а, по которому можно понять какой именно записи он соответсвует 
	 * @param recordId - уникальный идентификатор в базе данных (id записи )
	 * */
	public SenderIdentifier(int recordId){
		this.id=recordId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null){
			return false;
		}
		if(obj instanceof SenderIdentifier){
			return ((SenderIdentifier)obj).id==this.id;
		}
		return false;
	}
	
	/** получить уникальный идентификатор в масштабе базы данных */
	public int getId(){
		return this.id;
	}
}
