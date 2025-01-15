import java.util.List;
import java.util.Map;

public class Ship {
    public String name;
    int size;
    int health;
    List<Map<String, Integer>> occupiedCoordinates;

    public Ship(String name, int size, List<Map<String, Integer>> coordinates){
        this.name = name;
        this.size = size;
        this.health = size;
        this.occupiedCoordinates = coordinates;
    }
    //public void hit() {}
}
