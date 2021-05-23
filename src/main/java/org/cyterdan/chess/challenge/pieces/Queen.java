package org.cyterdan.chess.challenge.pieces;

import lombok.EqualsAndHashCode;
import org.cyterdan.chess.challenge.boards.TwoDimBoard;

/**
 * Represents a chess queen
 */
@EqualsAndHashCode(callSuper = true)
public class Queen extends TwoDimChessPiece {

    public Queen(int col, int row) {
        super(col, row);
    }

    @Override
    public String symbol() {
        return " ♛ ";
    }

    /**
     * @param board the board that we want to place the queen on
     * @return true if the queen can be placed on {board} without being threaten, assuming it is safe on the it's current row
     */
    public boolean canBePlacedOnCol(TwoDimBoard board) {
        return !board.getColumns().contains(getCol())
                && !board.getDiagonals().contains(diagonal())
                && !board.getAntiDiagonals().contains(antiDiagonal());
    }
}
