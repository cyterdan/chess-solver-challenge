package org.cyterdan.chess.challenge;

import org.cyterdan.chess.challenge.boards.Non3CollinearPiecesBoard;
import org.cyterdan.chess.challenge.boards.TwoDimBoard;
import org.cyterdan.chess.challenge.pieces.Knight;
import org.cyterdan.chess.challenge.pieces.Queen;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class BacktrackingSolverTest {
    /**
     * Place N queens on an NxN chess board so that none of them attack each other
     * (the classic n-queens problem). Additionally, please make sure that no three
     * queens are in a straight line at ANY angle, so queens on A1, C2 and E3,
     * despite not attacking each other, form a straight line at some angle.
     */
    @Test
    public void ProblemStatement() {
        NChessPieceSolver solver = new BacktrackingSolver(4, Queen::new, Non3CollinearPiecesBoard::new);
        List<TwoDimBoard> solutions = solver.placeNChessPieces();

        System.out.println("there are " + solutions.size() + " solutions");
        assertThat(solutions.size()).isEqualTo(2);
        solutions.forEach(board -> System.out.println(board.draw()));
    }


    /**
     * Verifies that we get the expected number of solutions for standard N queens (no co-linearity constraint)
     */
    @Test
    public void standard8QueensProblem() {
        //expected values sourced from https://en.wikipedia.org/wiki/Eight_queens_puzzle
        assertThat(placeQueensOnStandardBoard(1)).hasSize(1);
        assertThat(placeQueensOnStandardBoard(2)).hasSize(0);
        assertThat(placeQueensOnStandardBoard(3)).hasSize(0);
        assertThat(placeQueensOnStandardBoard(4)).hasSize(2);
        assertThat(placeQueensOnStandardBoard(5)).hasSize(10);
        assertThat(placeQueensOnStandardBoard(6)).hasSize(4);
        assertThat(placeQueensOnStandardBoard(7)).hasSize(40);
    }


    @Test
    public void knights(){
        NChessPieceSolver solver = new BacktrackingSolver(3, Knight::new, Non3CollinearPiecesBoard::new);

        List<TwoDimBoard> solutions = solver.placeNChessPieces();
        solutions.forEach(board -> System.out.println(board.draw()));
        assertThat(solutions).isNotEmpty();

    }

    private List<TwoDimBoard> placeQueensOnStandardBoard(int nbQueens) {
        NChessPieceSolver solver = new BacktrackingSolver(nbQueens, Queen::new, TwoDimBoard::new);
        return solver.placeNChessPieces();
    }

}