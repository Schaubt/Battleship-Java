import java.util.Scanner;

public abstract class Phase {
    protected Game game;
    public Phase(Game game) {
        this.game = game;
    }
    public abstract void execute(Player player1, Player player2);
}