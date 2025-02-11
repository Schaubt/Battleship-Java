public class AIPlayer extends Player {
    String[] hitMissHistory;
    char attackOrientation;
    char attackDirection;
    public AIPlayer(){
        super(true);
        this.hitMissHistory = new String[10];
    }
    public void attack(){}
}
