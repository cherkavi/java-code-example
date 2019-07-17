package chess.pieces;

import chess.Player;
import chess.pieces.move.RookMoving;

/**
 * The 'Rook' class
 */
public class Rook extends Piece {

    public Rook(Player owner) {
        super(owner, new RookMoving());
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'r';
    }
}
