import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
   private int count = 0; 
   public RandomizedQueue() {                // construct an empty randomized queue
   }
   public boolean isEmpty()  {               // is the queue empty?
       return this.count == 0;
   }
   public int size() {                       // return the number of items on the queue
       return this.count;
   }
   public void enqueue(Item item)  {         // add the item
       if(item == null) {
           throw new NullPointerException("item is null");
       }
   }
   public Item dequeue() {                   // remove and return a random item
       if(this.count == 0) {
           throw new NoSuchElementException("queue is empty.");
       }
       return null;
   }
   public Item sample() {                    // return (but do not remove) a random item
       if(this.count == 0) {
           throw new NoSuchElementException("queue is empty.");
       }
       return null;
   }
   
   private class RandomizedQueueIterator implements Iterator<Item> {

       @Override
       public boolean hasNext() {
           // TODO Auto-generated method stub
           return false;
       }

       @Override
       public Item next() {
           // TODO Auto-generated method stub
           Item tmp = null;
           return tmp;
       }
   };
   
   public Iterator<Item> iterator()   {      // return an independent iterator over items in random order
       return new RandomizedQueueIterator();
   }
   public static void main(String[] args) {  // unit testing
       
   }
}
