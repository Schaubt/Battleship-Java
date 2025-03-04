import java.io.InputStream;
import java.util.Scanner;

public class AttackPhase extends Phase {
    public AttackPhase(Game game) {
        super(game);
    }

    @Override
    public void execute(Player player1, Player player2) {

    }

    public Input getUserInput(InputStream in) {
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter(",\\s*");
        int rowNum = scanner.nextInt();
        int colNum = scanner.nextInt();
        return new Input(rowNum, colNum);
    }
}
