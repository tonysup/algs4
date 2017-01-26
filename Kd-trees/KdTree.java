import java.util.ArrayList;
import java.util.Iterator;


import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    
    private enum Color{
        RED,BLUE
    };
    
    private class TreeNode{
        TreeNode left;
        TreeNode right;
        Point2D  point2d;
        Color    color;
        
        public TreeNode(){
            left = null;
            right = null;
            point2d = null;
            color = Color.RED;
        }
    }
    
    private TreeNode root;
    private int count;
    
    public KdTree() {                              // construct an empty set of points 
        root = null;
        count = 0;
    }
    
    public boolean isEmpty() {                     // is the set empty? 
        return root == null;
    }
    
    public int size() {                        // number of points in the set 
        return count;
    }
    
    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        if(p == null){
            throw new NullPointerException("param is null.");
        }
        
        root = insertNode(root, p, Color.RED);
        ++count;
    }
    
    private TreeNode insertNode(TreeNode node,Point2D p,Color color){
        if(node == null){
            node = new TreeNode();
            node.point2d = p;
            node.color   = color;
            return node;
        }else{
            if(node.point2d.equals(p)){
                return node;
            }else{
                if(color == Color.RED){
                    if(p.x() < node.point2d.x()){
                        node.left = insertNode(node.left, p, node.color == Color.RED ? Color.BLUE : Color.RED);
                    }else{
                        node.right = insertNode(node.right, p, node.color == Color.RED ? Color.BLUE : Color.RED);
                    }
                }else{
                    if(p.y() < node.point2d.y()){
                        node.left = insertNode(node.left, p, node.color == Color.RED ? Color.BLUE : Color.RED);
                    }else{
                        node.right = insertNode(node.right, p, node.color == Color.RED ? Color.BLUE : Color.RED);
                    }
                }
            }
        }
        return node;
    }
    
    public boolean contains(Point2D p) {           // does the set contain point p? 
        if(p == null){
            throw new NullPointerException("param is null.");
        }
        return findNode(root, p, Color.RED) == null ? false : true;
    }
    
    private Point2D findNode(TreeNode node,Point2D p,Color color){
        if(node == null){
            return null;
        }
        if(node.point2d.equals(p)){
            return p;
        }
        
        if(color == Color.RED){
            if(p.x() < node.point2d.x()){
                return findNode(node.left, p, color == Color.RED ? Color.BLUE : Color.RED);
            }else{
                return findNode(node.right, p, color == Color.RED ? Color.BLUE : Color.RED);
            }
        }else{
            if(p.y() < node.point2d.y()){
                return findNode(node.left, p, color == Color.RED ? Color.BLUE : Color.RED);
            }else{
                return findNode(node.right, p, color == Color.RED ? Color.BLUE : Color.RED);
            }
        }
    }
    
    public void draw() {                        // draw all points to standard draw 
        
    }
    
    private class InnerIterable<T> implements Iterable<T>{

        private ArrayList<T> list;
        
        public InnerIterable(ArrayList<T> list){
            this.list = list;
        }
        
        @Override
        public Iterator<T> iterator() {
            // TODO Auto-generated method stub
            return list == null ? null : list.iterator();
        }    
     }
    
    public Iterable<Point2D> range(RectHV rect)  {           // all points that are inside the rectangle 
        if(rect == null){
            throw new NullPointerException("param is null");
        }
        
        ArrayList<Point2D> recList = new ArrayList<>();
        ListRec(root, rect, recList);
        return new InnerIterable<>(recList);
    }
    
    private void ListRec(TreeNode node,RectHV rect,ArrayList<Point2D> list){
        if(node == null){
            return;
        }
        
        if(rect.contains(node.point2d)){
            list.add(node.point2d);
            //ListRec(node.left, rect, list);
            //ListRec(node.right, rect, list);
        }
        
        if(node.left != null){
            double imin = node.point2d.x() < node.left.point2d.x() ? node.point2d.x() : node.left.point2d.x();
            double imax = node.point2d.x() > node.left.point2d.x() ? node.point2d.x() : node.left.point2d.x();
            double ymin = node.point2d.y() < node.left.point2d.y() ? node.point2d.y() : node.left.point2d.y();
            double ymax = node.point2d.y() > node.left.point2d.y() ? node.point2d.y() : node.left.point2d.y();
            
            RectHV tmp = new RectHV(imin, ymin, imax, ymax);
            if(rect.intersects(tmp)){
                ListRec(node.left, rect, list);
            }
        }
        
        if(node.right != null){
            double imin = node.point2d.x() < node.right.point2d.x() ? node.point2d.x() : node.right.point2d.x();
            double imax = node.point2d.x() > node.right.point2d.x() ? node.point2d.x() : node.right.point2d.x();
            double ymin = node.point2d.y() < node.right.point2d.y() ? node.point2d.y() : node.right.point2d.y();
            double ymax = node.point2d.y() > node.right.point2d.y() ? node.point2d.y() : node.right.point2d.y();
            
            RectHV tmp = new RectHV(imin, ymin, imax, ymax);
            if(rect.intersects(tmp)){
                ListRec(node.right, rect, list);
            }
        }
    }
    
    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        if(p == null){
            throw new NullPointerException("param is null");
        }
        
        if(isEmpty()){
            return null;
        }
        
        double dist = root.point2d.distanceTo(p);
        Point2D distNear = root.point2d;
        nearestNode(root, p, dist, distNear);
        return distNear;
    }
    
    private void nearestNode(TreeNode node,Point2D p,double dist,Point2D nearPoint){
        if(node == null){
            return;
        }
        if(node.point2d.distanceTo(p) < dist){
            dist = node.point2d.distanceTo(p);
            nearPoint = node.point2d;
            nearestNode(node.left,p,dist,nearPoint);
            nearestNode(node.right,p,dist,nearPoint);
        }
    }
    
    
    public static void main(String[] args) {                 // unit testing of the methods (optional) 
    }
}
