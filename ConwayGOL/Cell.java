package ConwayGOL;

import java.util.ArrayList;

public class Cell{
    boolean alive;
    int x;
    int y;
    
    /*
    Any live cell with fewer than two live neighbours dies (referred to as underpopulation).
    Any live cell with more than three live neighbours dies (referred to as overpopulation).
    Any live cell with two or three live neighbours lives, unchanged, to the next generation.
    Any dead cell with exactly three live neighbours comes to life.
    */
    
    public Cell(int x, int y){
       this.x = x;
       this.y = y;
       alive = false;
    }

    public ArrayList<Cell> getNeighbors(Cell[][] board){
        ArrayList<Cell> neighbors = new ArrayList<Cell>(8);
        int index = 0;
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                try {
                    neighbors.set(index, board[x + i][y + j]);
                    index += 1; 
                } catch (ArrayIndexOutOfBoundsException exception){
                    neighbors.set(index, new Cell(-1,-1));
                }
            }
        }
        return neighbors;
    }


    /**
     * @param board = board of all cells
     * @return true if alive and false if dead
     */
    public boolean checkStatus(Cell[][] board){
        ArrayList<Cell> neighbors = getNeighbors(board);
        int neighborCount = 0;
        for (Cell neighbor : neighbors){
            if (neighbor.x != -1){
                neighborCount += 1;
            }
        }
        
        //Returns boolean based on neighbors and rules 
        return !(neighborCount < 2 || neighborCount > 3);
    }
}
