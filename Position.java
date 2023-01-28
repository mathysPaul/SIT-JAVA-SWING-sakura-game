public class Position {
  public int x;
  public int y;
  public int[] point;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
    this.point = new int[]{x, y};
  }

  boolean equals(Position p) {
    return this.x == p.x && this.y == p.y;
  }

  boolean equals(int x, int y) {
    return this.x == x && this.y == y;
  }
}