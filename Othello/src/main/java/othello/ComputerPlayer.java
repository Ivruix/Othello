package othello;

import java.util.List;

/**
 * A computer player that calculates the next move using a formula
 */
public class ComputerPlayer extends Player {
    private final int maxDepth;

    public ComputerPlayer(String name, int maxDepth) {
        super(name);
        this.maxDepth = maxDepth;
    }

    @Override
    public Move getNextMove(Board board) {
        List<Move> validMoves = board.getValidMoves();

        Move bestMove = validMoves.get(0);
        double bestMoveEvaluation = evaluateMoveRecursive(board, bestMove, 1);

        for (int i = 1; i < validMoves.size(); i++) {
            double moveEvaluation = evaluateMoveRecursive(board, validMoves.get(i), 1);
            if (moveEvaluation > bestMoveEvaluation) {
                bestMove = validMoves.get(i);
                bestMoveEvaluation = moveEvaluation;
            }
        }

        return bestMove;
    }

    private double evaluateMove(Move move) {
        double result = 0;

        for (Position position : move.changed()) {
            if (position.x() == 0 || position.x() == 7 || position.y() == 0 || position.y() == 7) {
                result += 2.0;
            } else {
                result += 1.0;
            }
        }

        int moveX = move.position().x();
        int moveY = move.position().y();

        if ((moveX == 0 || moveX == 7) && (moveY == 0 || moveY == 7)) {
            result += 0.8;
        } else if (moveX == 0 || moveX == 7 || moveY == 0 || moveY == 7) {
            result += 0.4;
        }

        return result;
    }

    private double evaluateMoveRecursive(Board board, Move move, int currentDepth) {
        double result = evaluateMove(move);

        if (currentDepth != maxDepth) {
            board.makeMove(move);

            List<Move> validMoves = board.getValidMoves();

            if (validMoves.size() != 0) {
                double bestOpponentMoveEvaluation =
                        evaluateMoveRecursive(board, validMoves.get(0), currentDepth + 1);

                for (int i = 1; i < validMoves.size(); i++) {
                    double moveEvaluation =
                            evaluateMoveRecursive(board, validMoves.get(i), currentDepth + 1);
                    if (moveEvaluation > bestOpponentMoveEvaluation) {
                        bestOpponentMoveEvaluation = moveEvaluation;
                    }
                }

                result -= bestOpponentMoveEvaluation;
            }

            board.undoLastMove();
        }

        return result;
    }
}
