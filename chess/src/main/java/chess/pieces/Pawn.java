package chess.pieces;

import chess.Player;
import chess.pieces.move.PawnMoving;

/**
 * The Pawn
 */
public class Pawn extends Piece {
    public Pawn(Player owner) {
        super(owner, new PawnMoving());
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'p';
    }
}
