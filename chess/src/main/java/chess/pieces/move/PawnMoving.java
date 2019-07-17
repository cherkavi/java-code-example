package chess.pieces.move;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import chess.Player;
import chess.Position;

public class PawnMoving extends Moving{

	private final static Map<Player, Shift[]> SHIFT=new HashMap<Player, Shift[]>();
	static{
		SHIFT.put(Player.White, new Shift[]{new Shift(0,1)});
		SHIFT.put(Player.Black, new Shift[]{new Shift(0,-1)});
	}
	private final static Map<Player, Integer> START_POSITION=new HashMap<Player, Integer>();
	static{
		START_POSITION.put(Player.White, 2);
		START_POSITION.put(Player.Black, 7);
	}

	@Override
	protected Collection<Direction> getDirections(Position position,
			Player player) {
		Direction returnValue=new Direction();
		Position nextPosition=Shift.moveTo(position, SHIFT.get(player)[0]);
		returnValue.add(nextPosition);
		if(isFirstPosition(position, player)){
			returnValue.add(Shift.moveTo(nextPosition, SHIFT.get(player)[0]));
		}
		return Arrays.asList(returnValue);
	}

	private boolean isFirstPosition(Position position, Player player) {
		return START_POSITION.get(player).equals(position.getRow());
	}

}
