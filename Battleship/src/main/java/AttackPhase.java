import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AttackPhase extends Phase {
    public AttackPhase(Game game) {
        super(game);
    }

    @Override
    public void execute(Player player1, Player player2) {
        Player[] players = {player1, player2};

        for (int i = 0; i <=1 ; i++) {
            Input playerInput;
            int targetIndex = (i == 0) ? 1 : 0;

            if (!players[i].isComputer()) {
                playerInput = this.getUserInput(System.in);
            } else {
                playerInput = this.getAiInput();
            }

            players[i].attack(playerInput.getRowNum(), playerInput.getColNum(), players[targetIndex]);
        }
    }

    public Input getUserInput(InputStream in) {
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter(",\\s*");
        int rowNum = scanner.nextInt();
        int colNum = scanner.nextInt();
        return new Input(rowNum, colNum);
    }

    public Input getAiInput() {
        int rowNum = (int) (Math.random() * 10) + 1;
        int colNum = (int) (Math.random() * 10) + 1;
        return new Input(rowNum, colNum);
    }
}
