package othello;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Class used for keeping track of the board state
 */
public class Board {

    private int nextDiskColor = 1;
    private final int[][] board = new int[8][8];
    private final Stack<Move> moveHistory = new Stack<>();
    private final List<Move> validMoves = new ArrayList<>();

    public Board() {
        board[4][3] = 1;
        board[3][4] = 1;
        board[3][3] = -1;
        board[4][4] = -1;

        updateValidMoves();
    }

    public void makeMove(Move move) {
        board[move.position().x()][move.position().y()] = nextDiskColor;
        for (Position position : move.changed()) {
            board[position.x()][position.y()] *= -1;
        }

        moveHistory.add(move);
        nextDiskColor *= -1;

        updateValidMoves();
    }

    public void undoLastMove() {
        Move lastMove = moveHistory.pop();

        board[lastMove.position().x()][lastMove.position().y()] = 0;
        for (Position position : lastMove.changed()) {
            board[position.x()][position.y()] *= -1;
        }

        nextDiskColor *= -1;

        updateValidMoves();
    }

    private void updateValidMoves() {
        validMoves.clear();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board[x][y] != 0) {
                    continue;
                }

                List<Position> changed = new ArrayList<>();

                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (dx == 0 && dy == 0) {
                            continue;
                        }

                        List<Position> potentiallyChanged = new ArrayList<>();

                        int currentX = x + dx;
                        int currentY = y + dy;

                        while (currentX >= 0 && currentX <= 7 && currentY >= 0 && currentY <= 7) {
                            if (board[currentX][currentY] == 0) {
                                break;
                            } else if (board[currentX][currentY] == nextDiskColor) {
                                changed.addAll(potentiallyChanged);
                                break;
                            } else {
                                potentiallyChanged.add(new Position(currentX, currentY));
                            }

                            currentX += dx;
                            currentY += dy;
                        }
                    }
                }

                if (!changed.isEmpty()) {
                    validMoves.add(new Move(new Position(x, y), changed));
                }
            }
        }
    }

    public int getNextDiskColor() {
        return nextDiskColor;
    }

    public int getMoveCount() {
        return moveHistory.size();
    }

    public List<Move> getValidMoves() {
        return validMoves;
    }

    public boolean isValidMove(int x, int y) {
        for (Move move : validMoves) {
            if (move.position().x() == x && move.position().y() == y) {
                return true;
            }
        }
        return false;
    }

    public Move getMoveByPosition(int x, int y) {
        for (Move move : validMoves) {
            if (move.position().x() == x && move.position().y() == y) {
                return move;
            }
        }
        return null;
    }

    public int getScore(int color) {
        int result = 0;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board[x][y] == color) {
                    result++;
                }
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("   1  2  3  4  5  6  7  8\n");

        for (int y = 0; y < 8; y++) {
            result.append(y + 1);
            result.append("  ");
            for (int x = 0; x < 8; x++) {


                if (board[x][y] == 1) {
                    result.append('○');
                } else if (board[x][y] == -1) {
                    result.append('●');
                } else {
                    boolean isValidMove = false;
                    for (Move move : validMoves) {
                        if (x == move.position().x() && y == move.position().y()) {
                            result.append('x');
                            isValidMove = true;
                            break;
                        }
                    }
                    if (!isValidMove) {
                        result.append('.');
                    }
                }

                if (x != 7) {
                    result.append("  ");
                }
            }
            if (y != 7) {
                result.append('\n');
            }
        }

        return result.toString();
    }
}
