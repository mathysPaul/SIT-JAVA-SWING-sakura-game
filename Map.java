import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.awt.Point;

public class Map {
  public final static int PLAYER = 16;
  public final static int MONSTER = 17;
  public final static int TARGET = 18;
  public final int FLOOR = -1;
  public final static List<Integer> WALL = Arrays.asList(12, 13, 14, 15);
  public final static List<Integer> BACKGROUND = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
  public final static List<Integer> CHARACTERS = Arrays.asList(PLAYER, MONSTER, TARGET);

  private int width;
  private int height;
  private int[] size;
  private int[][] map;
  private int[][] maze;
  private int[][] background;
  private Position player;
  private ArrayList<Position> targets;
  private ArrayList<Position> monsters;
  
  public Map(String filename) {
    this.targets = new ArrayList<Position>();
    this.monsters = new ArrayList<Position>();
    this.map = read(filename);
    parser(); 
  }

  public int[][] read(String filename) {
    List<int[]> m = new ArrayList<int[]>();
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      String[] cells;
      for (int row = 0; (line = br.readLine()) != null; row++) {
        cells = line.trim().split("\\s+");
        int newLine[] = new int[cells.length];
        for (int i = 0; i < cells.length; i++) {
          newLine[i] = Integer.parseInt(cells[i].trim());
        }
        m.add(newLine);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid read of map file");
    }
    int[][] array = new int[m.size()][];
    array = m.toArray(array);
    this.height = array.length;
    this.width = array[0].length;
    this.size = new int[]{width, height};
    return array;
  }

  private void parser() {
    this.maze = new int[this.height][];
    this.background = new int[this.height][];
    for (int row = 0; row < this.height; row++) {
      this.maze[row] = new int[this.width];
      this.background[row] = new int[this.width];
      for (int col = 0; col < this.width; col++) {
        if (CHARACTERS.contains(this.map[row][col])) {
          this.background[row][col] = this.FLOOR;
          if (this.map[row][col] == PLAYER) {
            this.player = new Position(row, col);
          } else if (this.map[row][col] == TARGET) {
            this.targets.add(new Position(row, col));
          } else if (this.map[row][col] == MONSTER) {
            this.monsters.add(new Position(row, col));
          }
        } else {
          this.background[row][col] = this.map[row][col];
        }
      }
    }
  }

  public int[][] getMaze(List<Integer> hit, int[][] dataMap) {
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        if (hit.contains(dataMap[row][col])) {
          this.maze[row][col] = 1;
        } else {
          this.maze[row][col] = 0;
        }
      }
    }
    return this.maze;
  }

  // state 0 to remove, 1 to add
  public void update(Position player, List<Position> targets, List<Position> monsters, int state) {
    this.maze[player.x][player.y] = state;
    for (Position target: targets)
      this.maze[target.x][target.y] = state;
    for (Position monster: monsters)
      this.maze[monster.x][monster.y] = state;
  }

  public void update(Position position, int state) {
    this.maze[position.x][position.y] = state;
  }

  public int[] getSize() {
    return this.size;
  }

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }

  public Position getPlayerPos() {
    return this.player;
  }

  public ArrayList<Position> getMonstersPos() {
    return this.monsters;
  }

  public ArrayList<Position> getTargetsPos() {
    return this.targets;
  }

  public int[][] getBackground() {
    return this.background;
  }

  public int[][] get() {
    return this.map;
  }
}
