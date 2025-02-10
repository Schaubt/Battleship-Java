import java.util.Objects;

public class HumanPlayer extends Player {
    public HumanPlayer(){
        super(false);
    }
    public boolean attack(int col, int row, Player targetPlayer){
        if(col < 0 || col > 9){
            return false;
        }
        else if(row < 0 || row > 9){
            return false;
        }
        else if(Objects.equals(targetPlayer.bottomBoard.grid[row][col],"Miss"))
        {
            return false;
        }
        else if(Objects.equals(targetPlayer.bottomBoard.grid[row][col],"Hit"))
        {
            return false;
        }
        else if(Objects.equals(targetPlayer.bottomBoard.grid[row][col],null)) {
            targetPlayer.bottomBoard.grid[row][col] = "Miss";
        }
        else {
            targetPlayer.bottomBoard.grid[row][col] = "Hit";
        }
        return true;
    }
}
