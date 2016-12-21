
public class Subset {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("args is not legal!");
        }

        int showcount = Integer.parseInt(args[0]);
        if (showcount < 0 || showcount > args.length - 1) {
            throw new IllegalArgumentException("args[0] is not legal!");
        }

        RandomizedQueue<String> stringList = new RandomizedQueue<>();
        for (int i = 1; i < args.length; ++i) {
            stringList.enqueue(args[i]);
        }

        for (int i = 0; i < showcount; ++i) {
            System.out.println(stringList.dequeue());
        }
    }
}
