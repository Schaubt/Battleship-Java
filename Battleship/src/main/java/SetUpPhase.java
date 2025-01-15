import java.io.InputStream;
import java.util.Scanner;

public class SetUpPhase extends Phase{
    public SetUpPhase(){
        super();
    }
    public void execute(Player Player1){
        int row;
        int col;
        char orientation;
        System.out.println("Enter row (A-Z), column, and orientation (h or v");
        UserInput userInput = this.getUserInput(System.in);
        row = userInput.getRowNum();
        col = userInput.getColNum();
        orientation = userInput.getOrientation();

        Player1.bottomBoard.calcShipPlacementCoordinates(row, col,5, orientation);
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
