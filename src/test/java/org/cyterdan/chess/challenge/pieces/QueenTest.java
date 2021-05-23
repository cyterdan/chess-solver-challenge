package org.cyterdan.chess.challenge.pieces;

import org.cyterdan.chess.challenge.boards.TwoDimBoard;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class QueenTest {

    @Test
    public void queen_does_not_threaten() {
        TwoDimBoard board = TwoDimBoard.parse(3,
                "♛..",
                "...",
                "...");
        Queen queen = new Queen(1, 2);
        assertThat(queen.canBePlacedOnCol(board)).isTrue();
        board.place(queen);
        System.out.println(board.draw());
    }


    @Test
    public void queen_cannot_be_on_same_row() {
        TwoDimBoard board = TwoDimBoard.parse(3,
                "♛..",
                "...",
                "...");
        Queen queen = new Queen(0, 2);
        assertThat(queen.canBePlacedOnCol(board)).isFalse();
    }

    @Test
    public void queen_threaten_in_diagonal() {
        TwoDimBoard board = TwoDimBoard.parse(3,
                "♛..",
                "...",
                "...");
        Queen queen = new Queen(2, 2);
        assertThat(queen.canBePlacedOnCol(board)).isFalse();
    }

    @Test
    public void queen_threaten_in_antidiagonal() {
        TwoDimBoard board = TwoDimBoard.parse(3,
                "...",
                ".♛.",
                "...");
        Queen queen = new Queen(2, 0);
        assertThat(queen.canBePlacedOnCol(board)).isFalse();
    }
}