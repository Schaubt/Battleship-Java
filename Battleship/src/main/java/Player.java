import java.util.*;

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
        if(col < 1 || col > 10){
            System.out.println("This coordinate is out of bounds");
            return false;
        }
        else if(row < 1 || row > 10){
            System.out.println("This coordinate is out of bounds");
            return false;
        }
        else if(Objects.equals(targetPlayer.bottomBoard.grid[row][col],"Miss"))
        {
            System.out.println("This coordinate has already been attacked");
            return false;
        }
        else if(Objects.equals(targetPlayer.bottomBoard.grid[row][col],"Hit"))
        {
            System.out.println("This coordinate has already been attacked");
            return false;
        }
        else if(Objects.equals(targetPlayer.bottomBoard.grid[row][col],null)) {
            System.out.println("Miss!");
            targetPlayer.bottomBoard.grid[row][col] = "Miss";
            System.out.println(Arrays.deepToString(targetPlayer.bottomBoard.grid));

        }
        else {
            System.out.println("Hit!");
            targetPlayer.bottomBoard.grid[row][col] = "Hit";
            System.out.println(Arrays.deepToString(targetPlayer.bottomBoard.grid));
        }
        return true;
    }
    public void setAliveShips(Ship ship){
        this.shipsAlive.put(ship.name, ship);
    }
    //public void removeShip(int row, int col, String orientation){}
    public boolean isComputer() {
        return this.isComputer;
    }
    public boolean allShipsDestroyed(){
        return shipsAlive.isEmpty();
    }
}