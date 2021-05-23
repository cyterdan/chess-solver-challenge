package org.cyterdan.chess.challenge.pieces;

import lombok.EqualsAndHashCode;
import org.cyterdan.chess.challenge.boards.TwoDimBoard;

@EqualsAndHashCode(callSuper = true)
public class Bishop extends TwoDimChessPiece implements CanBePlacedVertically {

    public Bishop(int col, int row) {
        super(col, row);
    }

    @Override
    public String symbol() {
        return " ♝ ";
    }

    @Override
    public boolean canBePlacedOnCol(TwoDimBoard board) {
        return !board.getDiagonals().contains(diagonal())
                && !board.getAntiDiagonals().contains(antiDiagonal());
    }
}
