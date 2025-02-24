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
    boolean shipDiscovered;

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

    public void setShipDiscoveryCoordinates(int row, int col) {
        this.rowOfShipDiscovery = row;
        this.colOfShipDiscovery = col;
    }

    public void setAttackOrientation(char orientation) {
        this.attackOrientation = orientation;
    }

    public void setAttackDirection(char dir) {
        this.attackDirection = dir;
    }

    public boolean attackDirectionIsEastOrWest() {
        return this.attackDirection == 'w' || this.attackDirection == 'e';
    }

    public boolean attackDirectionIsNorthOrSouth() {
        return this.attackDirection == 'n' || this.attackDirection == 's';
    }
    public void toggleShipProbeStatus(){
        this.shipProbeInProgress = !this.shipProbeInProgress;
    }

    public boolean attackIntentionIsEastAndHorizontal() {
        return this.attackDirection == 'e' && this.attackOrientation == 'h';
    }

    public boolean attackIntentionIsNorthAndVertical() {
        return this.attackDirection == 'n' && this.attackOrientation == 'v';
    }

    @Override
    public boolean attack(int col, int row, Player targetPlayer) {
        boolean res = super.attack(col, row, targetPlayer);
        this.handleAIAttack(col, row, targetPlayer);
        return res;
    }

    public void handleAIAttack(int col, int row, Player targetPlayer) {
        this.lastResult = targetPlayer.bottomBoard.grid[row][col];
        if (this.lastResult == "Hit") {
            this.handleHit(row, col);
        } else if (this.lastResult == "Miss") {
            this.handleMiss();
        }
    }

    public void handleHit(int row, int col) {
        if (this.shipProbeInProgress) {
            this.toggleShipProbeStatus();
        } else {
            this.setShipDiscoveryCoordinates(row, col);
            this.toggleShipProbeStatus();
        }
    }

    public void handleMiss() {
        if (this.shipAttackInProgress()) {
            if ((this.attackOrientation == 'h' && this.attackDirection == 'w') || (this.attackOrientation == 'v' && this.attackDirection == 's')) {
                this.setShipDiscoveryCoordinates(-1, -1);
            }
        }
    }

    public void determineAttackOrientation() {
        if (Objects.equals(this.lastResult, "Hit") && this.shipProbeInProgress) {
            if (this.attackDirectionIsEastOrWest()) setAttackOrientation('h');
            else if (this.attackDirectionIsNorthOrSouth()) setAttackOrientation('v');
        }
    }

    public void determineAttackDirection() {
        if (Objects.equals(this.lastResult, "Hit")) {
            if (!this.shipAttackInProgress()) {
                setAttackDirection('n');
            }
        } else if (Objects.equals(this.lastResult, "Miss")) {
            if (this.shipProbeInProgress) {
                switch (this.attackDirection) {
                    case 'n' -> setAttackDirection('w');
                    case 'w' -> setAttackDirection('s');
                    case 's' -> setAttackDirection('e');
                }
            } else if (this.shipAttackInProgress()) {
                if (this.attackIntentionIsEastAndHorizontal()) {
                    this.setAttackDirection('w');
                } else if (this.attackIntentionIsNorthAndVertical()) {
                    this.setAttackDirection('s');
                } else {
                    this.setAttackDirection('r');
                }
            }
        }
    }

    public int[] determineAttackCoordinate(Player targetPlayer) {
        int row = -1;
        int col = -1;
        this.determineAttackDirection();
        this.determineAttackOrientation();
        System.out.println(this.attackDirection);
        if (this.lastResult == "Hit") {
            if (!this.shipProbeInProgress) {
                //getNextCoordinateInAttackDirection()
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
                    row = (this.attackDirection == 'w') ? this.rowOfShipDiscovery : (int) (Math.random() * 10);
                    col = (this.attackDirection == 'w') ? this.colOfShipDiscovery - 1 : (int) (Math.random() * 10); // if east, attack west
                } else if (this.attackOrientation == 'v') {
                    row = (this.attackDirection == 's') ? this.rowOfShipDiscovery - 1 : (int) (Math.random() * 10); // if north, attack south
                    col = (this.attackDirection == 's') ? this.colOfShipDiscovery : (int) (Math.random() * 10);
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

    public boolean shipAttackInProgress() {
        return (!this.shipProbeInProgress) && (this.rowOfShipDiscovery != -1) && (this.colOfShipDiscovery != -1) && (this.attackOrientation != 'r');
    }
}
