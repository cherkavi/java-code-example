package chess.board;

import chess.GameGenericException;

public class BoardException extends GameGenericException{

	private static final long serialVersionUID = 1L;

	public BoardException() {
		super();
	}

	public BoardException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BoardException(String arg0) {
		super(arg0);
	}

	public BoardException(Throwable arg0) {
		super(arg0);
	}

	
}
