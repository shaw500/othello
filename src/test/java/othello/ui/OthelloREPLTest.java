package othello.ui;

import helpers.FakeWriter;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringEndsWith.endsWith;

public class OthelloREPLTest {

    @Test
    public void whenOthelloStartsInstructionsAreShownAndFirstGameStarts() {
        FakeWriter output = new FakeWriter();
        StringReader input = new StringReader("");

        OthelloREPL othello = new OthelloREPL(input, output);
        othello.start();

        String guide = "\n" +
                "===========\n" +
                "= OTHELLO =\n" +
                "===========\n" +
                "\n" +
                "- Game Play -\n" +
                "\n" +
                "Players take alternate turns.\n" +
                "\n" +
                "'X' goes first and must place an 'X' on the board, in such a position that there exists at least\n" +
                "one straight (horizontal, vertical, or diagonal) occupied line of 'O's between the new 'X' and\n" +
                "another 'X' on the board.\n" +
                "\n" +
                "After placing the piece, all 'O's lying on all straight lines between the new 'X' and any existing\n" +
                "'X' are captured (i.e. they turn into 'X's )\n" +
                "\n" +
                "Now 'O' plays. 'O' operates under the same rules, with the roles reversed: 'O' places an 'O' on the\n" +
                "board in such a position where at least one 'X' is captured\n" +
                "\n" +
                "- Commands -\n" +
                "\n" +
                "n - start a new game\n" +
                "\n" +
                "q - quit othello\n" +
                "\n" +
                "{row}{column} - place piece for current player, {row} is 1-8, {column} is a-h\n" +
                "\n" +
                "{column}{row} - alternative format for placing a piece on the board\n" +
                "\n" +
                "Player X's turn.\n" +
                "1 --------\n" +
                "2 --------\n" +
                "3 --------\n" +
                "4 ---OX---\n" +
                "5 ---XO---\n" +
                "6 --------\n" +
                "7 --------\n" +
                "8 --------\n" +
                "  abcdefgh\n" +
                "\n" +
                "Score: X = 2, O = 2\n" +
                "\n" +
                "> ";

        assertThat(output.getOutput(), equalTo(guide));
    }

    @Test
    public void canQuitTheGame() {
        FakeWriter output = new FakeWriter();
        StringReader input = new StringReader("q");

        OthelloREPL othello = new OthelloREPL(input, output);
        othello.start();

        assertThat(output.getOutput(), endsWith("Goodbye!\n"));
    }

    @Test
    public void canCreateNewGame() {
        FakeWriter output = new FakeWriter();
        StringReader input = new StringReader("n");

        OthelloREPL othello = new OthelloREPL(input, output);
        othello.start();

        assertThat(output.getOutput(), endsWith("" +
                "Starting new game!\n" +
                "\n" +
                "Player X's turn.\n" +
                "1 --------\n" +
                "2 --------\n" +
                "3 --------\n" +
                "4 ---OX---\n" +
                "5 ---XO---\n" +
                "6 --------\n" +
                "7 --------\n" +
                "8 --------\n" +
                "  abcdefgh\n" +
                "\n" +
                "Score: X = 2, O = 2\n" +
                "\n" +
                "> "
        ));
    }

    @Test
    public void playerCanMakeFirstMove() {
        FakeWriter output = new FakeWriter();
        StringReader input = new StringReader("n \n 4c");

        OthelloREPL othello = new OthelloREPL(input, output);
        othello.start();

        assertThat(output.getOutput(), endsWith("" +
                "Player O's turn.\n" +
                "1 --------\n" +
                "2 --------\n" +
                "3 --------\n" +
                "4 --XXX---\n" +
                "5 ---XO---\n" +
                "6 --------\n" +
                "7 --------\n" +
                "8 --------\n" +
                "  abcdefgh\n" +
                "\n" +
                "Score: X = 4, O = 1\n" +
                "\n" +
                "> "
        ));
    }

    @Test
    public void canReverseColumnAndRow() {
        FakeWriter output = new FakeWriter();
        StringReader input = new StringReader("n \n c4");

        OthelloREPL othello = new OthelloREPL(input, output);
        othello.start();

        assertThat(output.getOutput(), endsWith("" +
                "Player O's turn.\n" +
                "1 --------\n" +
                "2 --------\n" +
                "3 --------\n" +
                "4 --XXX---\n" +
                "5 ---XO---\n" +
                "6 --------\n" +
                "7 --------\n" +
                "8 --------\n" +
                "  abcdefgh\n" +
                "\n" +
                "Score: X = 4, O = 1\n" +
                "\n" +
                "> "
        ));
    }

    @Test
    public void gameResetsWhenComplete() {
        FakeWriter output = new FakeWriter();
        StringReader input = new StringReader("n \n 5f \n 6d \n 5c \n 4f \n 3e \n 6f \n 5g \n 6e \n 7e");

        OthelloREPL othello = new OthelloREPL(input, output);
        othello.start();

        assertThat(output.getOutput(), endsWith("" +
                "1 --------\n" +
                "2 --------\n" +
                "3 ----X---\n" +
                "4 ---XXX--\n" +
                "5 --XXXXX-\n" +
                "6 ---XXX--\n" +
                "7 ----X---\n" +
                "8 --------\n" +
                "  abcdefgh\n" +
                "\n" +
                "Game over, X wins! Score: X = 13, O = 0\n" +
                "\n" +
                "> "
        ));
    }

    @Test
    public void onUnknownCommandAnErrorIsShown() {
        FakeWriter output = new FakeWriter();
        StringReader input = new StringReader("n \n 9k");

        OthelloREPL othello = new OthelloREPL(input, output);
        othello.start();

        assertThat(output.getOutput(), endsWith("9k is not a valid command.\n" +
                "\n" +
                "> "));
    }

    @Test
    public void onExceptionAnErrorIsShown() {
        FakeWriter output = new FakeWriter();
        StringReader input = new StringReader("n \n 4d");

        OthelloREPL othello = new OthelloREPL(input, output);
        othello.start();

        assertThat(output.getOutput(), endsWith("Cannot place piece here, place has already been taken.\n" +
                "\n" +
                "> "));
    }
}
