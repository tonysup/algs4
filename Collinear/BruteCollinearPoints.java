import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
   private ArrayList<LineSegment> arrayList;
   
   public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
       if(points == null){
           throw new NullPointerException();
       }
       arrayList = new ArrayList<>();
       Point group[] = new Point[4];
       for(int i=0; i < points.length; ++i){
           for(int j=i+1; j < points.length; ++j){
               if(points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                   throw new IllegalArgumentException();
               }
               for(int k=j+1; k < points.length;++k){
                   if(points[i].slopeTo(points[k]) == Double.NEGATIVE_INFINITY) {
                       throw new IllegalArgumentException();
                   }
                   for(int m = k+ 1; m < points.length; ++m){
                       if(points[i].slopeTo(points[m]) == Double.NEGATIVE_INFINITY) {
                           throw new IllegalArgumentException();
                       }
                       if(points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
                          points[i].slopeTo(points[j]) == points[i].slopeTo(points[m])){
                           group[0] = points[i];
                           group[1] = points[j];
                           group[2] = points[k];
                           group[3] = points[m];
                           Arrays.sort(group);
                           arrayList.add(new LineSegment(group[0], group[3]));
                       }
                   }
               }
           }
       }
   }
   public int numberOfSegments() {       // the number of line segments
       return arrayList.size();
   }
   public LineSegment[] segments() {               // the line segments
       return arrayList.toArray(new LineSegment[arrayList.size()]);
   }
   
   public static void main(String[] args) {

       // read the n points from a file
       In in = new In(args[0]);
       int n = in.readInt();
       Point[] points = new Point[n];
       for (int i = 0; i < n; i++) {
           int x = in.readInt();
           int y = in.readInt();
           points[i] = new Point(x, y);
       }

       // draw the points
       StdDraw.enableDoubleBuffering();
       StdDraw.setXscale(0, 32768);
       StdDraw.setYscale(0, 32768);
       for (Point p : points) {
           p.draw();
       }
       StdDraw.show();

       // print and draw the line segments
       BruteCollinearPoints collinear = new BruteCollinearPoints(points);
       for (LineSegment segment : collinear.segments()) {
           StdOut.println(segment);
           segment.draw();
       }
       StdDraw.show();
   }
}

