import java.util.ArrayList;
import java.util.Iterator;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET{
   private SET<Point2D>  ponitsSet;
   
   public PointSET() {                              // construct an empty set of points 
       ponitsSet = new SET<>();
   }
   
   public boolean isEmpty() {                     // is the set empty? 
       return ponitsSet.isEmpty();
   }
   
   public int size() {                        // number of points in the set 
       return ponitsSet.size();
   }
   
   public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
       if(p == null){
           throw new NullPointerException("param is null");
       }
       ponitsSet.add(p);
   }
   
   public boolean contains(Point2D p) {           // does the set contain point p? 
       if(p == null){
           throw new NullPointerException("param is null");
       }
       return ponitsSet.contains(p);
   }
   
   public void draw() {                        // draw all points to standard draw 
       for(Point2D tmpPoint2d : ponitsSet){
           tmpPoint2d.draw();
       }
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
       for(Point2D tmpPonit2d : ponitsSet){
           if(rect.contains(tmpPonit2d)){
               recList.add(tmpPonit2d);
           }
       }
       return new InnerIterable<>(recList);
   }
   
   public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
       if(p == null){
           throw new NullPointerException("param is null.");
       }
       
       if(ponitsSet.isEmpty()){
           return null;
       }
       
       double dis = 0;
       Point2D tmPoint2d = null;
       boolean firstInit = true;
       for(Point2D tmPoint : ponitsSet){
           if(firstInit){
              dis = tmPoint.distanceTo(p);
              tmPoint2d = tmPoint;
           }else {
               if(tmPoint.distanceTo(p) < dis){
                   dis = tmPoint.distanceTo(p);
                   tmPoint2d = tmPoint;
               }
           }
       }
       return tmPoint2d;
   }    
   
   public static void main(String[] args) {                 // unit testing of the methods (optional) 
   }
  
}
