import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private int trials; 
    private double[] threshold; 

    private double interval = 1.96; 
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Can't be less than 0");
        }
        this.trials = trials; 
        threshold = new double[trials]; 
        
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n); 

            int currentlyOpenSites = 0; 

            while (!percolation.percolates()) {
                int row = StdRandom.uniformInt(1, n+1); 
                int col = StdRandom.uniformInt(1, n+1); 

                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col); 
                    currentlyOpenSites++; 
                }
            }
            threshold[i] = (double) currentlyOpenSites / (n* n); 
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold); 
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold); 
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (interval * stddev()) / Math.sqrt(trials);
    }   

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (interval * stddev()) / Math.sqrt(trials);
    }

   // test client (see below)
   public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);

        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", "
                + stats.confidenceHi() + "]");
   }

}