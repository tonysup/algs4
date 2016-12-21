import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int count = 0;
    private Item[] array;

    public RandomizedQueue() { // construct an empty randomized queue
        array = (Item[]) new Object[20];
    }

    public boolean isEmpty() { // is the queue empty?
        return this.count == 0;
    }

    public int size() { // return the number of items on the queue
        return this.count;
    }

    public void enqueue(Item item) { // add the item
        if (item == null) {
            throw new NullPointerException("item is null");
        }
        if (count == array.length) {
            resize(2 * array.length);
        }
        array[count++] = item;
    }

    public Item dequeue() { // remove and return a random item
        if (this.count == 0) {
            throw new NoSuchElementException("queue is empty.");
        }
        int random = StdRandom.uniform(count);
        Item cur = array[random];
        array[random] = array[count - 1];
        array[count - 1] = null;
        count--;
        if (count > 0 && count == array.length / 4) {
            resize(array.length / 2);
        }
        return cur;
    }

    public Item sample() { // return (but do not remove) a random item
        if (this.count == 0) {
            throw new NoSuchElementException("queue is empty.");
        }
        return array[StdRandom.uniform(count)];
    }

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[20];
        for (int i = 0; i < count; i++) {
            temp[i] = array[i];
        }
        array = temp;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int i = count;

        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return i > 0;
        }

        @Override
        public Item next() {
            // TODO Auto-generated method stub
            if(i== 0){
                throw new NoSuchElementException("queue is empty.");
            }
            Item tmp = array[i - 1];
            array[i - 1] = null;
            i--;
            return tmp;
        }
    };

    public Iterator<Item> iterator() { // return an independent iterator over
                                       // items in random order
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) { // unit testing
        RandomizedQueue<Integer> info = new RandomizedQueue<>();
        info.enqueue(5);
        info.enqueue(6);
        System.out.println("dequeue:" + info.dequeue());
        info.enqueue(78);
        System.out.println("sample:" + info.sample());
        info.enqueue(9);
        Iterator<Integer> iterator = info.iterator();
        while (iterator.hasNext()) {
            System.out.println("num:" + iterator.next());
        }
    }
}
