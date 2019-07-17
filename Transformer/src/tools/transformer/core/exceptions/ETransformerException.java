package tools.transformer.core.exceptions;

/**
 * root exception for transform 
 */
public class ETransformerException extends RuntimeException{
	private final static long serialVersionUID=1L;
	
	public ETransformerException(String message ){
		super(message);
	}
}
