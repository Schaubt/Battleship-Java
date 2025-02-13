import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public void placeShip(Ship ship){
        this.bottomBoard.placeShip(ship);
        this.shipsAlive.put(ship.name, ship);
    }
    public boolean attack(int col, int row, Player targetPlayer){
        if(col < 0 || col > 9){
            return false;
        }
        else if(row < 0 || row > 9){
            return false;
        }
        else if(Objects.equals(targetPlayer.bottomBoard.grid[row][col],"Miss"))
        {
            return false;
        }
        else if(Objects.equals(targetPlayer.bottomBoard.grid[row][col],"Hit"))
        {
            return false;
        }
        else if(Objects.equals(targetPlayer.bottomBoard.grid[row][col],null)) {
            targetPlayer.bottomBoard.grid[row][col] = "Miss";
        }
        else {
            targetPlayer.bottomBoard.grid[row][col] = "Hit";
        }
        return true;
    }
    public void setAliveShips(Ship ship){
        this.shipsAlive.put(ship.name, ship);
    }
    //public void removeShip(int row, int col, String orientation){}
}