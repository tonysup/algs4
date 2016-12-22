import edu.princeton.cs.algs4.StdIn;

public class Subset {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("args is not legal!");
        }

        RandomizedQueue<String> stringList = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            stringList.enqueue(StdIn.readString());
        }

        int readCount = stringList.size();
        int showcount = Integer.parseInt(args[0]);
        if (showcount < 0 || showcount > readCount) {
            throw new IllegalArgumentException("args[0] is not legal!");
        }

        for (int i = 0; i < showcount; ++i) {
            System.out.println(stringList.dequeue());
        }
    }
}
