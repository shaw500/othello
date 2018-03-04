package othello.core;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

import static helpers.BoardMatcher.matchesBoard;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static othello.core.Player.*;

public class OthelloTest {

    @Rule
    public ExpectedException failure = ExpectedException.none();

    private Othello othello;

    @Before
    public void setUp() {
        othello = new Othello();
        othello.newGame();
    }

    @Test
    public void playerXHasTheFirstMove() {
        assertThat(othello.whoseTurn(), equalTo(X));
    }

    @Test
    public void afterPlayerXPlayerOMoves() {
        othello.placePiece(new Position(3, 2));
        assertThat(othello.whoseTurn(), equalTo(O));
    }

    @Test
    public void currentPlayerAlternatesIfValidMovesExist() {
        assertThat(othello.whoseTurn(), equalTo(X));
        othello.placePiece(new Position(5, 4));
        assertThat(othello.whoseTurn(), equalTo(O));
        othello.placePiece(new Position(3, 5));
        assertThat(othello.whoseTurn(), equalTo(X));
        othello.placePiece(new Position(2, 4));
        assertThat(othello.whoseTurn(), equalTo(O));
        othello.placePiece(new Position(5, 3));
    }

    @Test
    public void canGetCurrentScore() {
        Map<Player, Integer> expected = new HashMap<>();
        expected.put(X, 2);
        expected.put(O, 2);

        assertThat(othello.currentScore(), equalTo(expected));
    }

    @Test
    public void canGetScoreAfterEachMove() {
        othello.placePiece(new Position(5, 4));

        Map<Player, Integer> expected = new HashMap<>();
        expected.put(X, 4);
        expected.put(O, 1);

        assertThat(othello.currentScore(), equalTo(expected));
    }

    @Test
    public void whenNoMoreValidMovesRemainNextPlayerIs_() {
        othello.placePiece(new Position(4, 5));
        othello.placePiece(new Position(5, 3));
        othello.placePiece(new Position(4, 2));
        othello.placePiece(new Position(3, 5));
        othello.placePiece(new Position(2, 4));
        othello.placePiece(new Position(5, 5));
        othello.placePiece(new Position(4, 6));
        othello.placePiece(new Position(5, 4));
        othello.placePiece(new Position(6, 4));

        assertThat(othello.renderBoard(), matchesBoard(new Player[][] {
                {_, _, _, _, _, _, _, _},
                {_, _, _, _, _, _, _, _},
                {_, _, _, _, X, _, _, _},
                {_, _, _, X, X, X, _, _},
                {_, _, X, X, X, X, X, _},
                {_, _, _, X, X, X, _, _},
                {_, _, _, _, X, _, _, _},
                {_, _, _, _, _, _, _, _}
        }));

        assertThat(othello.whoseTurn(), equalTo(_));
    }

    @Test
    public void whenBoardIsFullGameIsOver() {
        othello.placePiece(new Position(5, 4));
        othello.placePiece(new Position(5, 3));
        othello.placePiece(new Position(5, 2));
        othello.placePiece(new Position(6, 3));
        othello.placePiece(new Position(4, 2));
        othello.placePiece(new Position(3, 5));
        othello.placePiece(new Position(7, 3));
        othello.placePiece(new Position(4, 1));
        othello.placePiece(new Position(3, 2));
        othello.placePiece(new Position(6, 4));
        othello.placePiece(new Position(3, 6));
        othello.placePiece(new Position(3, 1));
        othello.placePiece(new Position(7, 4));
        othello.placePiece(new Position(2, 3));
        othello.placePiece(new Position(3, 0));
        othello.placePiece(new Position(2, 2));
        othello.placePiece(new Position(1, 2));
        othello.placePiece(new Position(4, 0));
        othello.placePiece(new Position(5, 1));
        othello.placePiece(new Position(1, 3));
        othello.placePiece(new Position(5, 0));
        othello.placePiece(new Position(3, 7));
        othello.placePiece(new Position(1, 4));
        othello.placePiece(new Position(2, 4));
        othello.placePiece(new Position(4, 5));
        othello.placePiece(new Position(0, 4));
        othello.placePiece(new Position(2, 5));
        othello.placePiece(new Position(2, 6));
        othello.placePiece(new Position(0, 3));
        othello.placePiece(new Position(4, 6));
        othello.placePiece(new Position(0, 5));
        othello.placePiece(new Position(6, 1));
        othello.placePiece(new Position(6, 2));
        othello.placePiece(new Position(0, 2));
        othello.placePiece(new Position(0, 1));
        othello.placePiece(new Position(1, 1));
        othello.placePiece(new Position(1, 0));
        othello.placePiece(new Position(0, 0));
        othello.placePiece(new Position(2, 1));
        othello.placePiece(new Position(2, 0));
        othello.placePiece(new Position(6, 0));
        othello.placePiece(new Position(7, 0));
        othello.placePiece(new Position(7, 1));
        othello.placePiece(new Position(7, 2));
        othello.placePiece(new Position(5, 5));
        othello.placePiece(new Position(1, 5));
        othello.placePiece(new Position(0, 6));
        othello.placePiece(new Position(0, 7));
        othello.placePiece(new Position(2, 7));
        othello.placePiece(new Position(1, 6));
        othello.placePiece(new Position(1, 7));
        othello.placePiece(new Position(7, 5));
        othello.placePiece(new Position(5, 6));
        othello.placePiece(new Position(4, 7));
        othello.placePiece(new Position(5, 7));
        othello.placePiece(new Position(6, 5));
        othello.placePiece(new Position(6, 6));
        othello.placePiece(new Position(6, 7));
        othello.placePiece(new Position(7, 6));
        othello.placePiece(new Position(7, 7));

        assertThat(othello.renderBoard(), matchesBoard(new Player[][] {
                {O, O, O, O, O, O, O, O},
                {O, O, O, O, O, O, O, O},
                {O, O, O, O, X, O, X, O},
                {O, O, X, O, O, X, O, O},
                {O, X, O, X, O, O, X, O},
                {O, X, O, O, X, O, X, O},
                {O, O, O, O, O, X, O, O},
                {O, O, O, O, O, O, O, O}
        }));

        assertThat(othello.whoseTurn(), equalTo(_));
    }
}
