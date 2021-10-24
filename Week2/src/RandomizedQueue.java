import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INITIAL_SIZE = 2;
    private Item[] queue;
    private int index;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[INITIAL_SIZE];
        index = 0;
    }

    private void resizeQueue(int length) {
        Item[] newQueue = (Item[]) new Object[length];
        System.arraycopy(queue, 0, newQueue, 0, index);
        queue = newQueue;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return index == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return index;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (index >= queue.length) {
            resizeQueue(queue.length * 2);
        }
        queue[index++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size() == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        int removeIndex = StdRandom.uniform(index);
        Item toReturn = queue[removeIndex];
        queue[removeIndex] = queue[--index];
        if (index < queue.length / 4) {
            resizeQueue(queue.length / 2);
        }
        return toReturn;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (index < 1) {
            throw new NoSuchElementException("Queue is empty");
        }
        return queue[StdRandom.uniform(index)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            @Override
            public boolean hasNext() {
                return index > 0;
            }

            @Override
            public Item next() {
                if (size() == 0) {
                    throw new NoSuchElementException("Queue is empty");
                }
                return dequeue();
            }
        };
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<String> stringRandomizedQueue = new RandomizedQueue<>();
        stringRandomizedQueue.enqueue("apple");
        stringRandomizedQueue.enqueue("pear");
        stringRandomizedQueue.enqueue("orange");
        StdOut.printf("Size expected: 3, actual: %d\n", stringRandomizedQueue.size());
        StdOut.printf("IsEmpty expected: false, actual: %b\n", stringRandomizedQueue.isEmpty());
        StdOut.printf("Sample element returned: %s\n", stringRandomizedQueue.sample());
        StdOut.printf("Dequeue element returned: %s\n", stringRandomizedQueue.dequeue());
        StdOut.printf("Size expected: 2, actual: %b\n", stringRandomizedQueue.size());
        for (String s : stringRandomizedQueue) {
            StdOut.printf("%s ", s);
        }
    }
}