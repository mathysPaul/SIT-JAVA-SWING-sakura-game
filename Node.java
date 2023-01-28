public class Node {
  Node parent;
  Position pos;
  int g, h, f;

  Node(Node parent, Position pos) {
    this.parent = parent;
    this.pos = pos;
    this.g = 0;
    this.h = 0;
    this.f = 0;
  }

  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Node)) {
      return false;
    }
    Node other = (Node) obj;
    return pos.equals(other.pos);
  }
}