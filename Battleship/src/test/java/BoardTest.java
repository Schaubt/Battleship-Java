import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BoardTest {
    Board MockBoard = new Board();
    String mockName = "Carrier";
    int mockSize = 5;
    int mockRow = 1;
    int mockCol = 1;
    char mockOrientation = 'h';
    List<Map<String, Integer>> mockOccupiedCoordinates = MockBoard.calcShipPlacementCoordinates(mockRow,mockCol,mockSize, mockOrientation);
    Ship MockShip = new Ship(mockName, mockSize, mockOccupiedCoordinates);
    @BeforeEach
    public void init(){
        MockBoard = new Board();
    }

    @Test
    public void last_column_of_horizontal_ship_is_offset_by_size_zero(){
        char mockOrientation = 'h'; //vertical

        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("row", mockRow);
        expected.put("col", mockCol);

        List<Map<String, Integer>> actualCoordinates = MockBoard.calcShipPlacementCoordinates(mockRow, mockCol, mockSize, mockOrientation);
        Map<String, Integer> actual = actualCoordinates.get(0);

        assertEquals(expected,actual);
    }
    @Test
    public void last_column_of_horizontal_ship_is_offset_by_size_minus_one(){
        char mockOrientation = 'h'; //vertical

        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("row", mockRow);
        expected.put("col", mockCol+mockSize-1);

        List<Map<String, Integer>> actualCoordinates = MockBoard.calcShipPlacementCoordinates(mockRow, mockCol, mockSize, mockOrientation);
        Map<String, Integer> actual = actualCoordinates.get(mockSize-1);

        assertEquals(expected,actual);
    }
    @Test
    public void last_row_of_vertical_ship_is_offset_by_zero(){
        char mockOrientation = 'v'; //vertical

        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("row", mockRow);
        expected.put("col", mockCol);

        List<Map<String, Integer>> actualCoordinates = MockBoard.calcShipPlacementCoordinates(mockRow, mockCol, mockSize, mockOrientation);
        Map<String, Integer> actual = actualCoordinates.get(0);

        assertEquals(expected,actual);
    }
    @Test
    public void last_row_of_vertical_ship_is_offset_by_size_minus_one(){
        char mockOrientation = 'v'; //vertical

        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("row", mockRow+mockSize-1);
        expected.put("col", mockCol);

        List<Map<String, Integer>> actualCoordinates = MockBoard.calcShipPlacementCoordinates(mockRow, mockCol, mockSize, mockOrientation);
        Map<String, Integer> actual = actualCoordinates.get(mockSize-1);

        assertEquals(expected,actual);
    }
    @Test
    public void Ship_name_should_be_present_at_first_coordinate(){
        MockBoard.placeShip(MockShip);

        String expected = mockName;
        String actual = MockBoard.grid[mockOccupiedCoordinates.get(0).get("row")][mockOccupiedCoordinates.get(0).get("col")];

        assertEquals(expected, actual);
    }
    @Test
    public void Ship_name_should_be_present_at_last_coordinate(){
        MockBoard.placeShip(MockShip);

        String expected = mockName;
        String actual = MockBoard.grid[mockOccupiedCoordinates.get(mockSize-1).get("row")][mockOccupiedCoordinates.get(mockSize-1).get("col")];

        assertEquals(expected, actual);
    }
    @Test
    public void Ship_name_should_not_occupy_below_first_coordinate(){
        MockBoard.placeShip(MockShip);

        String actual = MockBoard.grid[mockOccupiedCoordinates.get(0).get("row")+1][mockOccupiedCoordinates.get(0).get("col")];

        assertNull(actual);
    }
    @Test
    public void Ship_name_should_not_occupy_above_first_coordinate(){
        MockBoard.placeShip(MockShip);

        String actual = MockBoard.grid[mockOccupiedCoordinates.get(0).get("row")-1][mockOccupiedCoordinates.get(0).get("col")];

        assertNull(actual);
    }
    @Test
    public void Ship_should_not_occupy_right_of_last_coordinate(){
        MockBoard.placeShip(MockShip);

        String actual = MockBoard.grid[mockOccupiedCoordinates.get(mockSize-1).get("row")][mockOccupiedCoordinates.get(mockSize-1).get("col")+1];

        assertNull(actual);
    }

    @Test
    public void Ship_should_not_occupy_left_of_first_coordinate(){
        MockBoard.placeShip(MockShip);

        String actual = MockBoard.grid[mockOccupiedCoordinates.get(0).get("row")][mockOccupiedCoordinates.get(0).get("col")-1];

        assertNull(actual);
    }
    @Test
    public void A_row_below_boards_range_is_invalid(){
        int mockCol = -1;
        int mockRow = 1;
        boolean actual = MockBoard.coordInBounds(mockRow, mockCol);
        assertFalse(actual);
    }
    @Test
    public void A_column_below_boards_range_is_invalid(){
        int mockCol = 1;
        int mockRow = -1;
        boolean actual = MockBoard.coordInBounds(mockRow, mockCol);
        assertFalse(actual);
    }
    @Test
    public void A_row_above_boards_range_is_invalid(){
        int maxBoardRow = MockBoard.grid.length;
        int mockCol = 0;
        int mockRow = maxBoardRow+1;
        boolean actual = MockBoard.coordInBounds(mockRow, mockCol);
        assertFalse(actual);
    }
    @Test
    public void A_column_above_boards_range_is_invalid(){
        int maxBoardCol = MockBoard.grid[0].length;
        int mockCol = maxBoardCol+1;
        int mockRow = 0;
        boolean actual = MockBoard.coordInBounds(mockRow, mockCol);
        assertFalse(actual);
    }

    @Test
    public void A_row_inside_boards_range_is_valid(){
        int maxBoardRow = MockBoard.grid.length-1;
        int mockCol = 0;
        int mockRow = (maxBoardRow / 2);
        boolean actual = MockBoard.coordInBounds(mockRow, mockCol);
        assertTrue(actual);
    }
    @Test
    public void A_column_inside_boards_range_is_valid(){
        int maxBoardCol = MockBoard.grid[0].length-1;
        int mockCol = maxBoardCol / 2;
        int mockRow = 0;
        boolean actual = MockBoard.coordInBounds(mockRow, mockCol);
        assertTrue(actual);
    }
    @Test
    public void A_coordinate_occupied_by_a_ship_is_unavailable(){
        MockBoard.grid[mockRow][mockCol] = "Patrol Boat";
        boolean actual = MockBoard.coordIsAvailable(mockRow,mockCol);
        assertFalse(actual);
    }
    @Test
    public void A_coordinate_not_occupied_by_a_ship_is_available(){
        boolean actual = MockBoard.coordIsAvailable(mockRow,mockCol);
        assertTrue(actual);
    }
    @Test
    public void Ship_overflows_off_board_returns_true(){
        int mockRow = MockBoard.grid[0].length;
        int mockCol = MockBoard.grid.length;
        List<Map<String, Integer>> mockCoordinates = MockBoard.calcShipPlacementCoordinates(mockRow,mockCol,mockSize, mockOrientation);
        boolean actual = MockBoard.shipOverflowsOffBoard(mockCoordinates);
        assertTrue(actual);
    }
    @Test
    public void Ship_does_not_overflow_off_board_returns_false(){
        int mockRow = 0;
        int mockCol = 0;
        List<Map<String, Integer>> mockCoordinates = MockBoard.calcShipPlacementCoordinates(mockRow,mockCol,mockSize, mockOrientation);
        boolean actual = MockBoard.shipOverflowsOffBoard(mockCoordinates);
        assertFalse(actual);
    }
    @Test
    public void display_grid_should_contain_ship_symbol_at_first_ship_coordinate(){
        char expected = '@';
        MockBoard.placeShip(MockShip);
        MockBoard.generateDisplayGrid(); // function in question
        char actual = MockBoard.displayGrid[MockShip.occupiedCoordinates.get(0).get("row")][MockShip.occupiedCoordinates.get(0).get("col")];
        assertEquals(expected, actual);
    }
    @Test
    public void display_grid_should_contain_ship_symbol_at_last_ship_coordinate(){
        char expected = '@';
        MockBoard.placeShip(MockShip);
        MockBoard.generateDisplayGrid(); //function in question
        char actual = MockBoard.displayGrid[MockShip.occupiedCoordinates.get(mockSize-1).get("row")][MockShip.occupiedCoordinates.get(mockSize-1).get("col")];
        assertEquals(expected, actual);
    }
}