import java.io.InputStream;
import java.util.*;

public class SetUpPhase extends Phase{
    final String CARRIER_NAME = "Carrier";
    final String BATTLESHIP_NAME = "Battleship";
    final String DESTROYER_NAME = "Destroyer";
    final String SUBMARINE_NAME = "Submarine";
    final String PATROL_BOAT_NAME = "Patrol Boat";
    final int CARRIER_SIZE = 5;
    final int BATTLESHIP_SIZE = 4;
    final int DESTROYER_SIZE = 3;
    final int SUBMARINE_SIZE = 3;
    final int PATROL_BOAT_SIZE = 2;

    public SetUpPhase(){
        super();
    }
    public void execute(Player player){
        Ship carrier = new Ship(CARRIER_NAME, CARRIER_SIZE);
        Ship battleship = new Ship(BATTLESHIP_NAME, BATTLESHIP_SIZE);
        Ship destroyer = new Ship(DESTROYER_NAME, DESTROYER_SIZE);
        Ship submarine = new Ship(SUBMARINE_NAME, SUBMARINE_SIZE);
        Ship patrolBoat = new Ship(PATROL_BOAT_NAME, PATROL_BOAT_SIZE);
        Ship[] ships = {carrier, battleship, destroyer, submarine, patrolBoat};

        if (!player.isComputer) {
            for (int i=0 ; i<ships.length ; i++) {
                System.out.println("Enter row (A-Z), column, and orientation (h or v");
                UserInput userInput = this.getUserInput(System.in);
                int row = userInput.getRowNum();
                int col = userInput.getColNum();
                char orientation = userInput.getOrientation();
                List<Map<String, Integer>> shipCoordinates = player.bottomBoard.calcShipPlacementCoordinates(row, col, ships[i].size, orientation);
                if (player.bottomBoard.validateShipPlacement(shipCoordinates)) {
                    ships[i].setCoordinates(shipCoordinates);
                    player.placeShip(ships[i]);
                }
                else {
                    i--;
                }
            }
        }
    }
    public UserInput getUserInput(InputStream in){
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter(",\\s*");
        int rowNum = scanner.nextInt();
        int colNum = scanner.nextInt();
        char orientation = scanner.next().charAt(0);
        return new UserInput(rowNum, colNum, orientation);
    }
}
