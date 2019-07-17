package chess.pieces.move;

import java.util.ArrayList;
import java.util.Collection;

import chess.Player;
import chess.Position;

abstract class ManyStepsMoving extends Moving{

	@Override
	protected Collection<Direction> getDirections(Position position, Player player) {
		Collection<Direction> returnValue=new ArrayList<Direction>();
		
		for(Shift eachShift:getShifts(player)){
			// for each direction only one shift
			Direction nextDirection=new Direction();
			Position nextStep=position;
			do{
				nextStep=Shift.moveTo(nextStep,eachShift);
				if(nextStep!=null){
					nextDirection.add(nextStep);
					// break;
				}
			}while(nextStep!=null);
			returnValue.add(nextDirection);
		}
		return returnValue;
	}
	

	abstract protected Shift[] getShifts(Player player);

}
