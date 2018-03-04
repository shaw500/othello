package helpers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import othello.core.Player;

import java.util.Arrays;

import static othello.core.Player._;

public class BoardMatcher extends TypeSafeMatcher<Player[][]> {

    private Player[][] actual;

    public static BoardMatcher matchesBoard(Player[][] actual) {
        return new BoardMatcher(actual);
    }

    private BoardMatcher(Player[][] actual) {
        this.actual = actual;
    }

    protected boolean matchesSafely(Player[][] expected) {
        return Arrays.deepEquals(actual, expected);
    }

    public void describeTo(Description description) {
        renderBoard(actual, description);
    }

    @Override
    protected void describeMismatchSafely(Player[][] expected, Description description) {
        renderBoard(expected, description);
    }

    private void renderBoard(Player[][] board, Description description) {
        description.appendText("\n");

        for (int i = 0; i < board.length; i++) {
            description.appendText(i + " ");
            for (int j = 0; j < board[i].length; j++) {
                Player player = board[i][j];
                description.appendText(player == _ ? "-" : player.name());
            }
            description.appendText("\n");
        }
        description.appendText("  01234567");
    }
}
