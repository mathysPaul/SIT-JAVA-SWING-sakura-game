import java.util.List;
import java.util.Arrays;

public abstract class NPC extends Character {
  public static int DELAY = 16;
  protected int delay = DELAY;
  public static int DISTANCE = 16;

  public NPC(int symbole, SpriteSheet spriteSheet, Position pos, Map map) {
    super(symbole, spriteSheet, pos, map);
    this.HITBOX = Arrays.asList(12, 13, 14, 15, 16, 17, 18);
  }

  public void play(Character player) {
    if (this.delay == 0) {
      this.delay = DELAY;
      if (closeToPlayer(player)) {
        moveNPC(player);
      }
    } else {
      this.delay--;
    }
  }

  public Double getDistance(Position a, Position b) {
    int dx = a.x - b.x;
    int dy = a.y - b.y;
    return Math.sqrt(dx*dx + dy*dy);
  }

  public boolean closeToPlayer(Character player) {
    return getDistance(this.pos, player.pos) <= DISTANCE;
  }

  public abstract void moveNPC(Character player);
}
