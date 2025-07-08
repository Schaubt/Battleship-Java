
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class Main extends Application {
    private BorderPane layout;
    private Node viewOne = new ViewOne(this);
    private Node gameView = new GameView(this);
    private Game game;

    @Override
    public void start(Stage primaryStage) {
        game = new Game();
        layout = new BorderPane();
        layout.setCenter(viewOne);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setView(Node view) {
        layout.setCenter(view);
    }
    public Game getGame() {
        return game;
    }
    public Node getViewTwo() {
        return gameView;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
class ViewOne extends StackPane {
    public ViewOne(Main main) {
        setStyle("-fx-background-color: lightblue; -fx-font-size: 30px;");
        Label label = new Label("Battleship the Game - Home");

        Button startBtn = new Button("Start Game");
        startBtn.setOnAction(e -> main.setView(main.getViewTwo()));

        VBox layout = new VBox(20, label, startBtn);
        layout.setAlignment(Pos.CENTER);
        getChildren().add(layout);
    }
}

/* public BorderPane createMenu(){
        MenuItem m1 = new MenuItem("Home");
        m1.setOnAction(e -> setView(viewOne));
        MenuItem m2 = new MenuItem("Game Screen");
        m2.setOnAction(e -> setView(gameView));
        MenuItem m3 = new MenuItem("Exit");
        m3.setOnAction(e -> Platform.exit());
        Menu m = new Menu(
                "Menu", null,
                m1,m2,m3
        );

        // create a menubar
        MenuBar mb = new MenuBar(m);

        // add menu to menubar
        layout = new BorderPane();
        layout.setTop(mb);
        return layout;
    }
     */
//private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
//scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());