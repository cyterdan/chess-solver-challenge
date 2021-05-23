package org.cyterdan.chess.challenge.boards;

import org.cyterdan.chess.challenge.pieces.ChessPiece;

/**
 * represent a chess board
 */
public interface Board {
    /**
     *
     * @return a string representation with console colors
     */
    String draw();

    /**
     *
     * @param piece the piece to place
     * @return true if the piece can be placed on the board
     */
    boolean allowsPlacement(ChessPiece piece);

    /**
     * @return a frozen snapshot of the board
     */
    Board snapshot();
}
