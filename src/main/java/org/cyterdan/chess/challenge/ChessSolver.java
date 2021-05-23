package org.cyterdan.chess.challenge;

import org.cyterdan.chess.challenge.boards.Non3CollinearPiecesBoard;
import org.cyterdan.chess.challenge.boards.TwoDimBoard;
import org.cyterdan.chess.challenge.pieces.*;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * cli runner class for the project
 */
@Command(name = "ChessSolver", mixinStandardHelpOptions = true, version = "ChessSolver 1.0",
        description = "Solve variants of the classical N-queens problem")
public class ChessSolver implements Callable<Integer> {

    @CommandLine.Option(names = {"-p", "--pieceType"}, description = "The type of chess piece to place. Valid values: ${COMPLETION-CANDIDATES}",
            defaultValue = "QUEEN")
    private PIECE_TYPE piece;

    @CommandLine.Option(names = {"-n", "--size"}, description = "The size of the board, or the number of pieces"
            , defaultValue = "4")
    private int boardSize;

    @CommandLine.Option(names = {"-b", "--boardType"}, description = "The type of board. Valid values: ${COMPLETION-CANDIDATES}",
            defaultValue = "NO_3_PIECES_ALIGNED")
    private BOARD_TYPE board;

    @Override
    public Integer call() {
        System.out.printf("Trying to place %d pieces (of type %s) on a board (type %s)\n", boardSize, piece, board);
        BacktrackingSolver solver = new BacktrackingSolver(boardSize, piece.biFunction, board.function);
        List<TwoDimBoard> solutions = solver.placeNChessPieces();
        System.out.println("there are " + solutions.size() + " solutions :");
        solutions.forEach(board -> System.out.println(board.draw()));
        return 0;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new ChessSolver()).execute(args);
        System.exit(exitCode);
    }

    enum PIECE_TYPE {
        QUEEN(Queen::new),
        ROOK(Rook::new),
        BISHOP(Bishop::new),
        KNIGHT(Knight::new);
        BiFunction<Integer, Integer, ChessPiece> biFunction;

        PIECE_TYPE(BiFunction<Integer, Integer, ChessPiece> biFunction) {
            this.biFunction = biFunction;
        }
    }

    enum BOARD_TYPE {
        STANDARD(TwoDimBoard::new),
        NO_3_PIECES_ALIGNED(Non3CollinearPiecesBoard::new);
        Function<Integer, TwoDimBoard> function;

        BOARD_TYPE(Function<Integer, TwoDimBoard> function) {
            this.function = function;
        }
    }
}
