import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AttackPhaseTest {
    Game game = new Game();
    AttackPhase attackPhase = new AttackPhase(game);
    int mockRowNum = 2;
    int mockColNum = 1;
    String mockInput = mockRowNum + ", " + mockColNum;
    Player humanPlayer = new HumanPlayer();
    Player humanPlayer2 = new HumanPlayer();
    AIPlayer AIPlayer = new AIPlayer();
    @Test
    public void executeAttackPhase_BothPlayersHaveShips_Player1IsHuman_InvokeGetUserInput() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream(mockInput.getBytes());
        System.setIn(in);
        Input[] input = {
                new Input(1, 1),
                new Input(2, 1),
                new Input(3, 1),
                new Input(4, 1),
                new Input(5, 1),
        };

        AttackPhase spyAttackPhase = Mockito.spy(attackPhase);

        Player spyPlayer1 = Mockito.spy(humanPlayer);
        spyPlayer1.bottomBoard = Mockito.spy(humanPlayer.bottomBoard);

        Player spyPlayer2 = Mockito.spy(humanPlayer2);
        spyPlayer2.bottomBoard = Mockito.spy(humanPlayer2.bottomBoard);

        Mockito.when(spyAttackPhase.getUserInput(in)).thenReturn(input[0], input[1], input[2], input[3], input[4], input[0], input[1], input[2], input[3], input[4]);
        Mockito.when(spyPlayer1.allShipsDestroyed()).thenReturn(false, true);
        Mockito.when(spyPlayer2.allShipsDestroyed()).thenReturn(false, false);
        spyAttackPhase.execute(spyPlayer1, spyPlayer2);
        //verify(spyAttackPhase).getUserInput(any());
        System.setIn(sysInBackup);
    }

    @Test
    public void executeAttackPhase_BothPlayersHaveShips_Player2IsComputer_InvokeDetermineAttackCoordinate() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream(mockInput.getBytes());
        System.setIn(in);
        Input[] input = {
                new Input(1, 1),
                new Input(2, 1),
                new Input(3, 1),
                new Input(4, 1),
                new Input(5, 1),
        };

        AttackPhase spyAttackPhase = Mockito.spy(attackPhase);

        Player spyPlayer1 = Mockito.spy(humanPlayer);
        spyPlayer1.bottomBoard = Mockito.spy(humanPlayer.bottomBoard);

        AIPlayer spyPlayer2 = Mockito.spy(AIPlayer);
        spyPlayer2.bottomBoard = Mockito.spy(AIPlayer.bottomBoard);

        Mockito.when(spyAttackPhase.getUserInput(in)).thenReturn(input[0], input[1], input[2], input[3], input[4]);
        Mockito.when(spyPlayer1.allShipsDestroyed()).thenReturn(false, true);
        Mockito.when(spyPlayer2.allShipsDestroyed()).thenReturn(false, false);
        spyAttackPhase.execute(spyPlayer1, spyPlayer2);
        //verify(spyPlayer2).determineAttackCoordinate();
        System.setIn(sysInBackup);
    }

    @Test
    public void executeAttackPhase_NeitherPlayersHaveShips_EndAttackPhase() {
    }
}