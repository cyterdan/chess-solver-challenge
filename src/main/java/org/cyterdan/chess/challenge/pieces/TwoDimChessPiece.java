package org.cyterdan.chess.challenge.pieces;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class TwoDimChessPiece implements ChessPiece{
    private int col;
    private int row;

    protected TwoDimChessPiece(int col, int row) {
        this.col = col;
        this.row = row;
    }

}
