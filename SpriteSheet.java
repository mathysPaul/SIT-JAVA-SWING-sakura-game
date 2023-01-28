import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class SpriteSheet {
  private BufferedImage sheet;
  private BufferedImage[][] sprites;
  private int width;
  private int height;
  private int numberX;
  private int numberY;

  public SpriteSheet(String path, int width, int height, int numberX, int numberY) {
    createSpriteSheet(path, width, height, numberX, numberY, 0, 0);
  }

  public SpriteSheet(String path, int width, int height, int numberX, int numberY, int marginX, int marginY) {
    createSpriteSheet(path, width, height, numberX, numberY, marginX, marginY);
  }

  private void createSpriteSheet(String path, int width, int height, int numberX, int numberY, int marginX, int marginY) {
    try {
      this.numberY = numberY;
      this.numberX = numberX;
      this.width = width;
      this.height = height;
      this.sheet = ImageIO.read(new File(path));
      sprites = new BufferedImage[this.numberY][];
      for (int x = 0; x < this.numberY; x++) {
        sprites[x] = new BufferedImage[this.numberX];
        for (int y = 0; y < this.numberX; y++) {
          int left = x * width;
          int top = y * height;
          int w = width - (marginX * 2);
          int h = height - (marginY * 2);
          this.sprites[x][y] = this.sheet.getSubimage(left, top, w, h);
        }
      }
    } catch (IOException e) {
      System.out.println("Error opening image: " + e.getMessage());
    }
  }

  public int getNumberX() {
    return numberX;
  }

  public int getNumberY() {
    return numberY;
  }

  public BufferedImage getSprite(int x, int y) {
    return this.sprites[x][y];
  }
}