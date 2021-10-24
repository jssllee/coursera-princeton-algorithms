import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final boolean BLOCKED = false;
    private static final boolean OPEN = true;
    private final boolean[][] grid;
    private final int gridSize;
    private final int virtualTopSite;
    private final int virtualBottomSite;
    private final WeightedQuickUnionUF qf;
    private int numOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be > 0");
        }
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = BLOCKED;
            }
        }
        gridSize = n;
        numOpenSites = 0;

        // Initialise UF with virtual top/bottom sites for checking percolation
        qf = new WeightedQuickUnionUF(n * n + 2);
        virtualTopSite = n * n;
        virtualBottomSite = (n * n) + 1;
    }

    // check if a requested row/col is out of bounds
    private void checkOutOfBounds(int row, int col) {
        if (row <= 0 || row > gridSize || col <= 0 || col > gridSize) {
            throw new IllegalArgumentException(String.format("Site [%d, %d] is out of bounds", row, col));
        }
    }

    // convert a row/col input into a siteID for UF
    private int siteID(int row, int col) {
        return (gridSize * (row - 1)) + (col - 1);
    }

    // unions a siteID to a row/col if it's open
    private void union(int siteID, int row, int col) {
        if (row > 0 && row <= gridSize && col > 0 && col <= gridSize && isOpen(row, col)) {
            qf.union(siteID, siteID(row, col));
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkOutOfBounds(row, col);
        if (grid[row - 1][col - 1] == BLOCKED) {
            grid[row - 1][col - 1] = OPEN;
            numOpenSites++;
            // Union the current site to adjacent open sites
            int currSite = siteID(row, col);
            union(currSite, row, col - 1);
            union(currSite, row, col + 1);
            union(currSite, row - 1, col);
            union(currSite, row + 1, col);

            // special case to connect first row to the virtualTopSite and last row to the virtualBottomSite
            if (row == 1) {
                qf.union(currSite, virtualTopSite);
            } else if (row == gridSize) {
                qf.union(currSite, virtualBottomSite);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkOutOfBounds(row, col);
        return grid[row - 1][col - 1] == OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkOutOfBounds(row, col);
        return qf.find(virtualTopSite) == qf.find(siteID(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return qf.find(virtualTopSite) == qf.find(virtualBottomSite);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Percolation p = new Percolation(n);
        while (!p.percolates()) {
            int random = StdRandom.uniform(n * n);
            if (!p.isOpen((random / n + 1), (random % n + 1))) {
                p.open((random / n + 1), (random % n + 1));
            }
        }
        System.out.printf("Percolation threshold is: %d / %d = %.5f\n",
                p.numberOfOpenSites(), (n * n), (double) p.numberOfOpenSites() / (n * n));
    }
}