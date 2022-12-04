package othello;

/**
 * Abstract player class
 */
public abstract class Player {
    protected final String name;

    protected Player(String name) {
        this.name = name;
    }

    public abstract Move getNextMove(Board board);

    @Override
    public String toString() {
        return name;
    }
}
