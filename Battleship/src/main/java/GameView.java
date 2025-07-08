import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;

class GameView extends VBox {
    private GridPane gridPane;
    private Game game;
    private Player player;
    private int shipIndex = 0;
    Button[][] grid;
    private static final Ship[] ships = {
            new Ship("Carrier", 5),
            new Ship("Battleship", 4),
            new Ship("Destroyer", 3),
            new Ship("Submarine", 3),
            new Ship("Patrol Boat", 2)
    };
    private char orientation = 'h'; // Default orientation

    public GameView(Main main) {
        this.game = main.getGame();
        this.player = new HumanPlayer();
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);

        Label label = new Label("Place your ships");

        Button orientationToggle = OrientationToggleButton();

        GridPane gameBoard = createGameBoard();

        getChildren().addAll(label, orientationToggle, gameBoard);
    }
    public Button OrientationToggleButton(){
        Button btn = new Button("Toggle Orientation (H/V)");
        btn.setOnAction(e -> {
            orientation = (orientation == 'h') ? 'v' : 'h';
            btn.setText("Orientation: " + orientation);
        });
        return btn;
    }
    public GridPane createGameBoard(){
        grid = new Button[10][10];

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        for(int row=0 ; row<10 ; row++){
            for(int col=0 ; col<10 ; col++){
                Button btn = new Button();
                btn.setMinSize(20,20);
                btn.setMaxSize(20,20);

                int r = row, c = col;
                btn.setOnAction(e -> handleGridClick(r, c, btn));

                grid[r][c] = btn;
                gridPane.add(grid[row][col], col, row);
            }
        }
        return gridPane;
    }

    private void handleGridClick(int row, int col, Button clickedButton) {
        if (shipIndex >= ships.length) return;
        Ship currentShip = ships[shipIndex];
        List<Map<String, Integer>> coords = player.bottomBoard.calcShipPlacementCoordinates(row, col, currentShip.size, orientation);

        if (player.bottomBoard.validateShipPlacement(coords)) {
            currentShip.setCoordinates(coords);
            player.placeShip(currentShip);

            // Change color of occupied spaces to green
            for (Map<String, Integer> point : coords) {
                int r = point.get("row");
                int c = point.get("col");

                Button btn = grid[r][c];
                btn.setStyle("-fx-background-color: green;");
            }
            shipIndex++;

            if (shipIndex >= ships.length) {
                System.out.println("All ships placed! Ready to start playing.");
                //game.nextphase placeholder
            }
        } else {
            System.out.println("Invalid ship placement. Try again.");
        }
    }
}
