package othello;

/**
 * A class that handles game logic
 */
public class OthelloGame {
    private final Board board = new Board();
    private final Player playerOne, playerTwo;

    public OthelloGame(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public void makeNextMove() {
        if (gameEnded()) {
            return;
        }

        printBoard();

        Move move;

        if (board.getNextDiskColor() == 1) {
            move = playerOne.getNextMove(board);
        } else {
            move = playerTwo.getNextMove(board);
        }

        System.out.println();

        board.makeMove(move);
    }

    public void printBoard() {
        System.out.println(board);
        if (gameEnded()) {

            System.out.printf("%s: %d\n", playerOne, board.getScore(1));
            System.out.printf("%s: %d\n", playerTwo, board.getScore(-1));
            if (board.getScore(1) > board.getScore(-1)) {
                System.out.printf("%s won!\n", playerOne);
            } else if (board.getScore(1) < board.getScore(-1)) {
                System.out.printf("%s won!\n", playerTwo);
            } else {
                System.out.println("Draw!");
            }
        } else {
            if (board.getNextDiskColor() == 1) {
                System.out.printf("> %s: %d\n", playerOne, board.getScore(1));
                System.out.printf("  %s: %d\n", playerTwo, board.getScore(-1));
            } else {
                System.out.printf("  %s: %d\n", playerOne, board.getScore(1));
                System.out.printf("> %s: %d\n", playerTwo, board.getScore(-1));
            }
        }
    }

    public boolean gameEnded() {
        return board.getValidMoves().isEmpty();
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public int getScore(int color) {
        return board.getScore(color);
    }
}
