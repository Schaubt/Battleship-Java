import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    @Test
    public void testDisplay() {
        Board board = new Board();
        board.updateCell(0, 0, "Hit");
        board.updateCell(0, 1, "Miss");
        board.updateCell(9, 9, "Ship");

        // Expected display output
        String expectedOutput = """
        XMOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOO@
        """;

        // Act: Capture the output of the display() method
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        board.display();
        System.setOut(System.out); // Reset standard output
        String normalizedExpected = expectedOutput.replace("\r\n", "\n").stripTrailing();
        String normalizedActual = outputStream.toString().replace("\r\n", "\n").stripTrailing();

        // Assert: Check if the captured output matches the expected output
        assertEquals(normalizedExpected, normalizedActual);
    }
    @Test
    public void testEmptyDisplay() {
        Board board = new Board();

        // Expected display output
        String expectedOutput = """
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        OOOOOOOOOO
        """;

        // Act: Capture the output of the display() method
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        board.display();
        System.setOut(System.out); // Reset standard output
        String normalizedExpected = expectedOutput.replace("\r\n", "\n").stripTrailing();
        String normalizedActual = outputStream.toString().replace("\r\n", "\n").stripTrailing();

        // Assert: Check if the captured output matches the expected output
        assertEquals(normalizedExpected, normalizedActual);
    }

    @Test
    public void testCalcShipPlacementCoordinatesHorizontal(){
        Board board = new Board();
        int mockRow = 5;
        int mockCol = 2;
        int mockSize = 3;
        char mockOrientation = 'h'; //horizontal

        //expected
        HashMap[] expected = new HashMap[2];
        HashMap<String, Integer> mockMap1 = new HashMap<>();
        mockMap1.put("row", mockRow);
        mockMap1.put("col", mockCol);
        expected[0] = mockMap1;

        HashMap<String, Integer> mockMap2 = new HashMap<>();
        mockMap2.put("row", mockRow);
        mockMap2.put("col", mockCol+mockSize-1);
        expected[1] = mockMap2;

        //actual
        HashMap[] actual = board.calcShipPlacementCoordinates(mockRow, mockCol, mockSize, mockOrientation);

        //test first coordinate in array
        assertEquals(expected[0].get("row"),actual[0].get("row"));
        assertEquals(expected[0].get("col"),actual[0].get("col"));

        //test last coordinate in array
        assertEquals(expected[1].get("row"),actual[mockSize-1].get("row"));
        assertEquals(expected[1].get("col"),actual[mockSize-1].get("col"));
    }
    @Test
    public void testCalcShipPlacementCoordinatesVertical(){
        Board board = new Board();
        int mockRow = 5;
        int mockCol = 2;
        int mockSize = 3;
        char mockOrientation = 'v'; //vertical

        //expected
        HashMap[] expected = new HashMap[2];
        HashMap<String, Integer> mockMap1 = new HashMap<>();
        mockMap1.put("row", mockRow);
        mockMap1.put("col", mockCol);
        expected[0] = mockMap1;

        HashMap<String, Integer> mockMap2 = new HashMap<>();
        mockMap2.put("row", mockRow+mockSize-1);
        mockMap2.put("col", mockCol);
        expected[1] = mockMap2;

        //actual
        HashMap[] actual = board.calcShipPlacementCoordinates(mockRow, mockCol, mockSize, mockOrientation);

        //test first coordinate in array
        assertEquals(expected[0].get("row"),actual[0].get("row"));
        assertEquals(expected[0].get("col"),actual[0].get("col"));

        //test last coordinate in array
        assertEquals(expected[1].get("row"),actual[mockSize-1].get("row"));
        assertEquals(expected[1].get("col"),actual[mockSize-1].get("col"));
    }
}