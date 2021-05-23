package org.cyterdan.chess.challenge.pieces;

import org.cyterdan.chess.challenge.boards.TwoDimBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * common interface for all chess pieces
 */
public interface ChessPiece {

    int getCol();

    int getRow();

    String symbol();

    default int diagonal() {
        return getRow() - getCol();
    }

    default int antiDiagonal() {
        return getRow() + getCol();
    }

    boolean canBePlacedOnCol(TwoDimBoard board);

    /**
     * @param rows string representation of e.g {"..♝", "..♝","..♝"}
     * @return a list of chess pieces parsed from a string representation
     */
    static List<ChessPiece> parse(String... rows) {
        boolean allRowsHaveSameLength = Arrays.stream(rows).map(row -> row.replaceAll(" ", ""))
                .allMatch(row -> row.length() == rows[0].length());
        if (!allRowsHaveSameLength) {
            throw new IllegalArgumentException("Could not parse this input" + Arrays.toString(rows));
        }
        List<ChessPiece> pieces = new ArrayList<>();
        for (int i = 0; i < rows.length; i++) {
            String[] row = rows[i].replaceAll(" ", "").split("");
            for (int j = 0; j < row.length; j++) {
                String symb = row[j];
                Optional<ChessPiece> tile = switch (symb) {
                    case "♛" -> Optional.of(new Queen(j, i));
                    case "♝" -> Optional.of(new Bishop(j, i));
                    case "♜" -> Optional.of(new Rook(j, i));
                    case "♞" -> Optional.of(new Knight(j, i));
                    default -> Optional.empty();
                };
                tile.ifPresent(pieces::add);
            }
        }
        return pieces;

    }
}
