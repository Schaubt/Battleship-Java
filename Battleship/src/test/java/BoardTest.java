import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board MockBoard = new Board();
    String mockName = "Carrier";
    int mockSize = 5;
    int mockRow = 1;
    int mockCol = 1;
    char mockOrientation = 'h';
    HashMap[] mockOccupiedCoordinates = MockBoard.calcShipPlacementCoordinates(mockRow,mockCol,mockSize, mockOrientation);
    Ship MockShip = new Ship(mockName, mockSize, mockOccupiedCoordinates);
    @BeforeEach
    public void init(){
        MockBoard = new Board();
    }
    @Test
    public void testDisplay() {
        MockBoard.updateCell(0, 0, "Hit");
        MockBoard.updateCell(0, 1, "Miss");
        MockBoard.updateCell(9, 9, "Ship");

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
        MockBoard.display();
        System.setOut(System.out); // Reset standard output
        String normalizedExpected = expectedOutput.replace("\r\n", "\n").stripTrailing();
        String normalizedActual = outputStream.toString().replace("\r\n", "\n").stripTrailing();

        // Assert: Check if the captured output matches the expected output
        assertEquals(normalizedExpected, normalizedActual);
    }
    @Test
    public void testEmptyDisplay() {

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
        MockBoard.display();
        System.setOut(System.out); // Reset standard output
        String normalizedExpected = expectedOutput.replace("\r\n", "\n").stripTrailing();
        String normalizedActual = outputStream.toString().replace("\r\n", "\n").stripTrailing();

        // Assert: Check if the captured output matches the expected output
        assertEquals(normalizedExpected, normalizedActual);
    }

    @Test
    @DisplayName("A horizontally placed ship's last coordinate is offset by 0 columns.")
    public void testCalcShipPlacementHorizontalFirstCoordinate(){
        char mockOrientation = 'h'; //vertical

        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("row", mockRow);
        expected.put("col", mockCol);

        HashMap[] actualCoordinates = MockBoard.calcShipPlacementCoordinates(mockRow, mockCol, mockSize, mockOrientation);
        HashMap actual = actualCoordinates[0];

        assertEquals(expected,actual);
    }
    @Test
    @DisplayName("A horizontally placed ship's last coordinate is offset by size-1 columns from it's first.")
    public void testCalcShipPlacementHorizontalLastCoordinate(){
        char mockOrientation = 'h'; //vertical

        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("row", mockRow);
        expected.put("col", mockCol+mockSize-1);

        HashMap[] actualCoordinates = MockBoard.calcShipPlacementCoordinates(mockRow, mockCol, mockSize, mockOrientation);
        HashMap actual = actualCoordinates[mockSize-1];

        assertEquals(expected,actual);
    }
    @Test
    @DisplayName("A vertically placed ship's last coordinate is offset by 0 rows")
    public void testCalcShipPlacementVerticalFirstCoordinate(){
        char mockOrientation = 'v'; //vertical

        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("row", mockRow);
        expected.put("col", mockCol);

        HashMap[] actualCoordinates = MockBoard.calcShipPlacementCoordinates(mockRow, mockCol, mockSize, mockOrientation);
        HashMap actual = actualCoordinates[0];

        assertEquals(expected,actual);
    }
    @Test
    @DisplayName("A vertically placed ship's last coordinate is offset by size-1 rows from its first.")
    public void testCalcShipPlacementVerticalLastCoordinate(){
        char mockOrientation = 'v'; //vertical

        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("row", mockRow+mockSize-1);
        expected.put("col", mockCol);

        HashMap[] actualCoordinates = MockBoard.calcShipPlacementCoordinates(mockRow, mockCol, mockSize, mockOrientation);
        HashMap actual = actualCoordinates[mockSize-1];

        assertEquals(expected,actual);
    }
    @Test
    @DisplayName("A ship's first coordinate on the board returns it's name.")
    public void testPlaceShipFirstIndex(){
        MockBoard.placeShip(MockShip);

        String expected = mockName;
        String actual = MockBoard.grid[(int)mockOccupiedCoordinates[0].get("row")][(int)mockOccupiedCoordinates[0].get("col")];

        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("A ship's last coordinate on the board returns it's name.")
    public void testPlaceShipLastIndex(){
        MockBoard.placeShip(MockShip);

        String expected = mockName;
        String actual = MockBoard.grid[(int)mockOccupiedCoordinates[mockSize-1].get("row")][(int)mockOccupiedCoordinates[mockSize-1].get("col")];

        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Coordinate directly below a ship's row range returns null.")
    public void testPlaceShipOutOfBoundsBelowRow(){
        MockBoard.placeShip(MockShip);

        String actual = MockBoard.grid[(int)mockOccupiedCoordinates[0].get("row")+1][(int)mockOccupiedCoordinates[0].get("col")];

        assertNull(actual);
    }
    @Test
    @DisplayName("Coordinate directly above a ship's row range returns null.")
    public void testPlaceShipOutOfBoundsAboveRow(){
        MockBoard.placeShip(MockShip);

        String actual = MockBoard.grid[(int)mockOccupiedCoordinates[0].get("row")-1][(int)mockOccupiedCoordinates[0].get("col")];

        assertNull(actual);
    }
    @Test
    @DisplayName("Coordinate directly to the right of a ship's column range returns null.")
    public void testPlaceShipOutOfBoundsRightColumn(){
        MockBoard.placeShip(MockShip);

        String actual = MockBoard.grid[(int)mockOccupiedCoordinates[4].get("row")][(int)mockOccupiedCoordinates[4].get("col")+1];

        assertNull(actual);
    }

    @Test
    @DisplayName("Coordinate directly to the left of a ship's column range returns null.")
    public void testPlaceShipOutOfBoundsLeftColumn(){
        MockBoard.placeShip(MockShip);

        String actual = MockBoard.grid[(int)mockOccupiedCoordinates[0].get("row")][(int)mockOccupiedCoordinates[0].get("col")-1];

        assertNull(actual);
    }
}