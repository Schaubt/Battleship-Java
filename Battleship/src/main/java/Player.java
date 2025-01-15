import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Player {
    private static int nextId = 0;
    private int id;
    HashMap<String, Ship> shipsAlive;
    Board topBoard;
    Board bottomBoard;
    String[] attackHistory;
    boolean isComputer;

    public Player(boolean isComputer){
        this.id = nextId++;
        this.shipsAlive = new HashMap<>();
        this.topBoard = new Board();
        this.bottomBoard = new Board();
        this.attackHistory = new String[10];
        this.isComputer = isComputer;
    }
    public void addShip(String name, int size, int row, int col, char orientation){
        List<Map<String, Integer>> coordinates = this.bottomBoard.calcShipPlacementCoordinates(row, col, size, orientation);
        Ship ship = new Ship(name, size, coordinates);
        this.bottomBoard.placeShip(ship);
        this.shipsAlive.put(ship.name, ship);
    }
    //public void removeShip(int row, int col, String orientation){}
}