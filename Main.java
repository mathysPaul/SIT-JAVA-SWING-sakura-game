import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
  private static void initWindow(String filename) {
    JFrame window = new JFrame("Sakura: The Daily Life of a Ninja");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Map map = new Map(filename);
    Board board = new Board(map);
    window.add(board);
    window.addKeyListener(board);
    window.setResizable(false);
    window.pack();
    window.setLocationRelativeTo(null);
    window.setVisible(true);
  }

  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("The path to the map file is missing!");
      System.exit(0);
    }
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        initWindow(args[0]);
      }
    });
  }
}