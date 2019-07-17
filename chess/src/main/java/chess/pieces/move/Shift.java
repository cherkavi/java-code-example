package chess.pieces.move;

import chess.Position;
import chess.board.DekartPosition;

public class Shift {
	private int x;
	private int y;
	
	public Shift(int x, int y){
		this.x=x;
		this.y=y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public static Position moveTo(Position p, Shift shift) {
		DekartPosition position=DekartPosition.convertFrom(p);
		position.setX(position.getX()+shift.x);
		position.setY(position.getY()+shift.y);
		
		if(isCorrectPosition(position)){
			return DekartPosition.convertTo(position);
		}else{
			return null;
		}
	}

	private static boolean isCorrectPosition(DekartPosition position) {
		if( position.getY()+Position.MIN_ROW > Position.MAX_ROW){
			return false;
		}
		if(position.getY()+Position.MIN_ROW<Position.MIN_ROW){
			return false;
		}
		if(position.getX()+Position.MIN_COLUMN>Position.MAX_COLUMN ){
			return false;
		}
		if(position.getX()+Position.MIN_COLUMN<Position.MIN_COLUMN){
			return false;
		}
		return true;
	}
	
}
