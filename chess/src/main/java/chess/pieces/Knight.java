package chess.pieces;

import chess.Player;
import chess.pieces.move.KnightMoving;

/**
 * The Knight class
 */
public class Knight extends Piece {
    public Knight(Player owner) {
        super(owner, new KnightMoving());
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'n';
    }
}
