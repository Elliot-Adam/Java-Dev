public class Board {
    private String[][] grid = new String[7][6];

    public String[][] getBoard(){
        return grid;
    }

    private int[] iterColumn(int column){
        int x = column - 1;
        int y = 0;

        while (grid[x][y] != ""){
            if (grid[x][5] != "") {
                return new int[]{column,-1};
            }
            y++;
        }

        return new int[]{x,y};
    }

    public void addPiece(int column, String piece){
        int[] coords = iterColumn(column);
        grid[coords[0]][coords[1]] = piece;
    }

    public int getTopColumn(int column){
        return iterColumn(column)[1];
    }    
}
