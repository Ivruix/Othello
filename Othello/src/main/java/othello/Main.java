package othello;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int highestScore = 0;

        boolean quit = false;
        while (!quit) {
            OthelloGame game = null;

            System.out.println("Choose game mode:");
            System.out.println("1) Player vs Computer (easy)");
            System.out.println("2) Player vs Computer (hard)");
            System.out.println("3) Player vs Player");
            System.out.println("4) Quit");
            System.out.printf("Current highest score: %d\n", highestScore);

            while (game == null && !quit) {
                System.out.print("Enter command: ");

                String str = in.nextLine();
                switch (str) {
                    case "1" -> game = new OthelloGame(
                            new HumanPlayer("Player"),
                            new ComputerPlayer("Computer (easy)", 1));
                    case "2" -> game = new OthelloGame(
                            new HumanPlayer("Player"),
                            new ComputerPlayer("Computer (hard)", 2));
                    case "3" -> game = new OthelloGame(
                            new HumanPlayer("Player 1"),
                            new HumanPlayer("Player 2"));
                    case "4" -> quit = true;
                    default -> System.out.println("Unable to recognize command!");
                }

                System.out.println();
            }

            if (!quit) {
                while (!game.gameEnded()) {
                    game.makeNextMove();
                }

                game.printBoard();

                if (game.getPlayerTwo() instanceof ComputerPlayer) {
                    int score = game.getScore(1);
                    if (score > highestScore) {
                        highestScore = score;
                    }
                }

                System.out.println();
            }
        }
    }
}