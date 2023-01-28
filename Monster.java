public class Monster extends NPC {
  public Monster(Position pos, Map map) {
      super(Map.MONSTER, new SpriteSheet("images/Sasuke.png", 45, 64, 4, 8, 2, 8), pos, map);
    }

  @Override
  public void moveNPC(Character player) {
    int[][] m = this.map.get();
    AStar astar = new AStar(map.getMaze(HITBOX, m), this.pos, player.pos);
    Position next = astar.getNextPos();

    if (next != null) {
      if (next.y < pos.y)
        moveLeft();
      if (next.y > pos.y)
        moveRight();
      if (next.x < pos.x)
        moveUp();
      if (next.x > pos.x)
        moveDown();
    }
  }
}
