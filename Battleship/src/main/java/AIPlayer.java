import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AIPlayer extends Player {
    String[] hitMissHistory;
    char attackOrientation;
    char attackDirection;
    boolean shipProbeInProgress;
    int rowOfShipDiscovery;
    int colOfShipDiscovery;
    List<String> HitMissHistory = new ArrayList<>();
    public AIPlayer(){
        super(true);
        this.hitMissHistory = new String[10];
        this.shipProbeInProgress = false;
        this.rowOfShipDiscovery = -1;
        this.colOfShipDiscovery = -1;
        this.attackDirection = 'r';
    }
    @Override
    public boolean attack(int col, int row, Player targetPlayer){
        boolean res = super.attack(col, row, targetPlayer);
        if(Objects.equals(targetPlayer.bottomBoard.grid[row][col], "Hit")){
            if(this.rowOfShipDiscovery == -1 && this.colOfShipDiscovery == -1 && this.attackDirection == 'r') {
                this.rowOfShipDiscovery = row;
                this.colOfShipDiscovery = col;
                this.attackDirection = 'n';
                this.shipProbeInProgress = true;
            }
            else if(this.shipProbeInProgress){
                if(attackDirection == 'w' || attackDirection == 'e') this.attackOrientation = 'h';
                else if(attackDirection == 'n' || attackDirection == 's') this.attackOrientation = 'v';

            }
        }
        else if(Objects.equals(targetPlayer.bottomBoard.grid[row][col], "Miss")){
            if(this.shipProbeInProgress){
                if(this.attackDirection == 'n') this.attackDirection = 'w';
                else if(this.attackDirection == 'w') this.attackDirection = 's';
                else if(this.attackDirection == 's') this.attackDirection = 'e';
            }
        }
        return res;
    }
    public void determineAttackCoordinate(){

    }
}
