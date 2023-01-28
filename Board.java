import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Board extends JPanel implements ActionListener, KeyListener {
  private final int DELAY = 25;
  public final int PLAY = 0;
  public final int LOSE = 1;
  public final int WIN = 2;
  private int score;
  public static final int TILE_SIZE = 50;
  static final long serialVersionUID = 42L;
  
  private int state;
  private Map map;
  private Timer timer;
  private Player player;
  private ArrayList<Monster> monsters;
  private ArrayList<Target> targets;
  private SpriteSheet mapSheet = new SpriteSheet("images/background.png", 50, 50, 1, 16);
  private Random rand = new Random();

  public Board(Map map) {
    setPreferredSize(new Dimension(TILE_SIZE * map.getWidth(), TILE_SIZE * map.getHeight()));
    setBackground(new Color(39, 120, 49));

    this.map = map;

    this.state = this.PLAY;

    CharacterFactory factory = new CharacterFactory();
    this.player = (Player)factory.createCharacter(Player.class, this.map.getPlayerPos(), this.map);
    this.monsters = new ArrayList<Monster>();
    for (Position pos: this.map.getMonstersPos())
        this.monsters.add((Monster)factory.createCharacter(Monster.class, pos, this.map));
    this.targets = new ArrayList<Target>();
    for (Position pos: this.map.getTargetsPos())
        this.targets.add((Target)factory.createCharacter(Target.class, pos, this.map));

    this.timer = new Timer(DELAY, this);
    this.timer.start();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (this.state == this.PLAY) {
      for (Monster monster : monsters)
        monster.play(this.player);
      for (Target target : targets)
        target.play(this.player);
      catchByMonster();
      catchTarget();
    }
    repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (this.state == this.PLAY) {
      drawMap(g);
      drawScore(g);
      for (Monster monster : monsters)
        monster.draw(g, this);
      for (Target target : targets)
        target.draw(g, this);
      player.draw(g, this);
    } else {
      drawEndGame(g);
    }
    Toolkit.getDefaultToolkit().sync();
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_UP:
        player.moveUp();
        break;
      case KeyEvent.VK_RIGHT:
        player.moveRight();
        break;
      case KeyEvent.VK_DOWN:
        player.moveDown();
        break;
      case KeyEvent.VK_LEFT:
        player.moveLeft();
        break;
    }
    catchByMonster();
    catchTarget();
  }

  @Override
  public void keyReleased(KeyEvent e) {}

  private void drawMap(Graphics g) {
    int[][] background = this.map.getBackground();
    for (int row = 0; row < map.getHeight(); row++) {
      for (int col = 0; col < map.getWidth(); col++) {
        if (Map.BACKGROUND.contains(background[row][col]) ||
            Map.WALL.contains(background[row][col])) {
          g.drawImage(
            mapSheet.getSprite(background[row][col], 0),
            col * TILE_SIZE,
            row * TILE_SIZE,
            this
          );
        }
      }    
    }
  }

  private void drawEndGame(Graphics g) {
    String text = "";
    if (this.state == this.WIN)
      text = "YOU WIN !!! Dattebayo !";
    if (this.state == this.LOSE)
      text = "YOU LOSE !!!";
    Graphics2D g2d = (Graphics2D)g;
    g2d.setRenderingHint(
      RenderingHints.KEY_TEXT_ANTIALIASING,
      RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2d.setRenderingHint(
      RenderingHints.KEY_RENDERING,
      RenderingHints.VALUE_RENDER_QUALITY);
    g2d.setRenderingHint(
      RenderingHints.KEY_FRACTIONALMETRICS,
      RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g2d.setColor(new Color(239, 132, 82));
    g2d.setFont(new Font("Lato", Font.BOLD, 32));
    FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
    Rectangle rect = new Rectangle(0, TILE_SIZE * (map.getHeight() / 2), TILE_SIZE * map.getWidth(), TILE_SIZE);
    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
    g2d.drawString(text, x, y);
  }

  private void drawScore(Graphics g) {
    String text = "Score: " + String.valueOf(this.score);
    Graphics2D g2d = (Graphics2D)g;
    g2d.setRenderingHint(
      RenderingHints.KEY_TEXT_ANTIALIASING,
      RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2d.setRenderingHint(
      RenderingHints.KEY_RENDERING,
      RenderingHints.VALUE_RENDER_QUALITY);
    g2d.setRenderingHint(
      RenderingHints.KEY_FRACTIONALMETRICS,
      RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g2d.setColor(new Color(239, 132, 82));
    g2d.setFont(new Font("Lato", Font.BOLD, 25));
    FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
    Rectangle rect = new Rectangle(0, TILE_SIZE * (map.getHeight() - 1), TILE_SIZE * map.getWidth(), TILE_SIZE);
    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
    g2d.drawString(text, x, y);
  }

  private void catchTarget() {
    ArrayList<Monster> collectedMonsters = new ArrayList<Monster>();
    for (Target target : targets) {
      if (player.pos.equals(target.pos)) {
        targets.remove(target);
        this.score += 1;
        break;
      }
    }
    if (targets.isEmpty())
      this.state = this.WIN;
  }

  private void catchByMonster() {
    ArrayList<Monster> collectedMonsters = new ArrayList<Monster>();
    for (Monster monster : monsters) {
      if (player.pos.equals(monster.pos)) {
        this.state = this.LOSE;
        break;
      }
    }
  }
}
