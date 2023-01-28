import java.util.List;
import java.util.Collections;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public interface Animate {
  public abstract boolean move(int direction, Map map);
  public abstract void animate();
  public abstract boolean moveUp();
  public abstract boolean moveDown();
  public abstract boolean moveRight();
  public abstract boolean moveLeft();
  public abstract void draw(Graphics g, ImageObserver observer);
  public abstract Position getPos();
}
