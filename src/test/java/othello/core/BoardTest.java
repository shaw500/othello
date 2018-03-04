package othello.core;

import org.junit.Before;
import org.junit.Test;
import othello.core.exceptions.InvalidMoveException;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static helpers.BoardMatcher.matchesBoard;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static othello.core.Player.*;

public class BoardTest {

    private Board board;

    @Before
    public void setUp() {
        board = new Board();
    }

    @Test
    public void boardHasInitialState() {
        Player[][] actual = board.render();

        assertThat(actual, matchesBoard(new Player[][] {
                {_, _, _, _, _, _, _, _},
                {_, _, _, _, _, _, _, _},
                {_, _, _, _, _, _, _, _},
                {_, _, _, O, X, _, _, _},
                {_, _, _, X, O, _, _, _},
                {_, _, _, _, _, _, _, _},
                {_, _, _, _, _, _, _, _},
                {_, _, _, _, _, _, _, _}
        }));
    }

    @Test
    public void canCheckValueMovesRemainForPlayer() {
        assertTrue(board.validMovesRemain(X));
    }

    @Test
    public void canCheckNoValueMovesRemainForPlayer() {
        board.makeMove(X, new Position(5, 4));
        board.makeMove(O, new Position(3, 5));
        board.makeMove(X, new Position(2, 4));
        board.makeMove(O, new Position(5, 3));
        board.makeMove(X, new Position(4, 2));
        board.makeMove(O, new Position(5, 5));
        board.makeMove(X, new Position(6, 4));
        board.makeMove(O, new Position(4, 5));
        board.makeMove(X, new Position(4, 6));

        assertFalse(board.validMovesRemain(O));
    }

    @Test
    public void playerCanMakeAMoveAndTakeOpponentsPieces() {
        board.makeMove(X, new Position(3, 2));

        Player[][] actual = board.render();

        assertThat(actual, matchesBoard(new Player[][] {
                {_, _, _, _, _, _, _, _},
                {_, _, _, _, _, _, _, _},
                {_, _, _, _, _, _, _, _},
                {_, _, X, X, X, _, _, _},
                {_, _, _, X, O, _, _, _},
                {_, _, _, _, _, _, _, _},
                {_, _, _, _, _, _, _, _},
                {_, _, _, _, _, _, _, _}
        }));
    }

    @Test
    public void errorIsThrownWhenMovingOnPositionAlreadyTaken() {
        Player[][] before = board.render();

        try {
            board.makeMove(X, new Position(3, 3));
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            assertThat(e, instanceOf(InvalidMoveException.class));
            assertThat(e.getMessage(), equalTo("Cannot place piece here, place has already been taken."));
            assertThat(board.render(), matchesBoard(before));
        }
    }

    @Test
    public void errorIsThrownWhenMovingOnPositionWithNoOpponentAsNeighbours() {
        Player[][] before = board.render();

        try {
            board.makeMove(X, new Position(2, 5));
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            assertThat(e, instanceOf(InvalidMoveException.class));
            assertThat(e.getMessage(), equalTo("Cannot place piece here, no opponent pieces will be taken."));
            assertThat(board.render(), matchesBoard(before));
        }
    }

    @Test
    public void errorIsThrownPositionHasNoLineToOtherPiece() {
        Player[][] before = board.render();

        try {
            board.makeMove(X, new Position(5, 3));
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            assertThat(e, instanceOf(InvalidMoveException.class));
            assertThat(e.getMessage(), equalTo("Cannot place piece here, no opponent pieces will be taken."));
            assertThat(board.render(), matchesBoard(before));
        }
    }

    @Test
    public void canGetPiecesOnBoardBetweenPlayers() {
        board.makeMove(X, new Position(3, 2));
        board.makeMove(O, new Position(2, 4));
        board.makeMove(X, new Position(1, 5));
        board.makeMove(O, new Position(1, 4));
        board.makeMove(X, new Position(1, 3));
        board.makeMove(O, new Position(0, 4));

        List<Position> positions = board.getPiecesOnLineBetween(new Position(5, 4), new Position(4, 4), O, X);

        assertThat(positions, equalTo(newArrayList()));
    }
}
