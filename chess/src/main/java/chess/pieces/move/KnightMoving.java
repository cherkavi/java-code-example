package chess.pieces.move;

import chess.Player;


public class KnightMoving extends OneStepMoving{
	private final static Shift[] SHIFTS=new Shift[]{
		new Shift(-1,2), new Shift(1,2), new Shift(2,1), new Shift(2,-1),  
		new Shift(1,-2), new Shift(-1,-2), new Shift(-2,-1), new Shift(-2,1),  
		};
	
	protected Shift[] getShifts(Player player) {
		return SHIFTS;
	}

	@Override
	protected boolean isFreeStyle() {
		return true;
	}
}
