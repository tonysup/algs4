import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> arrayList = new ArrayList<>();
    
    public FastCollinearPoints(Point[] inpoints)  {   // finds all line segments containing 4 or more points
        if(inpoints == null){
            throw new NullPointerException("points is null.");
        }
        
        ArrayList<Point> group = null;
        Point[] points = inpoints.clone();
        for(int i = 0; i < points.length; ++i){
            Point original = points[i];
            int begin = i+1;
            Arrays.sort(points,begin,points.length,original.slopeOrder());
            double currentSlope = original.slopeTo(points[begin]);
            if (currentSlope == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException();
            }
            group = new ArrayList<>();
            group.add(original);
            group.add(points[begin]);
            for(int j = begin + 1; j < points.length; ++j){
                Point p = points[j];
                double slope = original.slopeTo(p);
                if (currentSlope == slope) {
                    group.add(p);
                    if (group.size() >= 4 && j == points.length - 1) {
                        addGroup(group);
                    }
                } else {
                    if (group.size() >= 4) {
                        addGroup(group);
                    }
                    group = new ArrayList<>();
                    group.add(original);
                    group.add(p);
                    currentSlope = slope;
                }
            }
            
        }
    }
    
    private void addGroup(ArrayList<Point> list){
        list.sort(null);
        arrayList.add(new LineSegment(list.get(0), list.get(list.size()-1)));
    }
    public int numberOfSegments() {       // the number of line segments
        return arrayList.size();
    }
    public LineSegment[] segments() {               // the line segments
        return arrayList.toArray(new LineSegment[arrayList.size()]);
    }    
}
