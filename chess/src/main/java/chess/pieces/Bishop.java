package chess.pieces;

import chess.Player;
import chess.pieces.move.BishopMoving;

/**
 * The 'Bishop' class
 */
public class Bishop extends Piece {
    public Bishop(Player owner) {
        super(owner, new BishopMoving());
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'b';
    }
}
