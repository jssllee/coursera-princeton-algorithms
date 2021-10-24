import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int index;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[10];
        index = 0;
    }

    private void resizeQueue() {
        Item[] largerQueue = (Item[]) new Object[queue.length * 2];
        System.arraycopy(queue, 0, largerQueue, 0, queue.length);
        queue = largerQueue;
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
        if (index > queue.length - 1) {
            resizeQueue();
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
        System.arraycopy(queue, removeIndex + 1, queue, removeIndex, (--index - removeIndex));
        return toReturn;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size() == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        return queue[StdRandom.uniform(index)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            @Override
            public boolean hasNext() {
                return size() > 0;
            }

            @Override
            public Item next() {
                if (size() == 0) {
                    throw new NoSuchElementException("No elements");
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
        StdOut.printf("Same element returned: %s\n", stringRandomizedQueue.sample());
        StdOut.printf("Dequeue element returned: %s\n", stringRandomizedQueue.dequeue());
        StdOut.printf("Size expected: 2, actual: %b\n", stringRandomizedQueue.size());
        for (String s : stringRandomizedQueue) {
            StdOut.printf("%s ", s);
        }
    }
}