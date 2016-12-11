import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int count;
    private int [][] table;
    private WeightedQuickUnionUF uf;
    
    public Percolation(int n){// create n-by-n grid, with all sites blocked
        if(n <= 0){
            throw new IllegalArgumentException("n is not legla!");
        }
        
        count = n;
        table = new int[n][n];
        for(int i = 0; i < count; ++i)
            for(int j=0; j < count; ++j){
                table[i][j] = 0; // 0: full 1:open
            }
   
        uf = new WeightedQuickUnionUF(n*n + 2); // plus 2
        for(int i = 0;i < n; ++i){
            uf.union(i, n*n); // [n*n] : start [n*n+1] : end
            uf.union(n*(n-1) + i, n*n + 1);
        }
    }
    
    private void checkArgs(int row,int col){
        if(row <= 0 || row > count){
            throw new IndexOutOfBoundsException("row index out of bouns");
        }
        
        if(col <= 0 || col > count){
            throw new IndexOutOfBoundsException("column index out of bouns");
        }
    }
    
    public void open(int row, int col){// open site (row, col) if it is not open already
        checkArgs(row, col);

        table[row-1][col-1] = 1;
        if(row - 1 > 0){
            if(table[row - 1 -1][col -1] == 1){
                uf.union((row-1)*count+col-1, (row-1-1)*count + col - 1);
            }
        }
        
        if(row -1 + 1 < count){
            if(table[row -1 + 1][col - 1] == 1){
                uf.union((row-1)*count + col - 1,(row+1-1)*count + col - 1);
            }
        }

        if(col - 1 > 0){
            if(table[row - 1][col - 1 - 1] == 1){
                uf.union((row - 1)*count + col - 1,(row - 1)*count + col - 1 - 1);
            }
        }
        if(col - 1 + 1 < count){
            if(table[row - 1][col + 1 - 1] == 1){
                uf.union((row-1)*count + col - 1, (row-1)*count + col - 1 + 1);
            }
        }
    }
	   
    public boolean isOpen(int row, int col){// is site (row, col) open?
        checkArgs(row, col);

        return table[row-1][col-1] == 1 ? true : false;
    }
   
    public boolean isFull(int row, int col){// is site (row, col) full?
        checkArgs(row,col);
        if(!isOpen(row, col)){
            return false;
        }
        return uf.connected((row-1)*count + col -1, count*count);
    }
   
    public boolean percolates(){// does the system percolate?
        return uf.connected(count*count, count*count + 1);
    }
	   
    public static void main(String[] args){// test client (optional)
   /* try{
	   Percolation percolation = new Percolation(4);
	   percolation.open(1, 1);
	   percolation.open(2, 1);
	   percolation.open(3, 1);
	   percolation.open(4, 1);
	   percolation.open(3, 3);
	   System.out.println("is open:" + percolation.isOpen(2, 1));
       System.out.println("is percolates:" + percolation.percolates());
       }catch(ArrayIndexOutOfBoundsException ex){
    	   ex.printStackTrace();
       }catch (Exception ex) {
    	   ex.printStackTrace();
       } */
    }
}
