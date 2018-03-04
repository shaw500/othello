package othello.ui;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import othello.core.Othello;
import othello.core.Player;
import othello.core.Position;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.Scanner;

public class OthelloREPL {

    private static Map<Player, String> playerToNameMap = ImmutableMap.<Player, String>builder()
            .put(Player.X, "X")
            .put(Player.O, "O")
            .put(Player._, "-")
            .build();

    private static BiMap<Integer, String> rowToNameMap = HashBiMap.create(ImmutableMap.<Integer, String>builder()
            .put(0, "1")
            .put(1, "2")
            .put(2, "3")
            .put(3, "4")
            .put(4, "5")
            .put(5, "6")
            .put(6, "7")
            .put(7, "8")
            .build());

    private static BiMap<Integer, String> columnToNameMap = HashBiMap.create(ImmutableMap.<Integer, String>builder()
            .put(0, "a")
            .put(1, "b")
            .put(2, "c")
            .put(3, "d")
            .put(4, "e")
            .put(5, "f")
            .put(6, "g")
            .put(7, "h")
            .build());

    private Reader input;
    private Writer output;
    private Othello othello;

    public OthelloREPL(Reader input, Writer output) {
        this.input = input;
        this.output = output;
        this.othello = new Othello();
    }

    public void start() {
        printGuide();

        othello.newGame();
        renderGameState(othello);
        output.writeLine("");
        output.write("> ");

        Scanner scanner = new Scanner(input);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().toLowerCase().trim();
            output.writeLine("");

            switch (line) {

                case "q":
                    output.writeLine("Goodbye!");
                    return;

                case "n":
                    output.writeLine("Starting new game!");
                    output.writeLine("");
                    othello.newGame();
                    renderGameState(othello);
                    break;

                default:
                    processMoveCommand(line);
            }

            output.writeLine("");
            output.write("> ");
        }
    }

    private void processMoveCommand(String line) {
        try {
            if (line.matches("^[1-8][a-h]$")) {
                Position position = createPosition(line.charAt(0), line.charAt(1));
                othello.placePiece(position);
                renderGameState(othello);
            } else if (line.matches("^[a-h][1-8]$")) {
                Position position = createPosition(line.charAt(1), line.charAt(0));
                othello.placePiece(position);
                renderGameState(othello);
            } else {
                output.write(line);
                output.writeLine(" is not a valid command.");
            }
        } catch (Exception e) {
            output.writeLine(e.getMessage());
        }
    }

    private void renderGameState(Othello othello) {
        Player currentPlayer = othello.whoseTurn();
        Player[][] board = othello.renderBoard();
        Map<Player, Integer> score = othello.currentScore();

        if(!gameOver(currentPlayer)) output.writeLine("Player " + playerToNameMap.get(currentPlayer) + "'s turn.");

        renderBoard(board);

        if(gameOver(currentPlayer)) {
            int playerXScore = score.get(Player.X);
            int playerOScore = score.get(Player.O);

            output.write("Game over, ");
            if(playerOScore > playerXScore) {
                output.write(Player.O.name());
                output.write(" wins!");
            } else if(playerXScore > playerOScore) {
                output.write(Player.X.name());
                output.write(" wins!");
            } else {
                output.write("Its a draw!");
            }
            output.write(" ");
        }
        output.write("Score: X = ");
        output.write(score.get(Player.X).toString());
        output.write(", O = ");
        output.writeLine(score.get(Player.O).toString());
    }

    private boolean gameOver(Player currentPlayer) {
        return currentPlayer == Player._;
    }

    private void renderBoard(Player[][] board) {
        for (int i = 0; i < board.length; i++) {
            output.write(rowToNameMap.get(i));
            output.write(" ");
            for (int j = 0; j < board[i].length; j++) {
                output.write(playerToNameMap.get(board[i][j]));
            }
            output.writeLine("");
        }
        output.writeLine("  abcdefgh");
        output.writeLine("");
    }

    private Position createPosition(char row, char column) {
        int x = rowToNameMap.inverse().get(String.valueOf(row));
        int y = columnToNameMap.inverse().get(String.valueOf(column));
        return new Position(x, y);
    }

    private void printGuide() {
        output.writeLine("");
        output.writeLine("===========");
        output.writeLine("= OTHELLO =");
        output.writeLine("===========");
        output.writeLine("");
        output.writeLine("- Game Play -");
        output.writeLine("");
        output.writeLine("Players take alternate turns.");
        output.writeLine("");
        output.writeLine("'X' goes first and must place an 'X' on the board, in such a position that there exists at least");
        output.writeLine("one straight (horizontal, vertical, or diagonal) occupied line of 'O's between the new 'X' and");
        output.writeLine("another 'X' on the board.");
        output.writeLine("");
        output.writeLine("After placing the piece, all 'O's lying on all straight lines between the new 'X' and any existing");
        output.writeLine("'X' are captured (i.e. they turn into 'X's )");
        output.writeLine("");
        output.writeLine("Now 'O' plays. 'O' operates under the same rules, with the roles reversed: 'O' places an 'O' on the");
        output.writeLine("board in such a position where at least one 'X' is captured");
        output.writeLine("");
        output.writeLine("- Commands -");
        output.writeLine("");
        output.writeLine("n - start a new game");
        output.writeLine("");
        output.writeLine("q - quit othello");
        output.writeLine("");
        output.writeLine("{row}{column} - place piece for current player, {row} is 1-8, {column} is a-h");
        output.writeLine("");
        output.writeLine("{column}{row} - alternative format for placing a piece on the board");
        output.writeLine("");
    }

    public static void main(String[] args) {
        OthelloREPL repl = new OthelloREPL(new InputStreamReader(System.in), new WriterImpl());
        repl.start();
    }
}
