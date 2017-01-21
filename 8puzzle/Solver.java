import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private boolean isSolver = false;
    private int moves = -1;
    private Deque<Board> arrayList = null;
    private class Node{
        Board board = null;
        int   moves = 0;
        Node  preNode = null;
    };
    
    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        if(initial == null){
            throw new NullPointerException("board is null.");
        }
        Node init = new Node();
        init.board  = initial;
        init.moves = 0;
        init.preNode = null;
        Node twinsInit = new Node();
        twinsInit.board  = initial.twin();
        twinsInit.moves = 0;
        twinsInit.preNode = null;
        MinPQ<Node> minPQ = new MinPQ<>(new SolverCompartor());
        MinPQ<Node> twinPQ = new MinPQ<>(new SolverCompartor());
        twinPQ.insert(twinsInit);
        minPQ.insert(init);
        arrayList = new ArrayDeque<Board>();
        //int count = 0;
        
        while(true){
            if(minPQ.isEmpty()){
                //System.out.println("count is " + count);
                //System.out.println("pq is empty.");
                break;
            }
            Node node = minPQ.delMin();
            Node twinNode = twinPQ.delMin();
            if(node.board.isGoal()){
                moves = node.moves;
                Node tmp = node;
                isSolver = true;
                while(tmp != null){
                    arrayList.addFirst(tmp.board);
                    tmp = tmp.preNode;
                }
                //System.out.println("total count:" + count);
                break;
            }
            if(twinNode.board.isGoal()){
                break;
            }
            
            //System.out.println("node:" + node.board.toString());
            Iterable<Board> neighList = node.board.neighbors();
            if(neighList != null){
                Iterator<Board> iterator = neighList.iterator();
                while(iterator.hasNext()){
                    Board tmpBoard = iterator.next();
                    //System.out.println("neigh:" + tmpBoard.toString());
                    Node checkNode = node.preNode;
                    boolean findEqual = false;
                    while(checkNode != null){
                        if(tmpBoard.equals(checkNode.board)){
                            findEqual = true;
                            break;
                        }
                        checkNode = checkNode.preNode;
                    }
                    if(findEqual){
                        continue;
                    }
                    Node tmpNode = new Node();
                    tmpNode.board = tmpBoard;
                    tmpNode.moves = node.moves + 1;
                    tmpNode.preNode = node;
                    minPQ.insert(tmpNode);
                }
            }
            
            Iterable<Board> twinNeigh = twinNode.board.neighbors();
            if(neighList != null){
                Iterator<Board> iterator = twinNeigh.iterator();
                while(iterator.hasNext()){
                    Board tmpBoard = iterator.next();
                    /*if(twinNode.preNode != null){
                        if(tmpBoard.equals(twinNode.preNode.board)){
                            continue;
                        }
                    }*/
                    Node checkNode = twinNode.preNode;
                    boolean findEqual = false;
                    while(checkNode != null){
                        if(tmpBoard.equals(checkNode.board)){
                            findEqual = true;
                            break;
                        }
                        checkNode = checkNode.preNode;
                    }
                    if(findEqual){
                        continue;
                    }
                    Node tmpNode = new Node();
                    tmpNode.board = tmpBoard;
                    tmpNode.moves = twinNode.moves + 1;
                    tmpNode.preNode = twinNode;
                    twinPQ.insert(tmpNode);
                }
            }
            //++count;
        }
    }
    
    private class SolverCompartor implements Comparator<Node>{

        @Override
        public int compare(Node o1, Node o2) {
            // TODO Auto-generated method stub
            if(o1.board.manhattan() + o1.moves < o2.board.manhattan() + o2.moves){
                return -1;
            }else if(o1.board.manhattan() + o1.moves == o2.board.manhattan() + o2.moves){
                return 0;
            }else{
                return 1;
            }
        }  
    }
    
    public boolean isSolvable() {           // is the initial board solvable?
        return this.isSolver;
    }
    
    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        return this.moves;
    }
    
    private class SolverIterable implements Iterable<Board>{

        @Override
        public Iterator<Board> iterator() {
            // TODO Auto-generated method stub
            //Collections.reverse(arrayList);
            return arrayList.iterator();
        }   
    }
    
    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        return isSolver ? new SolverIterable() : null;
    }
    
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
