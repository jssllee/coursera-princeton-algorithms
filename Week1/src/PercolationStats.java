import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE = 1.96;
    private final int trials;
    private final double[] thresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        thresholds = new double[trials];
        this.trials = trials;
        while (trials > 0) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int random = StdRandom.uniform(n * n - p.numberOfOpenSites());
                while (p.isOpen((random / n + 1), (random % n + 1))) {
                    random++;
                    if (random >= (n * n)) {
                        random = 0;
                    }
                }
                p.open((random / n + 1), (random % n + 1));
            }
            thresholds[trials - 1] = (double) p.numberOfOpenSites() / (n * n);
            trials--;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        // mean - (1.96s/sqrt(T))
        return mean() - (CONFIDENCE * stddev() / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        // mean + (1.96s/sqrt(T))
        return mean() + (CONFIDENCE * stddev() / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.printf("mean                     = %f\n", stats.mean());
        System.out.printf("stddev                   = %f\n", stats.stddev());
        System.out.printf("95%% confidence interval = [%f, %f]\n", stats.confidenceLo(), stats.confidenceHi());
    }

}