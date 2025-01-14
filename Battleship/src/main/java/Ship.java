import java.util.HashMap;

public class Ship {
    public String name;
    int size;
    int health;
    HashMap[] occupiedCoordinates;

    public Ship(String name, int size, HashMap[] coordinates){
        this.name = name;
        this.size = size;
        this.health = size;
        this.occupiedCoordinates = coordinates;
    }
    //public void hit() {}
}
