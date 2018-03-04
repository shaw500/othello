package othello.core.exceptions;

import othello.core.Position;

public class InvalidPositionException extends RuntimeException {

    public Position invalidPosition;

    public InvalidPositionException(Position invalidPosition, String message) {
        super(message);
        this.invalidPosition = invalidPosition;
    }
}
