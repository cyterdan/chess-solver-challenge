package org.cyterdan.chess.challenge.boards;

import lombok.EqualsAndHashCode;
import org.cyterdan.chess.challenge.pieces.ChessPiece;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * a Board implementation that doesn't allow 3 chess pieces to be placed on a single line
 */
@EqualsAndHashCode(callSuper = true)
public class Non3CollinearPiecesBoard extends TwoDimBoard {

    public Non3CollinearPiecesBoard(int size) {
        super(size);
    }

    /**
     * @param piece the chess piece to be placed
     * @return true if the board specific rules allow placing this chess piece
     */
    public boolean allowsPlacement(ChessPiece piece) {
        //to check for collinearity, we calculate the slope against every other piece on the board
        //if we find the same slope twice, then we'd have 3 pieces that are aligned, which this board prohibits
        final List<ChessPiece> placedPieces = getPlacedPieces();
        Set<Double> slopes = placedPieces
                .stream()
                .map(placed -> calculateSlope(placed, piece))
                .collect(Collectors.toSet());
        //if we have as many slopes as pieces, then there are no duplicate slopes
        return slopes.size() == placedPieces.size();
    }

    /**
     * @return the slope of the line formed between two chess pieces
     */
    Double calculateSlope(ChessPiece placed, ChessPiece piece) {
        //protect against division by zero
        if (piece.getCol() - placed.getCol() == 0) {
            //"infinite" slope
            return Double.MAX_VALUE;
        }
        return ((double) (piece.getRow() - placed.getRow()) / (piece.getCol() - placed.getCol()));
    }


    List<ChessPiece> getPlacedPieces() {
        return getTiles().values().stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }


    /**
     * helper method to parse a board of this type from a set of strings that look like :
     * ..♝
     * .♝.
     * ♝..
     */
    public static Non3CollinearPiecesBoard parse(int size, String... rows) {
        TwoDimBoard.parse(size, rows);
        List<ChessPiece> parsed = ChessPiece.parse(rows);
        Non3CollinearPiecesBoard non3CollinearPiecesBoard = new Non3CollinearPiecesBoard(size);
        parsed.forEach(non3CollinearPiecesBoard::place);
        return non3CollinearPiecesBoard;
    }


}
