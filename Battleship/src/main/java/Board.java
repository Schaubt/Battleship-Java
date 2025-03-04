import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    String[][] grid;
    char[][] displayGrid;
    public Board(){
        this.grid = new String[10][10];
        this.displayGrid = new char[10][10];
    }
    public void updateCell(int row, int col, String value){
        this.grid[row][col] = value;
    }
    public void updateDisplayCell(int row, int col, char value){
        this.displayGrid[row][col] = value;
    }
    public void generateDisplayGrid() {
        int numOfCols = this.grid[0].length;
        int numOfRows = this.grid.length;
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                switch (this.grid[i][j]) {
                    case null -> this.displayGrid[i][j] = 'O';
                    case "Hit" -> this.displayGrid[i][j] = 'X';
                    case "Miss" -> this.displayGrid[i][j] = 'M';
                    default -> this.displayGrid[i][j] = '@';
                }
            }
        }
    }
    public void printDisplayGrid(){
        int numOfCols = this.grid[0].length;
        for(int i=0; i < this.displayGrid.length; i++){
            for (int j = 0; j < numOfCols; j++) {
                System.out.print(this.displayGrid[i][j] + " ");
            }
            System.out.println();
        }
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
        for(int i =0; i < ship.occupiedCoordinates.size(); i++){
            int currRow = ship.occupiedCoordinates.get(i).get("row");
            int currCol = ship.occupiedCoordinates.get(i).get("col");
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

    public boolean validateShipPlacement(List<Map<String, Integer>> coords){
        if(shipOverflowsOffBoard(coords)) {
            System.out.println("Error: Placing a ship here causes it to overflow off the board.");
            return false;
        }
        for(int i=0; i < coords.size()-1; i++){
            if(!coordIsAvailable(coords.get(i).get("row"), coords.get(i).get("col"))){
                System.out.println("Error: Placing a ship here overlaps another.");
                return false;
            }
        }
        return true;
    }
}
