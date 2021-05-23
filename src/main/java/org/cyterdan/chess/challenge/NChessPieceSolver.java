package org.cyterdan.chess.challenge;

import org.cyterdan.chess.challenge.boards.TwoDimBoard;

import java.util.List;

/**
 * Solvers of this challenge should implement this interface
 */
public interface NChessPieceSolver {

    /**
     * @return a list of solutions found given a specified problem
     */
    List<TwoDimBoard> placeNChessPieces();

}
