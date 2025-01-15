import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SetUpPhaseTest {
    SetUpPhase setUpPhase = new SetUpPhase();
    int mockRowNum = 2;
    int mockColNum = 1;
    char mockOrientation = 'h';
    String mockInput = mockRowNum + ", " + mockColNum + ", " + mockOrientation;
    @Test
    public void getting_user_input_should_include_row_number(){
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream(mockInput.getBytes());
        System.setIn(in);

        UserInput userInput = setUpPhase.getUserInput(in);
        int expected = mockRowNum;
        int actual = userInput.getRowNum();
        assertEquals(expected,actual);
        System.setIn(sysInBackup);
    }

    @Test
    public void getting_user_input_should_include_col_number(){
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream(mockInput.getBytes());
        System.setIn(in);

        UserInput userInput = setUpPhase.getUserInput(in);
        int expected = mockColNum;
        int actual = userInput.getColNum();
        assertEquals(expected,actual);
        System.setIn(sysInBackup);
    }

    @Test
    public void getting_user_input_should_include_orientation(){
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream(mockInput.getBytes());
        System.setIn(in);

        UserInput userInput = setUpPhase.getUserInput(in);
        int expected = mockOrientation;
        int actual = userInput.getOrientation();
        assertEquals(expected,actual);
        System.setIn(sysInBackup);
    }
    @Test
    public void getting_user_input_should_ignore_consecutive_spaces(){
        String mockInput = mockRowNum + ", " + mockColNum + ",                       " + mockOrientation;
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream(mockInput.getBytes());
        System.setIn(in);

        UserInput userInput = setUpPhase.getUserInput(in);
        int expected = mockOrientation;
        int actual = userInput.getOrientation();
        assertEquals(expected,actual);
        System.setIn(sysInBackup);
    }
}