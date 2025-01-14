import java.util.HashMap;

public abstract class Player {
    private static int nextId = 0;
    private int id;
    HashMap<String, Ship> shipsAlive;
    Board topBoard;
    Board bottomBoard;
    String[] attackHistory;
    String type;
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
        HashMap[] coordinates = this.bottomBoard.calcShipPlacementCoordinates(row, col, size, orientation);
        Ship ship = new Ship(name, size, coordinates);
        this.bottomBoard.placeShip(ship);
        this.shipsAlive.put(ship.name, ship);
    }
    //public void removeShip(int row, int col, String orientation){}
}