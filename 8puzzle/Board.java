import java.util.ArrayList;
import java.util.Iterator;

import edu.princeton.cs.algs4.In;

public class Board {
    private int a[][];
    private int dimesnion;
    
    public Board(int[][] blocks) {          
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
        if(blocks == null){
            throw new NullPointerException("block is null.");
        }
        this.dimesnion = blocks.length;
        //a = new int[dimesnion][dimesnion];
        a = blocks;
    }
    
    public int dimension() {                // board dimension n
        return this.dimesnion;
    }
    
    public int hamming()  {                 // number of blocks out of place
        int count = 0;
        for(int i = 0; i < dimesnion; ++i){
            for(int j = 0; j < dimesnion; ++j){
                if((a[i][j] != (i)*dimesnion + j +1) &&!(i == dimesnion -1 && j == dimesnion -1)){
                    count++;
                }
            }
        }
        return count;
    }
    
    public int manhattan() {                // sum of Manhattan distances between blocks and goal
        int count = 0;
        for(int i = 0; i < dimesnion; ++i){
            for(int j = 0; j < dimesnion; ++j){
                if(a[i][j] == 0){
                    continue;
                }else{
                    int row = a[i][j]/dimesnion;
                    int col = 0;
                    if(a[i][j]%dimesnion == 0){
                        col = dimesnion - 1;
                        row = row -1;
                    }else{
                        col = a[i][j]%dimesnion - 1;
                    }
                    if(!(row == i && col == j)){
                        count += row > i ? row - i : i - row;
                        count += col > j ? col - j : j - col;
                    }
                }
            }
        }
        return count;
    }
    
    public boolean isGoal() {               // is this board the goal board?
        for(int i = 0; i < dimesnion; ++i){
            for(int j = 0; j < dimesnion; ++j){
                if(i == dimesnion - 1 && j == dimesnion -1){
                    if(a[i][j] != 0){
                        return false;
                    }
                }else{
                    if(a[i][j] != i*dimesnion + j +1){
                        return false;
                    }
                }   
            }
        }
        return true;
    }
    
    public Board twin()   {                 // a board that is obtained by exchanging any pair of blocks
        int tmp[][] = new int[dimesnion][dimesnion];
        for(int i = 0; i < dimesnion; ++i){
            for(int j = 0; j < dimesnion; ++j){
                tmp[i][j] = a[i][j];
            }
        }
        if(tmp[0][0] != 0){
            if(tmp[0][1] != 0){
                int bak = tmp[0][1];
                tmp[0][1] = tmp[0][0];
                tmp[0][0] = bak;
            }else{
                int bak = tmp[1][0];
                tmp[1][0] = tmp[0][0];
                tmp[0][0] = bak;
            }
        }else{
            int bak = tmp[0][1];
            tmp[0][1] = tmp[1][0];
            tmp[1][0] = bak;
        }
        return new Board(tmp);
    }
    
    public boolean equals(Object y)  {      // does this board equal y?
        if(y == null){
            return false;
        }
        if(!(y instanceof Board || y instanceof String)){
            throw new IllegalArgumentException("object is illegal.");
        }
        if(y instanceof Board){
            Board that = (Board)y;
            if(dimesnion != that.dimension()){
                return false;
            }
            String tmpString = toString();
            return tmpString.equals(that.toString());
        }else{
            return toString().equals(y);
        }
    }
    
    private class BoardNeighbors implements Iterable<Board>{
       
        @Override
        public Iterator<Board> iterator() {
            // TODO Auto-generated method stub
            return genNeigh();
        }
        
        private Iterator<Board> genNeigh(){
            ArrayList<Board> neighorList = new ArrayList<>();
            int blankrow = 0;
            int blankcol = 0;
            for(int i = 0; i < dimesnion; ++i){
                for(int j = 0; j < dimesnion; ++j){
                    if(a[i][j] == 0){
                        blankrow = i;
                        blankcol = j;
                    }
                }
            }
            if(blankrow > 0){
                int tmp[][] = new int[dimesnion][dimesnion];
                for(int i = 0; i < dimesnion; ++i){
                    for(int j = 0; j < dimesnion; ++j){
                        tmp[i][j] = a[i][j];
                    }
                }
                tmp[blankrow][blankcol] = tmp[blankrow-1][blankcol];
                tmp[blankrow-1][blankcol] = 0;
                Board upRow = new Board(tmp);
                neighorList.add(upRow);
            }
            
            if(blankrow + 1 < dimesnion){
                int tmp[][] = new int[dimesnion][dimesnion];
                for(int i = 0; i < dimesnion; ++i){
                    for(int j = 0; j < dimesnion; ++j){
                        tmp[i][j] = a[i][j];
                    }
                }
                tmp[blankrow][blankcol] = tmp[blankrow+1][blankcol];
                tmp[blankrow+1][blankcol] = 0;
                Board dowRow = new Board(tmp);
                neighorList.add(dowRow);
            }
            
            if(blankcol > 0){
                int tmp[][] = new int[dimesnion][dimesnion];
                for(int i = 0; i < dimesnion; ++i){
                    for(int j = 0; j < dimesnion; ++j){
                        tmp[i][j] = a[i][j];
                    }
                }
                tmp[blankrow][blankcol] = tmp[blankrow][blankcol-1];
                tmp[blankrow][blankcol-1] = 0;
                Board leftRow = new Board(tmp);
                neighorList.add(leftRow);
            }
            
            if(blankcol + 1 < dimesnion){
                int tmp[][] = new int[dimesnion][dimesnion];
                for(int i = 0; i < dimesnion; ++i){
                    for(int j = 0; j < dimesnion; ++j){
                        tmp[i][j] = a[i][j];
                    }
                }
                tmp[blankrow][blankcol] = tmp[blankrow][blankcol+1];
                tmp[blankrow][blankcol+1] = 0;
                Board rightRow = new Board(tmp);
                neighorList.add(rightRow);
            }
            return neighorList.iterator();
        }
    }
    
    public Iterable<Board> neighbors() {    // all neighboring boards
        return new BoardNeighbors();
    }
    
    
    public String toString()   {            // string representation of this board (in the output format specified below)
        StringBuilder builder = new StringBuilder();
        builder.append(dimesnion);
        builder.append("\n");
        for(int i = 0; i < dimesnion; ++i){
            builder.append(" ");
            for(int j = 0; j < dimesnion; ++j){
                builder.append(a[i][j]);
                builder.append("  ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
   
    
    public static void main(String[] args) {// unit tests (not graded)
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        System.out.println("dimenson:" + initial.dimension());
        System.out.println("hamming:" + initial.hamming());
        System.out.println("manhattan:" + initial.manhattan());
        System.out.println("is goal:" + initial.isGoal());
        System.out.println(initial.toString());
        System.out.println("twins :" + initial.twin().toString());
    }
}
