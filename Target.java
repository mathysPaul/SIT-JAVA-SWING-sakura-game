import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Target extends NPC {
  public Target(Position pos, Map map) {
    super(Map.TARGET, new SpriteSheet("images/Naruto.png", 45, 64, 4, 4, 2, 8), pos, map);
    DISTANCE = 8;
    DELAY = 12;
  }

  @Override
  public void moveNPC(Character player) {
    Double currentDistance = getDistance(pos, player.pos);
    List<Double> distance = new ArrayList<Double>();
    for (int i = 0; i < 4; i++) {
      Position v = DIRECTION.get(i);
      if (checkNext(v, map)) {
        distance.add(getDistance(new Position(pos.x + v.x, pos.y + v.y), player.pos));
      } else {
        distance.add(-1.0);
      }
    }
    List<Double> sorted = new ArrayList<Double>(distance);
    Collections.sort(sorted, Collections.reverseOrder());
    for (Double e: sorted) {
      if (e != -1.0 && currentDistance < e) {
        move(distance.indexOf(e), map);
        break;
      }
    }
  }
}