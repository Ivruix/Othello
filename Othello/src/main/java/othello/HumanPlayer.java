package othello;

import java.util.Scanner;

/**
 * A human player that gets the next move from console input
 */
public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public Move getNextMove(Board board) {
        Scanner in = new Scanner(System.in);

        boolean choseValidMove = false;
        Move move = null;

        System.out.println();

        while (!choseValidMove) {
            System.out.print("Enter command (enter 'help' for information): ");

            String str = in.next();

            switch (str) {
                case "help":
                    System.out.println("Available commands:");
                    System.out.println("1) move x y (or m x y) - make move in x y position");
                    System.out.println("2) all - view all valid moves");
                    System.out.println("3) undo - undo your last move");
                    break;

                case "m":
                case "move":
                    int x = in.nextInt() - 1;
                    int y = in.nextInt() - 1;
                    if (board.isValidMove(x, y)) {
                        move = board.getMoveByPosition(x, y);
                        choseValidMove = true;
                    } else {
                        System.out.printf("Move %1$d %2$d is invalid.\n", x + 1, y + 1);
                    }
                    break;

                case "all":
                    System.out.println("All valid moves:");
                    for (Move validMove : board.getValidMoves()) {
                        System.out.printf("%1$d %2$d\n", validMove.position().x() + 1, validMove.position().y() + 1);
                    }
                    break;

                case "undo":
                    if (board.getMoveCount() >= 2) {
                        board.undoLastMove();
                        board.undoLastMove();
                        System.out.println();
                        System.out.println("Last move has been undone:");
                        System.out.println(board);
                    } else {
                        System.out.println("Unable to undo move.");
                    }
                    break;

                default:
                    System.out.println("Unable to recognize command.");
                    break;
            }

            if (!choseValidMove) {
                in.nextLine();
                System.out.println();
            }
        }


        return move;
    }
}
