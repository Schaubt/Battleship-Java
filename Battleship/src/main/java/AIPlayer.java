public class AIPlayer extends Player {
    String[] hitMissHistory;
    public AIPlayer(){
        super(true);
        this.hitMissHistory = new String[10];
    }
    public void attack(){}
}
