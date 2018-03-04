package othello.core;

import othello.core.exceptions.InvalidTransformException;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
                y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public List<Position> getNeighbours() {
        return newArrayList(
                new Position(x - 1, y - 1),
                new Position(x - 1, y),
                new Position(x - 1, y + 1),
                new Position(x, y - 1),
                new Position(x, y + 1),
                new Position(x + 1, y - 1),
                new Position(x + 1, y),
                new Position(x + 1, y + 1)
        ).stream()
                .filter(Position::withinBounds)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public Function<Position, Position> getPositionTransform(Position nextPosition) {
        int xDelta = nextPosition.x - x;
        int yDelta = nextPosition.y - y;

        if(xDelta > 1 || yDelta > 1) throw new InvalidTransformException("Can only create transform from positions next to each other.");
        if(this.equals(nextPosition)) throw new InvalidTransformException("Cannot create transform with identical positions.");

        return p -> new Position(p.x + xDelta, p.y + yDelta);
    }

    public boolean withinBounds() {
        return x >= 0 && y >= 0 && x < 8 && y < 8;
    }
}
