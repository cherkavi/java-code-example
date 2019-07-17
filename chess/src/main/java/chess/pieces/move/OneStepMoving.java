package chess.pieces.move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import chess.Player;
import chess.Position;

abstract class OneStepMoving extends Moving{

	@Override
	protected Collection<Direction> getDirections(Position position, Player player) {
		Collection<Direction> returnValue=new ArrayList<Direction>();
		
		for(Shift eachShift:getShifts(player)){
			// for each direction only one shift
			Position nextStep=Shift.moveTo(position,eachShift);
			if(nextStep!=null){
				// if Position is possible for current board and position
				returnValue.add(new Direction(Arrays.asList(nextStep)));
			}
		}
		return returnValue;
	}
	

	abstract protected Shift[] getShifts(Player player);
	

}
