import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;
import java.util.Objects;

class GameView extends VBox {
    private GridPane gridPane;
    private Game game;
    private HumanPlayer player;
    private AIPlayer player2;
    private int shipIndex = 0;
    Button[][] topGrid;
    Button[][] bottomGrid;
    SetUpPhase setUpPhase;
    private static final Ship[] ships = {
            new Ship("Carrier", 5),
            new Ship("Battleship", 4),
            new Ship("Destroyer", 3),
            new Ship("Submarine", 3),
            new Ship("Patrol Boat", 2)
    };
    private char orientation = 'h'; // Default orientation
    String orientationString = "horizontal";

    public GameView(Main main) {
        this.game = main.getGame();
        this.setUpPhase = new SetUpPhase(game);
        this.player = new HumanPlayer();
        this.player2 = new AIPlayer();
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);

        Label label = new Label("Place your ships");

        Button orientationToggle = OrientationToggleButton();

        GridPane topGameBoard = createTopGameBoard();
        GridPane bottomGameBoard = createBottomGameBoard();

        getChildren().addAll(label, orientationToggle, topGameBoard, bottomGameBoard);
    }
    public Button OrientationToggleButton(){
        Button btn = new Button("Toggle Orientation: " + orientationString);
        btn.setOnAction(e -> {
            orientation = (orientation == 'h') ? 'v' : 'h';
            orientationString = (orientation == 'h') ? "horizontal" : "vertical";
            btn.setText("Orientation: " + orientationString);
        });
        return btn;
    }
    public GridPane createBottomGameBoard(){
        bottomGrid = new Button[11][11];

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        for(int row=1 ; row<11 ; row++){
            for(int col=1 ; col<11 ; col++){
                Button btn = new Button();
                btn.setMinSize(20,20);
                btn.setMaxSize(20,20);

                int r = row, c = col;
                btn.setOnAction(e -> handleGridClick(r, c, btn));

                bottomGrid[r][c] = btn;
                gridPane.add(bottomGrid[row][col], col, row);
            }
        }
        return gridPane;
    }

    public GridPane createTopGameBoard(){
        topGrid = new Button[11][11];

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        for(int row=1 ; row<11 ; row++){
            for(int col=1 ; col<11 ; col++){
                Button btn = new Button();
                btn.setMinSize(20,20);
                btn.setMaxSize(20,20);

                int r = row, c = col;
                //btn.setOnAction(e -> handleGridClick(r, c, btn));

                topGrid[r][c] = btn;
                gridPane.add(topGrid[row][col], col, row);
            }
        }
        return gridPane;
    }

    private void handleGridClick(int row, int col, Button clickedButton) {
        if (shipIndex >= ships.length) return;
        Ship currentShip = ships[shipIndex];
        List<Map<String, Integer>> coords = player.bottomBoard.calcShipPlacementCoordinates(row, col, currentShip.size, orientation);
        System.out.println(coords);
        if (player.bottomBoard.validateShipPlacement(coords)) {
            currentShip.setCoordinates(coords);
            player.placeShip(currentShip);

            // Change color of occupied spaces
            for (Map<String, Integer> point : coords) {
                int r = point.get("row");
                int c = point.get("col");

                Button btn = bottomGrid[r][c];
                btn.setStyle("-fx-background-color: #878787;");
            }
            shipIndex++;

            if (shipIndex >= ships.length) {
                System.out.println("All ships placed! Ready to start playing.");
                this.setUpPhase.setAIPlayerShips(player2);
                reloadWithNewComponents();
            }
        } else {
            System.out.println("Invalid ship placement. Try again.");
        }
    }

    public GridPane createAttackPhaseBottomGameBoard(){
        bottomGrid = new Button[11][11];

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        for(int row=1 ; row<11 ; row++){
            for(int col=1 ; col<11 ; col++){
                Button btn = new Button();
                btn.setMinSize(20,20);
                btn.setMaxSize(20,20);

                int r = row, c = col;
                //btn.setOnAction(e -> handleAttackPhaseGridClick(r, c, btn));
                if(!player.bottomBoard.coordIsAvailable(r, c)){
                    btn.setStyle("-fx-background-color: #878787;");
                }
                bottomGrid[r][c] = btn;
                gridPane.add(bottomGrid[row][col], col, row);
            }
        }
        return gridPane;
    }

    public GridPane createAttackPhaseTopGameBoard(){
        topGrid = new Button[11][11];

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        for(int row=1 ; row<11 ; row++){
            for(int col=1 ; col<11 ; col++){
                Button btn = new Button();
                btn.setMinSize(20,20);
                btn.setMaxSize(20,20);

                int r = row, c = col;
                btn.setOnAction(e -> handleAttackPhaseGridClick(r, c, btn));

                topGrid[r][c] = btn;
                gridPane.add(topGrid[row][col], col, row);
            }
        }
        return gridPane;
    }

    private void handleAttackPhaseGridClick(int row, int col, Button clickedButton) {
        Button btn = topGrid[row][col];
        if(player.attack(row, col, player2)){
            if(Objects.equals(player2.bottomBoard.grid[row][col],"Hit")){
                btn.setStyle("-fx-background-color: red;");
            }
            else{
                btn.setStyle("-fx-background-color: yellow;");
            }
        }
    }

    public void reloadWithNewComponents() {
        this.getChildren().clear();

        Label newLabel = new Label("");
        GridPane topGameBoard = createAttackPhaseTopGameBoard();
        GridPane bottomGameBoard = createAttackPhaseBottomGameBoard();

        this.getChildren().addAll(newLabel, topGameBoard, bottomGameBoard);
    }
}
