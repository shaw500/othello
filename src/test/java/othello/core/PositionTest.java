package othello.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import othello.core.Position;
import othello.core.exceptions.InvalidTransformException;

import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;

public class PositionTest {

    @Rule
    public ExpectedException failure = ExpectedException.none();

    @Test
    public void canCreatePosition() {
        Position position = new Position(1, 2);

        assertThat(position.x, equalTo(1));
        assertThat(position.y, equalTo(2));
    }

    @Test
    public void canCreateOutOfBoundsPosition() {
        Position position = new Position(-1, -1);

        assertThat(position.x, equalTo(-1));
        assertThat(position.y, equalTo(-1));
    }

    @Test
    public void canCheckWhetherPositionIsInBounds() {
        assertTrue(new Position(1, 1).withinBounds());

        assertFalse(new Position(-1, 2).withinBounds());
        assertFalse(new Position(8, 2).withinBounds());
        assertFalse(new Position(1, -1).withinBounds());
        assertFalse(new Position(1, 8).withinBounds());
    }

    @Test
    public void canCheckPositionsAreEqual() {
        assertTrue(new Position(1, 1).equals(new Position(1, 1)));

        assertFalse(new Position(1, 1).equals(new Position(1, 2)));
        assertFalse(new Position(1, 1).equals(new Position(2, 1)));
        assertFalse(new Position(1, 1).equals(new Position(2, 2)));
        assertFalse(new Position(1, 1).equals(null));
        assertFalse(new Position(1, 1).equals("Not a position"));
    }

    @Test
    public void canHashPosition() {
        assertThat(new Position(1, 1).hashCode(), equalTo(new Position(1, 1).hashCode()));

        assertThat(new Position(1, 1).hashCode(), not(equalTo(new Position(1, 2).hashCode())));
        assertThat(new Position(1, 1).hashCode(), not(equalTo(new Position(2, 1).hashCode())));
        assertThat(new Position(1, 1).hashCode(), not(equalTo(new Position(2, 2).hashCode())));
    }

    @Test
    public void canGetNeighbours() {
        assertThat(new Position(3, 3).getNeighbours(), equalTo(newArrayList(
                new Position(2, 2),
                new Position(2, 3),
                new Position(2, 4),
                new Position(3, 2),
                new Position(3, 4),
                new Position(4, 2),
                new Position(4, 3),
                new Position(4, 4)
        )));
    }

    @Test
    public void whenGettingNeighboursDoNotGoOutOfBounds() {
        assertThat(new Position(0, 0).getNeighbours(), equalTo(newArrayList(
                new Position(0, 1),
                new Position(1, 0),
                new Position(1, 1)
        )));

        assertThat(new Position(7, 7).getNeighbours(), equalTo(newArrayList(
                new Position(6, 6),
                new Position(6, 7),
                new Position(7, 6)
        )));

        assertThat(new Position(0, 3).getNeighbours(), equalTo(newArrayList(
                new Position(0, 2),
                new Position(0, 4),
                new Position(1, 2),
                new Position(1, 3),
                new Position(1, 4)
        )));
    }

    @Test
    public void canGetPositionTransformBaseOnNextPosition() {
        Position start = new Position(1, 1);
        Position next = new Position(2, 2);

        Function<Position, Position> transform = start.getPositionTransform(next);

        assertThat(transform.apply(start), equalTo(new Position(2, 2)));
        assertThat(transform.andThen(transform).apply(start), equalTo(new Position(3, 3)));
        assertThat(transform.andThen(transform).andThen(transform).apply(start), equalTo(new Position(4, 4)));
    }

    @Test
    public void transformWillHappilyGenerateOutOfBoundPosition() {
        Position start = new Position(1, 1);
        Position next = new Position(0, 0);

        Function<Position, Position> transform = start.getPositionTransform(next);

        assertThat(transform.andThen(transform).apply(start), equalTo(new Position(-1, -1)));
    }

    @Test
    public void errorIsThrownWhenTryingToCreateTransformWithPositionsNotNextToEachOther() {
        Position start = new Position(1, 1);
        Position next = new Position(3, 3);

        failure.expect(InvalidTransformException.class);
        failure.expectMessage("Can only create transform from positions next to each other.");

        Function<Position, Position> transform = start.getPositionTransform(next);
    }

    @Test
    public void errorIsThrownWhenTryingToCreateTransformIdenticalPositions() {
        Position start = new Position(1, 1);

        failure.expect(InvalidTransformException.class);
        failure.expectMessage("Cannot create transform with identical positions.");

        Function<Position, Position> transform = start.getPositionTransform(start);
    }
}
