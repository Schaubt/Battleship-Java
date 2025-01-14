import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {
    Board Board = new Board();
    String mockName = "Carrier";
    int mockSize = 5;
    int mockRow = 1;
    int mockCol = 1;
    char mockOrientation = 'h';
    HashMap[] mockOccupiedCoordinates = Board.calcShipPlacementCoordinates(mockRow,mockCol,mockSize, mockOrientation);

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
        HashMap[] expected = mockOccupiedCoordinates;
        HashMap[] actual = shipActual.occupiedCoordinates;

        assertEquals(expected, actual);
    }
}