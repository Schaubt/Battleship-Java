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

    @Override
    public boolean attack(int col, int row, Player targetPlayer) {
        boolean res = super.attack(col, row, targetPlayer);
        this.handleAIAttack(col, row, targetPlayer);
        return res;
    }

    public int[] determineAttackCoordinate() {
        int row = this.rowOfShipDiscovery;
        int col = this.colOfShipDiscovery;

        this.determineAttackDirection();
        this.determineAttackOrientation();

        if ((Objects.equals(this.lastResult, "Hit")) && !this.shipProbeInProgress) {
            row = this.rowOfLastAttack;
            col = this.colOfLastAttack;
        }

        return this.getAttackCoordinate(row, col);
    }

    public void handleAIAttack(int col, int row, Player targetPlayer) {
        this.storeLastResult(row, col, targetPlayer);
        if (this.lastResult.equals("Hit")) {
            this.handleHit(row, col);
        } else if (this.lastResult.equals("Miss")) {
            this.handleMiss();
        }
    }

    public void handleHit(int row, int col) {
        if (!this.shipProbeInProgress) {
            this.setShipDiscoveryCoordinates(row, col);
        }
        this.toggleShipProbeStatus();
    }

    public void handleMiss() {
        if (this.shipAttackInProgress() && (this.attackIntentionIsWestAndHorizontal() || this.attackIntentionIsSouthAndVertical())) {
            this.setShipDiscoveryCoordinates(-1, -1);
        }
    }

    public int[] getAttackCoordinate(int row, int col) {
        int[] res = new int[2];
        if (!this.shipProbeInProgress && (this.attackIntentionIsWestAndHorizontal() || this.attackIntentionIsSouthAndVertical())) {
            res = this.getRandomCoordinate();
        }
        if (this.attackIntentionIsEastAndHorizontal()) {
            res = this.getEastCoordinate(row, col);
        } else if (this.attackIntentionIsWestAndHorizontal()) {
            res = this.getWestCoordinate(row, col);
        } else if (this.attackIntentionIsNorthAndVertical()) {
            res = this.getNorthCoordinate(row, col);
        } else if (this.attackIntentionIsSouthAndVertical()) {
            res = this.getSouthCoordinate(row, col);
        }
        return res;
    }
    public void storeLastResult(int row, int col, Player targetPlayer){
        this.lastResult = targetPlayer.bottomBoard.grid[row][col];
    }
    public int[] getNorthCoordinate(int row, int col) {
        return new int[]{row + 1, col};
    }

    public int[] getSouthCoordinate(int row, int col) {
        return new int[]{row - 1, col};
    }

    public int[] getEastCoordinate(int row, int col) {
        return new int[]{row, col + 1};
    }

    public int[] getWestCoordinate(int row, int col) {
        return new int[]{row, col - 1};
    }

    public int[] getRandomCoordinate() {
        return new int[]{(int) (Math.random() * 10), (int) (Math.random() * 10)};
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

    public void toggleShipProbeStatus() {
        this.shipProbeInProgress = !this.shipProbeInProgress;
    }

    public boolean attackIntentionIsEastAndHorizontal() {
        return this.attackDirection == 'e' && this.attackOrientation == 'h';
    }

    public boolean attackIntentionIsWestAndHorizontal() {
        return this.attackDirection == 'w' && this.attackOrientation == 'h';
    }

    public boolean attackIntentionIsNorthAndVertical() {
        return this.attackDirection == 'n' && this.attackOrientation == 'v';
    }

    public boolean attackIntentionIsSouthAndVertical() {
        return this.attackDirection == 's' && this.attackOrientation == 'v';
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

    public boolean shipAttackInProgress() {
        return (!this.shipProbeInProgress) && (this.rowOfShipDiscovery != -1) && (this.colOfShipDiscovery != -1) && (this.attackOrientation != 'r');
    }
}