public class Player extends Character {
    public Player(Position pos, Map map) {
        super(Map.PLAYER, new SpriteSheet("images/Sakura.png", 47, 64, 4, 8, 2, 8), pos, map);
    }
}
