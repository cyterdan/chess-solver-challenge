package org.cyterdan.chess.challenge.pieces;

import org.cyterdan.chess.challenge.boards.TwoDimBoard;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KnightTest {

    @Test
    public void test() {
        TwoDimBoard board = new TwoDimBoard(3);
        board.place(new Knight(1, 0));
        board.place(new Knight(1, 1));
        Knight knight = new Knight(0, 2);
        System.out.println(board.draw());
        assertThat(knight.canBePlacedOnCol(board)).isFalse();
        System.out.println(board.draw());
    }

}

