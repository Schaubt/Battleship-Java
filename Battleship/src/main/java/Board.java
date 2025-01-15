import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    String[][] grid;
    public Board(){
        this.grid = new String[10][10];
    }
    public void updateCell(int row, int col, String value){
        this.grid[row][col] = value;
    }
    public void display(){
        int numOfCols = this.grid[0].length;
        for (String[] strings : this.grid) {
            for (int j = 0; j < numOfCols; j++) {
                switch (strings[j]) {
                    case null -> System.out.print("O");
                    case "Hit" -> System.out.print("X");
                    case "Miss" -> System.out.print("M");
                    default -> System.out.print("@");
                }
            }
            System.out.println();
        }
    }
    //public void markHitOrMiss(int row, int col){}
    public List<Map<String, Integer>> calcShipPlacementCoordinates(int row, int col, int size, char orientation){
        List<Map<String, Integer>> coordinates = new ArrayList<>();
        int colTemp = col;
        int rowTemp = row;
            for(int i=0;i<size;i++)
            {
                Map<String, Integer> coord = new HashMap<>();
                coord.put("row", rowTemp);
                coord.put("col", colTemp);
                coordinates.add(coord);

                if(orientation == 'h') {
                    colTemp++;
                }
                else if(orientation == 'v')
                {
                    rowTemp++;
                }
            }
            return coordinates;
    }
    public void placeShip(Ship ship){
        int currRow;
        int currCol;
        for(int i =0; i < ship.occupiedCoordinates.size(); i++){
            currRow = ship.occupiedCoordinates.get(i).get("row");
            currCol = ship.occupiedCoordinates.get(i).get("col");
            this.grid[currRow][currCol] = ship.name;
        }
    }
    public boolean coordInBounds(int row, int col){
        if (row < 0 || col <0) {
            return false;
        }
        else if (row>this.grid.length || col>this.grid[0].length){
            return false;
        }
        return true;
    }
    public boolean coordIsAvailable(int row, int col){
        return this.grid[row][col] == null;
    }
    public boolean shipOverflowsOffBoard(List<Map<String, Integer>> coords){
        int row = coords.get(coords.size()-1).get("row");
        int col = coords.get(coords.size()-1).get("col");
        return !coordInBounds(row, col);
    }
}
