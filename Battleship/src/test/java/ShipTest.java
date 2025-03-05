import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {
    Board Board = new Board();
    String mockName = "Carrier";
    int mockSize = 5;
    int mockRow = 1;
    int mockCol = 1;
    char mockOrientation = 'h';
    List<Map<String, Integer>> mockOccupiedCoordinates = Board.calcShipPlacementCoordinates(mockRow,mockCol,mockSize, mockOrientation);

    @Test
    public void testShipConstructorName(){
        Ship shipActual = new Ship(mockName, mockSize, mockOccupiedCoordinates);
        String expected = mockName;
        String actual = shipActual.name;

        assertEquals(expected, actual);
    }
    @Test
    public void testShipConstructorSize(){
        Ship shipActual = new Ship(mockName, mockSize, mockOccupiedCoordinates);
        int expected = mockSize;
        int actual = shipActual.size;

        assertEquals(expected, actual);
    }
    @Test
    public void testShipConstructorCoordinates(){
        Ship shipActual = new Ship(mockName, mockSize, mockOccupiedCoordinates);
        List<Map<String, Integer>> expected = mockOccupiedCoordinates;
        List<Map<String, Integer>> actual = shipActual.occupiedCoordinates;

        assertEquals(expected, actual);
    }

    @Test
    public void setCoordinates_SetOccupiedCoordinatesAsIntended(){
        Ship mockShip = new Ship(mockName, mockSize);
        mockShip.setCoordinates(mockOccupiedCoordinates);
        List<Map<String, Integer>> expected = mockOccupiedCoordinates;
        List<Map<String, Integer>> actual = mockShip.occupiedCoordinates;
        assertEquals(expected, actual);
    }

}