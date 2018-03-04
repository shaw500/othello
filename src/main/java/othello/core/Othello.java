package othello.core;

import java.util.Map;

import static othello.core.Player.*;

public class Othello {

    private Board board;
    private Player currentPlayer;

    public void newGame() {
        currentPlayer = X;
        board = new Board();
    }

    public Board getBoard() {
        return board;
    }

    public Player whoseTurn() {
        return currentPlayer;
    }

    public void placePiece(Position position) {
        board.makeMove(currentPlayer, position);
        progressPlayer();
    }

    private void progressPlayer() {
        Player nextPlayer = currentPlayer.opponent();
        if(!board.validMovesRemain(nextPlayer)) nextPlayer = nextPlayer.opponent();
        if(!board.validMovesRemain(nextPlayer)) nextPlayer = _;
        currentPlayer = nextPlayer;
    }

    public Player[][] renderBoard() {
        return board.render();
    }

    public Map<Player, Integer> currentScore() {
        return board.currentScore();
    }
}