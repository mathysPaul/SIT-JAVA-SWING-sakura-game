import java.util.Arrays;
import java.util.List;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class Character implements Animate {
  public static final int DOWN = 0;
  public static final int LEFT = 1;
  public static final int RIGHT = 2;
  public static final int UP = 3;
  public static final List<Position> DIRECTION = Arrays.asList(
    new Position(1, 0),
    new Position(0, -1),
    new Position(0, 1),
    new Position(-1, 0)
  );

  protected SpriteSheet sprites;
  protected Position pos;
  protected int frame;
  protected int direction;
  protected boolean isAnimate;
  protected Map map;
  public int symbole;
  protected List<Integer> HITBOX;

  public Character(int symbole, SpriteSheet sprites, Position pos, Map map) {
    this.sprites = sprites;
    this.pos = pos;
    this.frame = 0;
    this.direction = 0;
    this.map = map;
    this.symbole = symbole;
    this.HITBOX = Map.WALL;
  }

  public void animate() {
    this.frame = (this.frame + 1) % this.sprites.getNumberX();
  }

  public boolean checkNext(Position v, Map map) {
    int[][] m = map.get();

    return (pos.x + v.x >= 0 && pos.y + v.y >= 0 &&
      pos.x + v.x < map.getHeight() &&
      pos.y + v.y < map.getWidth() &&
      !HITBOX.contains(m[pos.x + v.x][pos.y + v.y]));
  }

  public boolean move(int direction, Map map) {
    Position v = DIRECTION.get(direction);
    int[][] background = this.map.getBackground();
    int[][] m = this.map.get();

    if (checkNext(v, map)) {
      this.direction = direction;
      this.animate();
      m[this.pos.x][this.pos.y] = background[this.pos.x][this.pos.y];
      this.pos.x += v.x;
      this.pos.y += v.y;
      m[this.pos.x][this.pos.y] = this.symbole;      
      return true;
    }
    return false;
  }

  public boolean moveUp() {
    return move(UP, map);
  }

  public boolean moveDown() {
    return move(DOWN, map);
  }

  public boolean moveRight() {
    return move(RIGHT, map);
  }

  public boolean moveLeft() {
    return move(LEFT, map);
  }

  public void draw(Graphics g, ImageObserver observer) {
    g.drawImage(
      sprites.getSprite(this.frame, this.direction),
      this.pos.y * Board.TILE_SIZE,
      this.pos.x * Board.TILE_SIZE,
      observer
    );
  }

  public Position getPos() {
    return this.pos;
  }
}