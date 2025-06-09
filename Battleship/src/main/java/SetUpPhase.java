import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SetUpPhase extends Phase {
    private static final Ship[] ships = {
            new Ship("Carrier", 5),
            new Ship("Battleship", 4),
            new Ship("Destroyer", 3),
            new Ship("Submarine", 3),
            new Ship("Patrol Boat", 2)
    };

    public SetUpPhase(Game game) {
        super(game);
    }

    @Override
    public void execute(Player player1, Player player2) {
        Player[] players = {player1, player2};
        List<Map<String, Integer>> shipCoordinates;
        int row, col;
        char orientation;
        boolean placemenIsValid;

        for (Player player : players) {
            for (Ship ship : ships) {
                do {
                    Input playerInput;
                    if (!player.isComputer) {
                        playerInput = this.getUserInput(System.in);
                    } else {
                        playerInput = this.getAiInput();
                    }
                    shipCoordinates = getShipPlacementCoords(playerInput, player, ship);
                    placemenIsValid = player.bottomBoard.validateShipPlacement(shipCoordinates);

                    if (!placemenIsValid && !player.isComputer) {
                        System.out.println("Invalid input. Try again.");
                    }
                } while (!placemenIsValid);
                ship.setCoordinates(shipCoordinates);
                player.placeShip(ship);
            }
        }

    }

    public Input getUserInput(InputStream in) {
        System.out.println("Enter row (A-Z), column, and orientation (h or v)");
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter(",\\s*");
        int rowNum = scanner.nextInt();
        int colNum = scanner.nextInt();
        char orientation = scanner.next().charAt(0);
        return new Input(rowNum, colNum, orientation);
    }

    public Input getAiInput() {
        int rowNum = (int) (Math.random() * 10) + 1;
        int colNum = (int) (Math.random() * 10) + 1;
        char orientation = Math.random() < 0.5 ? 'v' : 'h';
        return new Input(rowNum, colNum, orientation);
    }
    public List<Map<String, Integer>> getShipPlacementCoords(Input input, Player player, Ship ship){
        int row = input.getRowNum();
        int col = input.getColNum();
        char orientation = input.getOrientation();
        return player.bottomBoard.calcShipPlacementCoordinates(row, col, ship.size, orientation);
    }
}
