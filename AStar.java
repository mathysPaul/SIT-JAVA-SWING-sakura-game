import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AStar {
  private List<Position> path;
  private int[][] maze;
  private Position start;
  private Position end;
  private int count;
  private static final List<Position> VECTORS = Arrays.asList(
    new Position(0, -1),
    new Position(0, 1),
    new Position(-1, 0),
    new Position(1, 0),
    new Position(-1, -1),
    new Position(-1, 1),
    new Position(1, -1),
    new Position(1, 1)
  );

  public boolean contains(int x, int y) {
    for (Position step: this.path)
      if (step.equals(x, y))
        return true;
    return false;
  }

  public AStar(int[][] maze, Position start, Position end) {
    this.count = 1;
    this.maze = maze;
    this.start = start;
    this.end = end;
    maze[start.x][start.y] = 0;
    maze[end.x][end.y] = 0;
    this.path = process(maze, start, end);
  }

  public static List<Position> process(int[][] maze, Position start, Position end) {
    Node startNode = new Node(null, start);
    Node endNode = new Node(null, end);

    List<Node> openList = new ArrayList<>();
    List<Node> closedList = new ArrayList<>();

    openList.add(startNode);

    while (!openList.isEmpty()) {
      Node cur = openList.get(0);
      int currentIndex = 0;
      for (int i = 0; i < openList.size(); i++) {
        if (openList.get(i).f < cur.f) {
          cur = openList.get(i);
          currentIndex = i;
        }
      }
      openList.remove(currentIndex);
      closedList.add(cur);

      if (cur.equals(endNode)) {
        List<Position> path = new ArrayList<>();
        while (cur != null) {
          path.add(cur.pos);
          cur = cur.parent;
        }
        Collections.reverse(path);
        return path;
      }

      List<Node> children = new ArrayList<>();
      for (Position v : VECTORS) {
        Position nodePos = new Position(cur.pos.x + v.x, cur.pos.y + v.y);
        if (nodePos.x < 0 ||
            nodePos.y < 0 ||
            nodePos.x > maze.length - 1 ||
            nodePos.y > maze[0].length - 1 ||
            maze[nodePos.x][nodePos.y] != 0) {
          continue;
        }
        Node newNode = new Node(cur, nodePos);
        children.add(newNode);
      }

      for (Node child : children) {
        if (closedList.contains(child)) {
          continue;
        }
        child.g = cur.g + 1;
        child.h = (child.pos.x - endNode.pos.x) * (child.pos.x - endNode.pos.x) + (child.pos.y - endNode.pos.y) * (child.pos.y - endNode.pos.y);
        child.f = child.g + child.h;

        boolean openContains = false;
        for (Node openNode : openList) {
          if (child.equals(openNode) && child.g > openNode.g) {
            openContains = true;
            break;
          }
        }
        if (!openContains) {
          openList.add(child);
        }
      }
    }
    return null;
  }

  public Position getNextPos() {
    if (this.path != null && this.path.size() > this.count) {
      return this.path.get(this.count++);
    }
    return null;
  }
}