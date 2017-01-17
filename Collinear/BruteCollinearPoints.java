import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
   private Point[] points;
   
   public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
       if(points == null){
           throw new NullPointerException();
       }
       for(int i = 0; i < points.length; ++i){
           for(int j = i+1; j < points.length; ++j){
               if(points[i].compareTo(points[j]) == 0){
                   throw new IllegalArgumentException("points array must not be equal.");
               }
           }
       }
       this.points = points;
   }
   public int numberOfSegments() {       // the number of line segments
       return segments().length;
   }
   public LineSegment[] segments() {               // the line segments
       if(points.length < 4){
           return new LineSegment[0];
       }
       ArrayList<LineSegment> arrayList = new ArrayList<>();
       Point group[] = new Point[4];
       for(int i=0; i < points.length; ++i){
           for(int j=i+1; j < points.length; ++j){
               for(int k=j+1; k < points.length;++k){
                   for(int m = k+ 1; m < points.length; ++m){
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
       LineSegment[] segmentList = new LineSegment[arrayList.size()];
       segmentList = arrayList.toArray(segmentList);
       return segmentList;
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

