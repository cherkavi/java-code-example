package chess.pieces;

import chess.Player;
import chess.pieces.move.QueenMoving;

/**
 * The Queen
 */
public class Queen extends Piece{
    public Queen(Player owner) {
        super(owner, new QueenMoving());
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'q';
    }
}
