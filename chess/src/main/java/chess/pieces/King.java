package chess.pieces;

import chess.Player;
import chess.pieces.move.KingMoving;

/**
 * The King class
 */
public class King extends Piece {
    public King(Player owner) {
        super(owner, new KingMoving());
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'k';
    }
}
