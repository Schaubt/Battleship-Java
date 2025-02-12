import java.util.ArrayList;
import java.util.List;

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
    public void attack(){}
    public void determineAttackCoordinate(){

    }
}
