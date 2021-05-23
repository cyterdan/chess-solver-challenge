package org.cyterdan.chess.challenge.pieces;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cyterdan.chess.challenge.boards.TwoDimBoard;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
public class Knight extends TwoDimChessPiece implements CanBePlacedVertically {
    //a set of all the tiles that can potentially threaten this knight
    Set<Offset> threatOffsets = new HashSet<>() {{
        add(new Offset(-2, -1));
        add(new Offset(-2, +1));
        add(new Offset(-1, -2));
        add(new Offset(-1, +2));
        add(new Offset(1, -2));
        add(new Offset(1, +2));
        add(new Offset(2, -1));
        add(new Offset(2, +1));
    }};

    public Knight(int col, int row) {
        super(col, row);
    }

    @Override
    public String symbol() {
        return " ♞ ";
    }

    @Override
    public boolean canBePlacedOnCol(TwoDimBoard board) {
        return threatOffsets.stream()
                .map(offset -> new Offset(getCol() + offset.getDcol(), getRow() + offset.getDrow())) //get the potential threads on this knight
                .filter(offset -> offset.getDcol() < board.getSize() && offset.getDcol() >= 0
                        && offset.getDrow() < board.getSize() && offset.getDrow() >= 0) //check that threatening tile is within the board
                .noneMatch(offset -> board.getTiles().get(offset.getDcol(), offset.getDrow()).isPresent());
    }

    /**
     * wrapper for storing a pair of column and row offsets
     */
    @Data
    @AllArgsConstructor
    private static class Offset {
        private int dcol;
        private int drow;
    }
}
