package org.cyterdan.chess.challenge.pieces;

import org.cyterdan.chess.challenge.boards.TwoDimBoard;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BishopTest {
    @Test
    public void bishop_cannot_be_on_diagonal() {
        TwoDimBoard board = TwoDimBoard.parse(3,
                "♝..",
                "...",
                "...");
        Bishop bishop = new Bishop(2, 2);
        assertThat(bishop.canBePlacedOnCol(board)).isFalse();
    }
}