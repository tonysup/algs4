import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
   private int  count;
   private int  times;
   private double[]  record;
   private double mean;

   public PercolationStats(int n, int trials) { // perform trials independent experiments on an n-by-n grid
       if (n <= 0 || trials <= 0) { 
           throw new IllegalArgumentException("n or trails is illegal!");
       }
       count = n;
       times = trials;
       record = new double[trials];
       for (int i = 0; i < trials; ++i) { 
           Percolation percolation = new Percolation(n);
           int passCount = 0;
           while (!percolation.percolates()) { 
               int random = StdRandom.uniform(n*n);
               int row = random/n + 1;
               int col = random % n + 1;
               if (percolation.isOpen(row, col)) { 
                   continue;
               }
               percolation.open(row, col);
               passCount++;
           }
           record[i] = passCount*1.0/(count*count);
       }
       
       mean = StdStats.mean(record);
   }
   
   public double mean() { // sample mean of percolation threshold
       return mean;
   }
   
   public double stddev() { // sample standard deviation of percolation threshold
       return StdStats.stddev(record);
   }
   
   public double confidenceLo() { // low  endpoint of 95% confidence interval
       return mean - (1.96*Math.sqrt(stddev()))/Math.sqrt(times);
   }
   
   public double confidenceHi() { // high endpoint of 95% confidence interval
       return mean - (1.96*Math.sqrt(stddev()))/Math.sqrt(times);
   }
   
   public static void main(String[] args) { // test client (described below){
       if (args.length < 2) { 
           throw new IllegalArgumentException("args is not legal!");
       }
       
       int n = Integer.parseInt(args[0]);
       int trial = Integer.parseInt(args[1]);
       PercolationStats percolationStats = new PercolationStats(n, trial);
       System.out.println("mean:                   = " + percolationStats.mean());
       System.out.println("stddev:                 = " + percolationStats.stddev());
       System.out.println("95% confidence interval = " 
                  + percolationStats.confidenceHi() + "," + percolationStats.confidenceHi());
   }
}
