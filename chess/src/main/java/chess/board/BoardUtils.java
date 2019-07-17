package chess.board;

import chess.Position;

/**
 * utility class for board
 */
public class BoardUtils {
	
	private BoardUtils(){
	}
	
	/**
	 * get X coordinate for position
	 * @param position
	 * @return
	 */
	public static int getX(Position position){
		return position.getColumn()-Position.MIN_COLUMN;
	}
	
	/**
	 * get Y coordinate for position 
	 * @param position
	 * @return
	 */
	public static int getY(Position position){
		return position.getRow()-Position.MIN_ROW;
	}
}
