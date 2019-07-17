package chess;

/**
 * common exception for a game
 */
public class GameGenericException extends Exception{

	private static final long serialVersionUID = 1L;

	public GameGenericException() {
		super();
	}

	public GameGenericException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public GameGenericException(String arg0) {
		super(arg0);
	}

	public GameGenericException(Throwable arg0) {
		super(arg0);
	}

	
}
