import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int count = 0;
    private Node first = null;
    private Node last = null;

    class Node {
        Item info;
        Node pre;
        Node next;

        public Node(Item item, Node pre, Node next) {
            this.info = item;
            this.pre = pre;
            this.next = next;
        }
    };

    public Deque() { // construct an empty deque
        this.count = 0;
        this.first = this.last = null;
    }

    public boolean isEmpty() { // is the deque empty?
        return this.count == 0;
    }

    public int size() { // return the number of items on the deque
        return this.count;
    }

    public void addFirst(Item item) { // add the item to the front
        if (item == null) {
            throw new NullPointerException("item is null");
        }
        Node cur = new Node(item, null, null);
        if (first == null) {
            this.first = cur;
            this.last = cur;
        } else {
            this.first.pre = cur;
            cur.next = this.first;
            this.first = cur;
        }
        count++;
    }

    public void addLast(Item item) { // add the item to the end
        if (item == null) {
            throw new NullPointerException("item is null");
        }

        Node cur = new Node(item, null, null);
        if (this.last == null) {
            this.first = cur;
            this.last = cur;
        } else {
            this.last.next = cur;
            cur.pre = this.last;
            this.last = cur;
        }
        count++;
    }

    public Item removeFirst() { // remove and return the item from the front
        if (count == 0) {
            throw new NoSuchElementException("deque is empty.");
        }
        Item tmp = first.info;
        first.next.pre = null;
        first = first.next;
        count--;
        return tmp;
    }

    public Item removeLast() { // remove and return the item from the end
        if (count == 0) {
            throw new NoSuchElementException("deque is empty.");
        }
        Item tmp = last.info;
        last.pre.next = null;
        last = last.pre;
        count--;
        return tmp;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node cur = first;

        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return cur != null;
        }

        @Override
        public Item next() {
            // TODO Auto-generated method stub
            if (cur == null) {
                throw new NoSuchElementException("no such element");
            }
            Item tmp = cur.info;
            cur = cur.next;
            return tmp;
        }

    }

    public Iterator<Item> iterator() { // return an iterator over items in order
                                       // from front to end
        return new DequeIterator();
    }

    public static void main(String[] args) { // unit testing
        Deque<Integer> intlist = new Deque<>();
        intlist.addFirst(5);
        intlist.addLast(8);
        intlist.removeFirst();
        intlist.addFirst(13);
        intlist.addLast(67);
        intlist.removeLast();

        Iterator<Integer> iterator = intlist.iterator();
        while (iterator.hasNext()) {
            System.out.println("info:" + iterator.next());
        }
    }
}
