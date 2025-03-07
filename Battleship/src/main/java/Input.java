public class Input {
    private int rowNum;
    private int colNum;
    private char orientation;
    public Input(int rowNum, int colNum, char orientation){
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.orientation = orientation;
    }
    public Input(int rowNum, int colNum) {
        this.rowNum = rowNum;
        this.colNum = colNum;
    }
    public int getRowNum(){
        return this.rowNum;
    }
    public int getColNum(){
        return this.colNum;
    }
    public char getOrientation(){
        return this.orientation;
    }
}
