package othello.core;

import othello.core.exceptions.InvalidMoveException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static othello.core.Player.O;
import static othello.core.Player.X;
import static othello.core.Player._;

public class Board {

    private Map<Position, Player> moves = new HashMap<>();

    public Board() {
        moves.put(new Position(3, 3), O);
        moves.put(new Position(3, 4), X);
        moves.put(new Position(4, 3), X);
        moves.put(new Position(4, 4), O);
    }

    public Player[][] render() {
        Player[][] board = new Player[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = _;
            }
        }

        moves.forEach((position, player) -> board[position.x][position.y] = player);
        return board;
    }

    public void makeMove(Player player, Position position) {
        if (moves.containsKey(position))
            throw new InvalidMoveException("Cannot place piece here, place has already been taken.");

        if (!positionIsValidMove(player, position))
            throw new InvalidMoveException("Cannot place piece here, no opponent pieces will be taken.");

        getNeighbouringOpponents(player, position)
                .flatMap(neighbour -> getPiecesOnLineBetween(position, neighbour, player.opponent(), player).stream())
                .forEach(p -> moves.put(p, player));

        moves.put(position, player);
    }

    private Stream<Position> getNeighbouringOpponents(Player player, Position position) {
        Player opponent = player.opponent();
        return position.getNeighbours().stream().filter(p -> moves.get(p) == opponent);
    }

    private boolean positionIsValidMove(Player player, Position position) {
        if(getNeighbouringOpponents(player, position).count() == 0) return false;

        return getNeighbouringOpponents(player, position).anyMatch(neighbour ->
                getPiecesOnLineBetween(position, neighbour, player.opponent(), player).size() > 0
        );
    }

    public boolean validMovesRemain(Player player) {
        return moves.keySet().stream().filter(key -> moves.get(key) == player).anyMatch(position ->
                getNeighbouringOpponents(player, position).anyMatch(neighbour ->
                        getPiecesOnLineBetween(position, neighbour, player.opponent(), null).size() > 0
                )
        );
    }

    public List<Position> getPiecesOnLineBetween(Position start, Position nextPosition, Player linePiece, Player terminator) {
        List<Position> line = new LinkedList<>();

        Function<Position, Position> transform = start.getPositionTransform(nextPosition);
        Position next = transform.apply(start);
        while (next.withinBounds()) {
            if (moves.get(next) == linePiece) {
                line.add(next);
            } else if (moves.get(next) == terminator) {
                return line;
            } else {
                break;
            }
            next = transform.apply(next);
        }
        line.clear();
        return line;
    }

    public Map<Player, Integer> currentScore() {
        Map<Player, Integer> score = new HashMap<>();
        score.put(X, 0);
        score.put(O, 0);
        moves.forEach((position, player) -> score.put(player, score.get(player) + 1));

        return score;
    }
}
