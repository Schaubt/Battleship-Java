import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Player {
    private static int nextId = 0;
    private int id;
    Map<String, Ship> shipsAlive;
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
    public void placeShip(Ship ship ){
        this.bottomBoard.placeShip(ship);
        this.shipsAlive.put(ship.name, ship);
    }
    public void setAliveShips(Ship ship){
        this.shipsAlive.put(ship.name, ship);
    }
    //public void removeShip(int row, int col, String orientation){}
}