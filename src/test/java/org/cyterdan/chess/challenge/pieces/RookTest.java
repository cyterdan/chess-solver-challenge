package org.cyterdan.chess.challenge.pieces;

import org.cyterdan.chess.challenge.boards.TwoDimBoard;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RookTest {

    @Test
    public void rook_cannot_be_on_same_row() {
        TwoDimBoard board = TwoDimBoard.parse(3,
                "...",
                "...",
                "♜..");
        Rook rook = new Rook(0, 2);
        assertThat(rook.canBePlacedOnCol(board)).isFalse();
    }

}