import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid; 
    private int dimensions = 0; 
    private WeightedQuickUnionUF uf;  
    private WeightedQuickUnionUF fullUF;
    private int openSites = 0; 
    private int top = 0; 
    private int bottom = 0; 
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N cannot be less than zero");
        }

        dimensions = n; 
        grid = new boolean[n][n]; 

        fullUF = new WeightedQuickUnionUF(n * n + 1);
        uf = new WeightedQuickUnionUF(n * n + 2); 

        openSites = 0; 

        top = 0; 
        bottom = n * n + 1; 

        for (int i = 1; i <= n; i++) {
            uf.union(top, getCurrentIndex(1, i));
            // uf.union(bottom, getCurrentIndex(n, i));
            fullUF.union(top, getCurrentIndex(1, i));
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateDimensions(row, col);

        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            openSites++; 

            connect(row, col); 
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateDimensions(row, col);
        return grid[row - 1][col - 1]; 
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateDimensions(row, col);

        return isOpen(row, col) && fullUF.find(getCurrentIndex(row, col)) == fullUF.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites; 
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(top) == uf.find(bottom);
    }

    private void validateDimensions(int row, int col) {
        if (row < 1 || row > dimensions || col < 1 || col > dimensions) {
            throw new IllegalArgumentException("Given dimensions are out of bounds"); 
        }
    }

    private void connect(int row, int col) {
        int currentIndex = getCurrentIndex(row, col);

        if (row > 1 && isOpen(row - 1, col)) { 
            uf.union(currentIndex, getCurrentIndex(row - 1, col));
            fullUF.union(currentIndex, getCurrentIndex(row - 1, col)); 
        }
        if (row < dimensions && isOpen(row + 1, col)) { 
            uf.union(currentIndex, getCurrentIndex(row + 1, col));
            fullUF.union(currentIndex, getCurrentIndex(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(currentIndex, getCurrentIndex(row, col - 1));
            fullUF.union(currentIndex, getCurrentIndex(row, col - 1)); 
        }
        if (col < dimensions && isOpen(row, col + 1)) {
            uf.union(currentIndex, getCurrentIndex(row, col + 1));
            fullUF.union(currentIndex, getCurrentIndex(row, col+1));
        }

        if (row == dimensions) {
            uf.union(currentIndex, bottom); 
        }
    }
    
    private int getCurrentIndex(int row, int col) {
        validateDimensions(row, col);
        return (row - 1) * dimensions + (col - 1) + 1; 
    }

    // test client (optional)
    public static void main(String[] args) {

    }

    
}