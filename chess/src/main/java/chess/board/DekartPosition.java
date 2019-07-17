package chess.board;

import chess.Position;

public class DekartPosition {
	int x;
	int y;
	
	DekartPosition(){
	}
	
	DekartPosition(int posX, int posY){
		this.x=posX;
		this.y=posY;
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

	public static DekartPosition convertFrom(Position position){
		DekartPosition returnValue=new DekartPosition();
		returnValue.x=BoardUtils.getX(position);
		returnValue.y=BoardUtils.getY(position);
		return returnValue;
	}
	
	public static Position convertTo(DekartPosition position){
		return new Position( (char)(position.x+Position.MIN_COLUMN) , position.y+Position.MIN_ROW );
	}

}
