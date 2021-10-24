import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        if(args.length > 0) {
            // Initialise champion to the first string
            String champion = StdIn.readString();
            double i = 2;
            while (!StdIn.isEmpty()) {
                // Calculate probability of 1/i to be the champion
                String word = StdIn.readString();
                if (StdRandom.bernoulli(1 / i)) {
                    champion = word;
                }
                i++;
            }
            // Print the surviving champion
            StdOut.println(champion);
        }
    }
}
