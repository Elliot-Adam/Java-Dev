public class Board {
    private String[][] grid = new String[7][6];

    public String[][] getBoard(){
        return grid;
    }

    public void addPiece(int column, String piece){
        int x = column - 1;
        int y = 0;

        while (grid[x][y] != ""){
            if (y > 6) {
                return;
            }
            y++;
        }

        grid[x][y] = piece;
    }

    
}
