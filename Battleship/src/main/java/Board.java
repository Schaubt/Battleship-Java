public class Board {
    String[][] grid;
    public Board(){
        this.grid = new String[10][10];
    }
    public void updateCell(int row, int col, String value){
        this.grid[row][col] = value;
    }
    public void display(){
        int numOfRows = this.grid.length;
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
    public void markHitOrMiss(int row, int col){

    }
    public void placeShip(){

    }
}
