package org.cyterdan.chess.challenge.boards;

import com.diogonunes.jcolor.Attribute;
import com.google.common.collect.TreeBasedTable;
import lombok.Data;
import org.cyterdan.chess.challenge.pieces.ChessPiece;

import java.util.*;

import static com.diogonunes.jcolor.Ansi.colorize;

@Data
public class TwoDimBoard implements Board {
    private final int size;
    private final TreeBasedTable<Integer, Integer, Optional<ChessPiece>> tiles;
    private final Set<Integer> columns = new HashSet<>();
    private final Set<Integer> diagonals = new HashSet<>();
    private final Set<Integer> antiDiagonals = new HashSet<>();

    public TwoDimBoard(int size) {
        this.size = size;
        tiles = TreeBasedTable.create();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles.put(i, j, Optional.empty());
            }
        }
    }

    public TwoDimBoard(int size, TreeBasedTable<Integer, Integer, Optional<ChessPiece>> tiles) {
        this.size = size;
        this.tiles = tiles;
    }

    private int antiDiagonal(ChessPiece chessPiece) {
        return chessPiece.getRow() + chessPiece.getCol();
    }

    private int diagonal(ChessPiece chessPiece) {
        return chessPiece.getRow() - chessPiece.getCol();
    }


    public void place(ChessPiece piece) {
        tiles.put(piece.getCol(), piece.getRow(), Optional.of(piece));
        columns.add(piece.getCol());
        diagonals.add(diagonal(piece));
        antiDiagonals.add(antiDiagonal(piece));
    }

    public void remove(ChessPiece piece) {
        tiles.put(piece.getCol(), piece.getRow(), Optional.empty());
        columns.remove(piece.getCol());
        diagonals.remove(diagonal(piece));
        antiDiagonals.remove(antiDiagonal(piece));
    }

    public TwoDimBoard snapshot() {
        return new TwoDimBoard(this.size, TreeBasedTable.create(this.tiles));
    }

    /**
     * @return a string representing the board (with colors!)
     */
    public String draw() {
        boolean isWhite = true;
        StringBuilder board = new StringBuilder();
        for (int i = 0; i < size; i++) {
            List<String> rowParts = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                String raw = tiles.get(j, i).map(ChessPiece::symbol).orElse("   ");
                String wColor = isWhite ? colorize(raw, Attribute.BRIGHT_BLUE_BACK(), Attribute.BLACK_TEXT())
                        : colorize(raw, Attribute.WHITE_BACK(), Attribute.BLACK_TEXT());
                rowParts.add(wColor);
                isWhite = !isWhite;
            }
            if (size % 2 == 0) {
                isWhite = !isWhite;
            }
            String row = String.join("", rowParts);
            board.append(row).append("\n");
        }
        return "\n" + board;
    }

    /**
     * helper method to parse a board of this type from a set of strings that look like :
     * ..♝
     * .♝.
     * ♝..
     */
    public static TwoDimBoard parse(int size, String... rows) {
        List<ChessPiece> parsed = ChessPiece.parse(rows);
        TwoDimBoard twoDimBoard = new TwoDimBoard(size);
        parsed.forEach(twoDimBoard::place);
        return twoDimBoard;
    }

    @Override
    public boolean allowsPlacement(ChessPiece piece) {
        //standard board does not have any extra rules and just accept any chess piece
        return true;
    }
}
