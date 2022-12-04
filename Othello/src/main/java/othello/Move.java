package othello;

import java.util.List;

/**
 * A record that contains a move's position and the positions of disks that have been changed
 */
public record Move(Position position, List<Position> changed) {
}
