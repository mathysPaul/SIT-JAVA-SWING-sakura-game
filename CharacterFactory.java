import java.lang.Object;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class CharacterFactory<T extends Animate> {
  public T createCharacter(Class<T> type, Position pos, Map map) {
    try {
      Constructor<T> constructor = type.getConstructor(Position.class, Map.class);
      return constructor.newInstance(pos, map);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new IllegalArgumentException("Invalid character type");
    }
  }
}
