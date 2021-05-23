package org.cyterdan.chess.challenge.pieces;

import lombok.EqualsAndHashCode;
import org.cyterdan.chess.challenge.boards.TwoDimBoard;

@EqualsAndHashCode(callSuper = true)
public class Rook extends TwoDimChessPiece {

    public Rook(int col, int row) {
        super(col, row);
    }

    @Override
    public String symbol() {
        return " ♜ ";
    }

    @Override
    public boolean canBePlacedOnCol(TwoDimBoard board) {
        return !board.getColumns().contains(getCol());
    }
}
