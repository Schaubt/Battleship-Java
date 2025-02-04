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

    public void execute(Player player1, Player player2) {
        Player[] players = {player1, player2};
        List<Map<String, Integer>> shipCoordinates;
        int row, col;
        char orientation;
        boolean placemenIsValid;

        for (Player player : players) {
            for (Ship ship : ships) {
                do {
                    if (!player.isComputer) {
                        System.out.println("Enter row (A-Z), column, and orientation (h or v)");
                        UserInput userInput = this.getUserInput(System.in);
                        row = userInput.getRowNum();
                        col = userInput.getColNum();
                        orientation = userInput.getOrientation();
                    } else {
                        row = (int) (Math.random() * 10);
                        col = (int) (Math.random() * 10);
                        orientation = Math.random() < 0.5 ? 'v' : 'h';
                    }
                    shipCoordinates = player.bottomBoard.calcShipPlacementCoordinates(row, col, ship.size, orientation);
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

    public UserInput getUserInput(InputStream in) {
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter(",\\s*");
        int rowNum = scanner.nextInt();
        int colNum = scanner.nextInt();
        char orientation = scanner.next().charAt(0);
        return new UserInput(rowNum, colNum, orientation);
    }
}
