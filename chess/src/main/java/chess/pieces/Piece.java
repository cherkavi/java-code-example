package chess.pieces;

import java.util.Collection;

import chess.Player;
import chess.Position;
import chess.board.Board;
import chess.board.BoardException;
import chess.pieces.move.Moving;

/**
 * A base class for chess pieces
 */
public abstract class Piece {
    private final Player owner;
    private Moving moving;
    
    protected Piece(Player owner, Moving moving) {
        this.owner = owner;
        this.moving=moving;
    }

    public Collection<Position> getAccessiblePositions(Board board, Position position, Player player) throws BoardException{
    	return this.moving.getAccessiblePositions(board, position, player);
    }
    
    
    public char getIdentifier() {
        char id = getIdentifyingCharacter();
        if (owner.equals(Player.White)) {
            return Character.toLowerCase(id);
        } else {
            return Character.toUpperCase(id);
        }
    }

    public Player getOwner() {
        return owner;
    }

    protected abstract char getIdentifyingCharacter();

}
