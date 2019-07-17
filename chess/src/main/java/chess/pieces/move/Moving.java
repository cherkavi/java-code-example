package chess.pieces.move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import chess.Player;
import chess.Position;
import chess.board.Board;
import chess.board.BoardException;

public abstract class Moving {
	
	/**
	 * TODO need to extend with wrapper ( position - figure, which can be killed )
	 * @param board - 
	 * @param figure - figure for move
	 * @param player - use for direction only 
	 * @return
	 * @throws BoardException 
	 */
	public Collection<Position> getAccessiblePositions(Board board, Position position, Player player) throws BoardException{
		// collect directions ( when field is empty )
		Collection<Direction> directions=getDirections(position, player);

		Collection<Position> returnValue=new ArrayList<Position>();
		// analyze directions with other Pieces;
		for(Direction eachDirection:directions){
			for( Position nextStep: eachDirection.getPositions()){
				if(board.isEmpty(nextStep)){
					returnValue.add(nextStep);
				}else{
					if(isFreeStyle()){
						continue;
					}else{
						break;
					}
				}
			}
		}
		return returnValue;
	}

	/**
	 * for Knight only - possibility for jumping over Pieces
	 * @return
	 */
	protected boolean isFreeStyle(){
		return false;
	}

	/**
	 * when figure will be alone on center of the board - this is a map of one certain direction
	 * ( for Pawn - one only, for Knight - 4, for Queen - 8 etc... )
	 * @param board
	 * @param 
	 * @param player 
	 */
	protected abstract Collection<Direction> getDirections(Position position, Player player) ;
	
}

/**
 * all possible variants for moving in one certain direction
 */
class Direction{
	LinkedList<Position> positions;
	
	Direction(){
		this.positions=new LinkedList<Position>();
	}
	
	Direction(Collection<Position> positions){
		this.positions=new LinkedList<Position>(positions);
	}
	
	void add(Position position){
		this.positions.addLast(position);
	}
	
	Collection<Position> getPositions(){
		return this.positions;
	}
}
