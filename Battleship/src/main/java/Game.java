public class Game {
    Phase currentPhase;
    public Game(){
        this.currentPhase = new SetUpPhase(this); // Start with the initial phase
    }
    public void start()
    {
    }
}
