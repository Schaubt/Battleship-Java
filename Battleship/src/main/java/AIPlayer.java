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
    int rowOfLastAttack;
    int colOfLastAttack;
    String lastResult;
    List<String> HitMissHistory = new ArrayList<>();

    public AIPlayer() {
        super(true);
        this.hitMissHistory = new String[10];
        this.shipProbeInProgress = false;
        this.rowOfShipDiscovery = -1;
        this.colOfShipDiscovery = -1;
        this.attackDirection = 'r';
        this.rowOfLastAttack = -1;
        this.colOfLastAttack = -1;
    }

    @Override
    public boolean attack(int col, int row, Player targetPlayer) {
        boolean res = super.attack(col, row, targetPlayer);
        if (Objects.equals(targetPlayer.bottomBoard.grid[row][col], "Hit")) {
            this.lastResult = targetPlayer.bottomBoard.grid[row][col];
            if (this.shipProbeInProgress) {
                if (attackDirection == 'w' || attackDirection == 'e') this.attackOrientation = 'h';
                else if (attackDirection == 'n' || attackDirection == 's') this.attackOrientation = 'v';
                this.shipProbeInProgress = false;
            } else {
                this.rowOfShipDiscovery = row;
                this.colOfShipDiscovery = col;
                this.attackDirection = 'n';
                this.shipProbeInProgress = true;
            }
        } else if (Objects.equals(targetPlayer.bottomBoard.grid[row][col], "Miss")) {
            this.lastResult = targetPlayer.bottomBoard.grid[row][col];
            if (this.shipProbeInProgress) {
                if (this.attackDirection == 'n') this.attackDirection = 'w';
                else if (this.attackDirection == 'w') this.attackDirection = 's';
                else if (this.attackDirection == 's') this.attackDirection = 'e';
            } else if (this.rowOfShipDiscovery != -1 && this.colOfShipDiscovery != -1) {
                if ((this.attackOrientation == 'h' && this.attackDirection == 'e') || (this.attackOrientation == 'v' && this.attackDirection == 's')) {
                    this.rowOfShipDiscovery = -1;
                    this.colOfShipDiscovery = -1;
                    this.attackOrientation = 'n';
                    this.attackDirection = 'r';
                }
            }
        }
        return res;
    }

    public int[] determineAttackCoordinate(Player targetPlayer) {
        int row = -1;
        int col = -1;
        if (this.lastResult == "Hit") {
            if (!this.shipProbeInProgress) {
                if (this.attackOrientation == 'h') {
                    row = this.rowOfLastAttack;
                    col = (this.attackDirection == 'e') ? this.colOfLastAttack + 1 : this.colOfLastAttack - 1;
                } else if (this.attackOrientation == 'v') {
                    col = this.colOfLastAttack;
                    row = (this.attackDirection == 'n') ? this.rowOfLastAttack + 1 : this.rowOfLastAttack - 1;
                }
            }
        } else if (this.lastResult == "Miss") {
            if (!this.shipProbeInProgress) {
                if (this.attackOrientation == 'h') {
                    row = (this.attackDirection == 'e') ? this.rowOfShipDiscovery : (int) (Math.random() * 10);
                    col = (this.attackDirection == 'e') ? this.colOfShipDiscovery - 1 : (int) (Math.random() * 10); // if east, attack west
                    this.attackDirection = (this.attackDirection == 'e') ? 'w' : 'r';
                } else if (this.attackOrientation == 'v') {
                    row = (this.attackDirection == 'n') ? this.rowOfShipDiscovery - 1 : (int) (Math.random() * 10); // if north, attack south
                    col = (this.attackDirection == 'n') ? this.colOfShipDiscovery : (int) (Math.random() * 10);
                    this.attackDirection = (this.attackDirection == 'n') ? 's' : 'r';
                }
            }
        } else {
            if (this.attackOrientation == 'h') {
                row = this.rowOfShipDiscovery;
                col = (this.attackDirection == 'e') ? this.colOfShipDiscovery + 1 : this.colOfShipDiscovery - 1;
            } else if (this.attackOrientation == 'v') {
                col = this.colOfShipDiscovery;
                row = (this.attackDirection == 'n') ? this.rowOfShipDiscovery + 1 : this.rowOfShipDiscovery - 1;
            }
        }
        return new int[]{row, col};
    }
}
