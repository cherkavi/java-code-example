package bonpay.mail.sender_core.manager;

/** фабрика, которая производит объекты ILetter Aware  */
public interface ILetterAwareFactory {
	/** получить новый объект класса ILetterAware  */
	public ILetterAware createNewLetterAware();
}
