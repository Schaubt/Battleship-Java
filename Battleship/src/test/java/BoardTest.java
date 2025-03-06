import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BoardTest {
    Board mockBoard = new Board();
    String mockName = "Carrier";
    int mockSize = 5;
    int mockRow = 1;
    int mockCol = 1;
    char mockOrientation = 'h';
    List<Map<String, Integer>> mockOccupiedCoordinates = mockBoard.calcShipPlacementCoordinates(mockRow,mockCol,mockSize, mockOrientation);
    Ship mockShip = new Ship(mockName, mockSize, mockOccupiedCoordinates);
    char shipSymbol = '@';
    @BeforeEach
    public void init(){
        mockBoard = new Board();
    }

    @Test
    public void last_column_of_horizontal_ship_is_offset_by_size_zero(){
        char mockOrientation = 'h'; //vertical

        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("row", mockRow);
        expected.put("col", mockCol);

        List<Map<String, Integer>> actualCoordinates = mockBoard.calcShipPlacementCoordinates(mockRow, mockCol, mockSize, mockOrientation);
        Map<String, Integer> actual = actualCoordinates.get(0);

        assertEquals(expected,actual);
    }
    @Test
    public void last_column_of_horizontal_ship_is_offset_by_size_minus_one(){
        char mockOrientation = 'h'; //vertical

        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("row", mockRow);
        expected.put("col", mockCol+mockSize-1);

        List<Map<String, Integer>> actualCoordinates = mockBoard.calcShipPlacementCoordinates(mockRow, mockCol, mockSize, mockOrientation);
        Map<String, Integer> actual = actualCoordinates.get(mockSize-1);

        assertEquals(expected,actual);
    }
    @Test
    public void last_row_of_vertical_ship_is_offset_by_zero(){
        char mockOrientation = 'v'; //vertical

        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("row", mockRow);
        expected.put("col", mockCol);

        List<Map<String, Integer>> actualCoordinates = mockBoard.calcShipPlacementCoordinates(mockRow, mockCol, mockSize, mockOrientation);
        Map<String, Integer> actual = actualCoordinates.get(0);

        assertEquals(expected,actual);
    }
    @Test
    public void last_row_of_vertical_ship_is_offset_by_size_minus_one(){
        char mockOrientation = 'v'; //vertical

        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("row", mockRow+mockSize-1);
        expected.put("col", mockCol);

        List<Map<String, Integer>> actualCoordinates = mockBoard.calcShipPlacementCoordinates(mockRow, mockCol, mockSize, mockOrientation);
        Map<String, Integer> actual = actualCoordinates.get(mockSize-1);

        assertEquals(expected,actual);
    }
    @Test
    public void Ship_name_should_be_present_at_first_coordinate(){
        mockBoard.placeShip(mockShip);

        String expected = mockName;
        String actual = mockBoard.grid[mockOccupiedCoordinates.get(0).get("row")][mockOccupiedCoordinates.get(0).get("col")];

        assertEquals(expected, actual);
    }
    @Test
    public void Ship_name_should_be_present_at_last_coordinate(){
        mockBoard.placeShip(mockShip);

        String expected = mockName;
        String actual = mockBoard.grid[mockOccupiedCoordinates.get(mockSize-1).get("row")][mockOccupiedCoordinates.get(mockSize-1).get("col")];

        assertEquals(expected, actual);
    }
    @Test
    public void Ship_name_should_not_occupy_below_first_coordinate(){
        mockBoard.placeShip(mockShip);

        String actual = mockBoard.grid[mockOccupiedCoordinates.get(0).get("row")+1][mockOccupiedCoordinates.get(0).get("col")];

        assertNull(actual);
    }
    @Test
    public void Ship_name_should_not_occupy_above_first_coordinate(){
        mockBoard.placeShip(mockShip);

        String actual = mockBoard.grid[mockOccupiedCoordinates.get(0).get("row")-1][mockOccupiedCoordinates.get(0).get("col")];

        assertNull(actual);
    }
    @Test
    public void Ship_should_not_occupy_right_of_last_coordinate(){
        mockBoard.placeShip(mockShip);

        String actual = mockBoard.grid[mockOccupiedCoordinates.get(mockSize-1).get("row")][mockOccupiedCoordinates.get(mockSize-1).get("col")+1];

        assertNull(actual);
    }

    @Test
    public void Ship_should_not_occupy_left_of_first_coordinate(){
        mockBoard.placeShip(mockShip);

        String actual = mockBoard.grid[mockOccupiedCoordinates.get(0).get("row")][mockOccupiedCoordinates.get(0).get("col")-1];

        assertNull(actual);
    }
    @Test
    public void A_row_below_boards_range_is_invalid(){
        int mockCol = -1;
        int mockRow = 1;
        boolean actual = mockBoard.coordInBounds(mockRow, mockCol);
        assertFalse(actual);
    }
    @Test
    public void A_column_below_boards_range_is_invalid(){
        int mockCol = 1;
        int mockRow = -1;
        boolean actual = mockBoard.coordInBounds(mockRow, mockCol);
        assertFalse(actual);
    }
    @Test
    public void A_row_above_boards_range_is_invalid(){
        int maxBoardRow = mockBoard.grid.length;
        int mockCol = 0;
        int mockRow = maxBoardRow+1;
        boolean actual = mockBoard.coordInBounds(mockRow, mockCol);
        assertFalse(actual);
    }
    @Test
    public void A_column_above_boards_range_is_invalid(){
        int maxBoardCol = mockBoard.grid[0].length;
        int mockCol = maxBoardCol+1;
        int mockRow = 0;
        boolean actual = mockBoard.coordInBounds(mockRow, mockCol);
        assertFalse(actual);
    }

    @Test
    public void A_row_inside_boards_range_is_valid(){
        int maxBoardRow = mockBoard.grid.length-1;
        int mockCol = 1;
        int mockRow = (maxBoardRow / 2);
        boolean actual = mockBoard.coordInBounds(mockRow, mockCol);
        assertTrue(actual);
    }
    @Test
    public void A_column_inside_boards_range_is_valid(){
        int maxBoardCol = mockBoard.grid[0].length-1;
        int mockCol = maxBoardCol / 2;
        int mockRow = 1;
        boolean actual = mockBoard.coordInBounds(mockRow, mockCol);
        assertTrue(actual);
    }
    @Test
    public void A_coordinate_occupied_by_a_ship_is_unavailable(){
        mockBoard.grid[mockRow][mockCol] = "Patrol Boat";
        boolean actual = mockBoard.coordIsAvailable(mockRow,mockCol);
        assertFalse(actual);
    }
    @Test
    public void A_coordinate_not_occupied_by_a_ship_is_available(){
        boolean actual = mockBoard.coordIsAvailable(mockRow,mockCol);
        assertTrue(actual);
    }
    @Test
    public void Ship_overflows_off_board_returns_true(){
        int mockRow = mockBoard.grid[0].length;
        int mockCol = mockBoard.grid.length;
        List<Map<String, Integer>> mockCoordinates = mockBoard.calcShipPlacementCoordinates(mockRow,mockCol,mockSize, mockOrientation);
        boolean actual = mockBoard.shipOverflowsOffBoard(mockCoordinates);
        assertTrue(actual);
    }
    @Test
    public void Ship_does_not_overflow_off_board_returns_false(){
        int mockRow = 1;
        int mockCol = 1;
        List<Map<String, Integer>> mockCoordinates = mockBoard.calcShipPlacementCoordinates(mockRow,mockCol,mockSize, mockOrientation);
        boolean actual = mockBoard.shipOverflowsOffBoard(mockCoordinates);
        assertFalse(actual);
    }
    @Test
    public void display_grid_should_contain_ship_symbol_at_first_ship_coordinate(){
        char expected = shipSymbol;
        mockBoard.placeShip(mockShip);
        mockBoard.generateDisplayGrid(); // function in question
        char actual = mockBoard.displayGrid[mockShip.occupiedCoordinates.get(0).get("row")][mockShip.occupiedCoordinates.get(0).get("col")];
        assertEquals(expected, actual);
    }
    @Test
    public void display_grid_should_contain_ship_symbol_at_last_ship_coordinate(){
        char expected = shipSymbol;
        mockBoard.placeShip(mockShip);
        mockBoard.generateDisplayGrid(); //function in question
        char actual = mockBoard.displayGrid[mockShip.occupiedCoordinates.get(mockSize-1).get("row")][mockShip.occupiedCoordinates.get(mockSize-1).get("col")];
        assertEquals(expected, actual);
    }
    @Test
    public void ValidateShipPlacement_ShipOverlap_ReturnsFalse(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        int mockRow1 = 1;
        int mockCol1 = 2;
        char mockOrientation1 = 'v';
        int mockSize1 = 3;
        List<Map<String, Integer>> mockOccupiedCoordinates1 = mockBoard.calcShipPlacementCoordinates(mockRow1,mockCol1,mockSize1, mockOrientation1);
        Ship mockShip1 = new Ship("Submarine", mockSize1, mockOccupiedCoordinates1);

        //ship 2 will overlap ship 1
        int mockRow2 = 3;
        int mockCol2 = 1;
        char mockOrientation2 = 'h';
        int mockSize2 = 5;
        List<Map<String, Integer>> mockOccupiedCoordinates2 = mockBoard.calcShipPlacementCoordinates(mockRow2,mockCol2,mockSize2, mockOrientation2);
        Ship mockShip2 = new Ship("Carrier", mockSize2, mockOccupiedCoordinates2);

        mockBoard.placeShip(mockShip1);
        mockBoard.placeShip(mockShip2);
        boolean actualBoolean = mockBoard.validateShipPlacement(mockShip2.occupiedCoordinates);
        System.setOut(System.out);
        assertEquals("Error: Placing a ship here overlaps another.", outputStream.toString().stripTrailing());
        assertFalse(actualBoolean);
    }
    @Test
    public void ValidateShipPlacement_ShipOverflowsOffBoard_ReturnsFalse(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        // ship will overflow off board with these coordinates
        int mockRow = 3;
        int mockCol = 7;
        char mockOrientation = 'h';
        int mockSize = 5;
        List<Map<String, Integer>> mockOccupiedCoordinates = mockBoard.calcShipPlacementCoordinates(mockRow,mockCol,mockSize, mockOrientation);
        Ship mockShip = new Ship(mockName, mockSize, mockOccupiedCoordinates);
        boolean actualBoolean = mockBoard.validateShipPlacement(mockShip.occupiedCoordinates);
        System.setOut(System.out);
        assertEquals("Error: Placing a ship here causes it to overflow off the board.", outputStream.toString().stripTrailing());
        assertFalse(actualBoolean);
    }
    @Test
    public void ValidateShipPlacement_ShipPlacementValid_ReturnsTrue(){
        int mockRow = 1;
        int mockCol = 1;
        char mockOrientation = 'h';
        int mockSize = 5;
        List<Map<String, Integer>> mockOccupiedCoordinates = mockBoard.calcShipPlacementCoordinates(mockRow,mockCol,mockSize, mockOrientation);
        Ship mockShip = new Ship(mockName, mockSize, mockOccupiedCoordinates);
        boolean actualBoolean = mockBoard.validateShipPlacement(mockShip.occupiedCoordinates);
        assertTrue(actualBoolean);

    }
}