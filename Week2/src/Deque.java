import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private static final int INITIAL_SIZE = 10;
    private Item[] queue;
    private int queueSize;
    private int index;

    // construct an empty deque
    public Deque() {
        queueSize = INITIAL_SIZE;
        queue = (Item[]) new Object[queueSize];
        index = 0;
    }

    private void resizeQueue() {
        queueSize *= 2;
        Item[] largerQueue = (Item[]) new Object[queueSize];
        System.arraycopy(queue, 0, largerQueue, 0, queue.length);
        queue = largerQueue;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return index == 0;
    }

    // return the number of items on the deque
    public int size() {
        return index;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (index >= queueSize) {
            resizeQueue();
        }
        System.arraycopy(queue, 0, queue, 1, index);
        queue[0] = item;
        index++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (index >= queueSize - 1) {
            resizeQueue();
        }
        queue[index++] = item;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size() == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = queue[0];
        index--;
        System.arraycopy(queue, 1, queue, 0, index);
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size() == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        return queue[--index];
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < index;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No elements");
                }
                return queue[i++];
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> stringDeque = new Deque<>();
        stringDeque.addFirst("apple");
        stringDeque.addLast("pear");
        stringDeque.addFirst("orange");
        StdOut.printf("Size expected: 3, actual: %d\n", stringDeque.size());
        StdOut.printf("IsEmpty expected: false, actual: %b\n", stringDeque.isEmpty());
        StdOut.printf("RemoveFirst element expected: orange, actual: %s\n", stringDeque.removeFirst());
        StdOut.printf("RemoveLast element expected: pear, actual: %s\n", stringDeque.removeLast());
        StdOut.printf("RemoveLast element expected: apple, actual: %s\n", stringDeque.removeLast());
        StdOut.printf("IsEmpty expected: true, actual: %b\n", stringDeque.isEmpty());

        Deque<Integer> intDeque = new Deque<>();
        for (int i = 0; i < 20; i++) {
            intDeque.addLast(i);
        }
        StdOut.printf("Size expected: 20, actual: %d\n", intDeque.size());
        for (Integer integer : intDeque) {
            StdOut.printf("%d ", integer);
        }
    }
}