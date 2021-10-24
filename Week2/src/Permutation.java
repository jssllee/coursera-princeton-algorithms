import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int numInputs = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        for (int i = 0; i < numInputs; i++) {
            rq.enqueue(StdIn.readString());
        }
        for (String s : rq) {
            StdOut.println(s);
        }
    }
}