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
    @DisplayName("A horizontally placed ship's last coordinate is offset by 0 columns.")
    public void last_column_of_horizontal_ship_is_offset_by_size_zero(){
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
    public void last_column_of_horizontal_ship_is_offset_by_size_minus_one(){
        char mockOrientation = 'h'; //vertical

        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("row", mockRow);
        expected.put("col", mockCol+mockSize-1);

        HashMap[] actualCoordinates = MockBoard.calcShipPlacementCoordinates(mockRow, mockCol, mockSize, mockOrientation);
        HashMap actual = actualCoordinates[mockSize-1];

        assertEquals(expected,actual);
    }
    @Test
    @DisplayName("A vertically placed ship's first coordinate is offset by 0 rows")
    public void last_row_of_vertical_ship_is_offset_by_zero(){
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
    public void last_row_of_vertical_ship_is_offset_by_size_minus_one(){
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
    public void Ship_name_should_be_present_at_first_coordinate(){
        MockBoard.placeShip(MockShip);

        String expected = mockName;
        String actual = MockBoard.grid[(int)mockOccupiedCoordinates[0].get("row")][(int)mockOccupiedCoordinates[0].get("col")];

        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("A ship's last coordinate on the board returns it's name.")
    public void Ship_name_should_be_present_at_last_coordinate(){
        MockBoard.placeShip(MockShip);

        String expected = mockName;
        String actual = MockBoard.grid[(int)mockOccupiedCoordinates[mockSize-1].get("row")][(int)mockOccupiedCoordinates[mockSize-1].get("col")];

        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("A row coordinate less than a ship's minimum doesn't return the ship's name.")
    public void Ship_name_should_not_occupy_below_first_coordinate(){
        MockBoard.placeShip(MockShip);

        String actual = MockBoard.grid[(int)mockOccupiedCoordinates[0].get("row")+1][(int)mockOccupiedCoordinates[0].get("col")];

        assertNull(actual);
    }
    @Test
    @DisplayName("A row coordinate greater than a ship's minimum doesn't return the ship's name.")
    public void Ship_name_should_not_occupy_above_first_coordinate(){
        MockBoard.placeShip(MockShip);

        String actual = MockBoard.grid[(int)mockOccupiedCoordinates[0].get("row")-1][(int)mockOccupiedCoordinates[0].get("col")];

        assertNull(actual);
    }
    @Test
    @DisplayName("A column coordinate greater than a ship's minimum doesn't return the ship's name.")
    public void Ship_should_not_occupy_right_of_last_coordinate(){
        MockBoard.placeShip(MockShip);

        String actual = MockBoard.grid[(int)mockOccupiedCoordinates[4].get("row")][(int)mockOccupiedCoordinates[4].get("col")+1];

        assertNull(actual);
    }

    @Test
    @DisplayName("A column coordinate less than a ship's minimum doesn't return the ship's name.")
    public void Ship_should_not_occupy_left_of_first_coordinate(){
        MockBoard.placeShip(MockShip);

        String actual = MockBoard.grid[(int)mockOccupiedCoordinates[0].get("row")][(int)mockOccupiedCoordinates[0].get("col")-1];

        assertNull(actual);
    }
    @Test
    public void A_row_below_boards_range_is_invalid(){
        int mockCol = -1;
        int mockRow = 1;
        boolean actual = MockBoard.coordOutOfBounds(mockRow, mockCol);
        assertFalse(actual);
    }
    @Test
    public void A_column_below_boards_range_is_invalid(){
        int mockCol = 1;
        int mockRow = -1;
        boolean actual = MockBoard.coordOutOfBounds(mockRow, mockCol);
        assertFalse(actual);
    }
    @Test
    public void A_row_above_boards_range_is_invalid(){
        int maxBoardRow = MockBoard.grid.length;
        int mockCol = 0;
        int mockRow = maxBoardRow+1;
        boolean actual = MockBoard.coordOutOfBounds(mockRow, mockCol);
        assertFalse(actual);
    }
    @Test
    public void A_column_above_boards_range_is_invalid(){
        int maxBoardCol = MockBoard.grid[0].length;
        int mockCol = maxBoardCol+1;
        int mockRow = 0;
        boolean actual = MockBoard.coordOutOfBounds(mockRow, mockCol);
        assertFalse(actual);
    }

    @Test
    public void A_row_inside_boards_range_is_valid(){
        int maxBoardRow = MockBoard.grid.length-1;
        int mockCol = 0;
        int mockRow = (maxBoardRow / 2);
        boolean actual = MockBoard.coordOutOfBounds(mockRow, mockCol);
        assertTrue(actual);
    }
    @Test
    public void A_column_inside_boards_range_is_valid(){
        int maxBoardCol = MockBoard.grid[0].length-1;
        int mockCol = maxBoardCol / 2;
        int mockRow = 0;
        boolean actual = MockBoard.coordOutOfBounds(mockRow, mockCol);
        assertTrue(actual);
    }
}