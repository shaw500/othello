package othello.core;

public enum Player {
    X, O, _;

    public Player opponent() {
        return this == X ? O : X;
    }
}
