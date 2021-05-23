package org.cyterdan.chess.challenge.boards;

import org.cyterdan.chess.challenge.pieces.Knight;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class Non3CollinearPiecesBoardTest {

    @Test
    public void slopeCalulationsAreCorrect() {
        Non3CollinearPiecesBoard board = Non3CollinearPiecesBoard.parse(5,
                "♞...♞",
                ".....",
                ".....",
                ".....",
                "♞...♞");
        Knight bottomeRight = new Knight(2, 2);

        List<Double> slopes = board.getPlacedPieces().stream()
                .map(chessPiece -> board.calculateSlope(bottomeRight, chessPiece))
                .collect(Collectors.toList());
        assertThat(slopes).hasSize(4);
        assertThat(slopes).hasSameElementsAs(Arrays.asList(-1.0, 1.0));
    }

    @Test
    public void SlopeShouldAccountForDivideByZero() {
        Non3CollinearPiecesBoard board = Non3CollinearPiecesBoard.parse(5,
                "....♞",
                ".....",
                "....♞",
                ".....",
                ".....");
        Knight bottomeRight = new Knight(4, 4);

        List<Double> slopes = board.getPlacedPieces().stream()
                .map(chessPiece -> board.calculateSlope(bottomeRight, chessPiece))
                .collect(Collectors.toList());
        //check that both vertical slopes are equal
        assertThat(slopes.get(0)).isEqualTo(Double.MAX_VALUE);
        assertThat(slopes.get(1)).isEqualTo(Double.MAX_VALUE);

    }

    @Test
    public void boardShouldNotAllow3PiecesOnLine() {
        Non3CollinearPiecesBoard board = Non3CollinearPiecesBoard.parse(3,
                "♞..",
                ".♞.",
                "...");
        Knight knight3 = new Knight(2, 2);
        assertThat(board.allowsPlacement(knight3)).isFalse();
    }

    @Test
    public void BoardConstraintsShouldRemainConsistentWithAddAndRemoveTopRight() {
        Non3CollinearPiecesBoard board = Non3CollinearPiecesBoard.parse(3,
                "...",
                ".♞.",
                "...");
        Knight topLeft = new Knight(0, 0);
        Knight bottomRight = new Knight(2, 2);
        assertThat(board.allowsPlacement(topLeft)).isTrue();
        assertThat(board.allowsPlacement(bottomRight)).isTrue();
        board.place(topLeft);
        assertThat(board.allowsPlacement(bottomRight)).isFalse();
        board.remove(topLeft);
        assertThat(board.allowsPlacement(bottomRight)).isTrue();
        board.place(bottomRight);
        assertThat(board.allowsPlacement(topLeft)).isFalse();
        board.remove(bottomRight);
        assertThat(board.allowsPlacement(topLeft)).isTrue();
    }

    @Test
    public void BoardConstraintsShouldRemainConsistentWithAddAndRemoveBottomLeft() {
        Non3CollinearPiecesBoard board = Non3CollinearPiecesBoard.parse(3,
                "...",
                "...",
                "...");
        Knight middle = new Knight(1, 1);
        Knight topLeft = new Knight(0, 0);
        Knight bottomRight = new Knight(2, 2);
        board.place(bottomRight);
        board.place(topLeft);
        System.out.println(board.draw());
        assertThat(board.allowsPlacement(middle)).isFalse();
        board.remove(topLeft);
        assertThat(board.allowsPlacement(middle)).isTrue();
        board.place(middle);
        assertThat(board.allowsPlacement(topLeft)).isFalse();

    }
}