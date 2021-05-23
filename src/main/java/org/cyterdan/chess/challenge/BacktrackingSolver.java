package org.cyterdan.chess.challenge;

import lombok.extern.slf4j.Slf4j;
import org.cyterdan.chess.challenge.boards.TwoDimBoard;
import org.cyterdan.chess.challenge.pieces.CanBePlacedVertically;
import org.cyterdan.chess.challenge.pieces.ChessPiece;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * implementation of a N-pieces solver that relies on backtracking
 * see Readme.md for details
 */
@Slf4j
public class BacktrackingSolver implements NChessPieceSolver {

    private final List<TwoDimBoard> solutions = new ArrayList<>();
    //function to constructs an instance of chessPiece
    private final BiFunction<Integer, Integer, ? extends ChessPiece> chessPieceSupplier;
    //function to provide a board implementation
    private final Function<Integer, ? extends TwoDimBoard> boardSupplier;
    //size of the board = number of pieces
    private final int boardSize;

    public BacktrackingSolver(int boardSize,
                              BiFunction<Integer, Integer, ? extends ChessPiece> chessPieceSupplier,
                              Function<Integer, ? extends TwoDimBoard> boardSupplier) {
        this.chessPieceSupplier = chessPieceSupplier;
        this.boardSupplier = boardSupplier;
        this.boardSize = boardSize;
    }

    @Override
    public List<TwoDimBoard> placeNChessPieces() {
        backtrackHorizontally(0, boardSupplier.apply(boardSize));
        //for pieces that can't be placed on the same column (queens for example),
        // there's no need to backtrack vertically
        if (chessPieceSupplier.apply(0, 0) instanceof CanBePlacedVertically) {
            backtrackVertically(0, boardSupplier.apply(boardSize));
        }
        return solutions;
    }


    private void backtrackVertically(int col, TwoDimBoard board) {
        //we've reached the end of the board, meaning we found a valid solution
        if (col == board.getSize()) {
            solutions.add(board.snapshot());
            return;
        }
        for (int row = 0; row < board.getSize(); row++) {
            ChessPiece piece = chessPieceSupplier.apply(col, row);
            if (piece.canBePlacedOnCol(board) && board.allowsPlacement(piece)) {
                //the piece can be legally placed, let's add it to the board
                board.place(piece);
                log.debug("added piece : " + board.draw());
                //and call backtrack again recursively on the next row
                backtrackVertically(col + 1, board);
                //we're done with that branch now we remove the piece from the board
                board.remove(piece);
                log.debug("removed piece : " + board.draw());
            }
        }
    }


    private void backtrackHorizontally(int row, TwoDimBoard board) {
        //we've reached the end of the board, meaning we found a valid solution
        if (row == board.getSize()) {
            solutions.add(board.snapshot());
            log.debug("found valid solution");
            return;
        }
        for (int col = 0; col < board.getSize(); col++) {
            ChessPiece piece = chessPieceSupplier.apply(col, row);
            if (piece.canBePlacedOnCol(board) && board.allowsPlacement(piece)) {
                //the piece can be legally placed, let's add it to the board
                board.place(piece);
                log.debug("added piece : " + board.draw());
                //and call backtrack again recursively on the next row
                backtrackHorizontally(row + 1, board);
                //we're done with that branch now we remove the piece from the board
                board.remove(piece);
                log.debug("removed piece : " + board.draw());
            }
        }
    }
}
