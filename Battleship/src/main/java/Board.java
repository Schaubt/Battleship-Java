import java.util.HashMap;

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
    public HashMap[] calcShipPlacementCoordinates(int row, int col, int size, char orientation){
        HashMap[] array = new HashMap[size];
        int colTemp = col;
        int rowTemp = row;
            for(int i=0;i<size;i++)
            {
                HashMap<String, Integer> coord = new HashMap<>();
                coord.put("row", rowTemp);
                coord.put("col", colTemp);
                array[i] = coord;
                if(orientation == 'h') {
                    colTemp++;
                }
                else if(orientation == 'v')
                {
                    rowTemp++;
                }
            }
            return array;
    }
    public void placeShip(Ship ship){
        int currRow;
        int currCol;
        for(int i =0; i < ship.occupiedCoordinates.length; i++){
            currRow = (int) ship.occupiedCoordinates[i].get("row");
            currCol = (int) ship.occupiedCoordinates[i].get("col");
            this.grid[currRow][currCol] = ship.name;
        }
    }
}
