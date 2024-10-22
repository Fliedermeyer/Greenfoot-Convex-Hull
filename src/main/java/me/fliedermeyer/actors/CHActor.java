package me.fliedermeyer.actors;

import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Stack;

public abstract class CHActor extends BBActor {

    // Every subclass needs to implement this method using Point values representing
    // a location x,y in coordinate space
    // -> Every convex hull object must deliver points to calculate the convex hull
    protected abstract Point[] getPoints();

    // Calculate the complete convex hull by processing points triplet by triplet
    protected Point[] calculateConvexHull(Point[] points) {

        points = getPoints();
        points = removeDuplicates(points);

        int numOfPts = points.length; // Number of points in the array

        // Convex Hull isn't constructable if there are less than 3 points, because in
        // that case it's only a point or a line -> Return empty array
        if (numOfPts < 3) {
            return new Point[0];
        }

        int minY = 0; // Index of the lowest point

        // Find the index of lowest point
        for (int i = 1; i < numOfPts; i++) {
            if (points[i].y < points[minY].y) {
                minY = i;
            }

            // If 2 points have the same minimum y-value, take the point with the larger
            // x-value
            else if (points[i].y == points[minY].y && points[i].x > points[minY].x) {
                minY = i;
            }
        }

        Point minYPoint = points[minY];
        final Point base = minYPoint;

        // Sort points based on their orientation relative to the base point
        Arrays.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {
                int orientation = getOrientation(base, a, b);

                // if points are collinear, sort them by distance from the base
                if (orientation == 0) {
                    int distanceA = getDistance(base, a);
                    int distanceB = getDistance(base, b);
                    return Integer.compare(distanceA, distanceB);

                } else if (orientation == 2) {
                    return -1; // Counterclockwise -> a should come before b
                } else {
                    return 1; // Clockwise or collinear -> b should come before a
                }
            }
        });

        Stack<Point> convexHull = new Stack<>();
        // Push the first 2 points onto the convexHull stack
        convexHull.push(points[0]);
        convexHull.push(points[1]);

        // Build the convex hull
        for (int i = 2; i < numOfPts; i++) {
            Point front = points[i]; // Current point beeing processed
            Point middle = convexHull.pop(); // Point at the top of the stack
            Point back = convexHull.peek(); // Point below the middle point

            switch (getOrientation(back, middle, front)) {
                case 2: // Counterclockwise
                    convexHull.push(middle); // Keep middle point in the hull
                    convexHull.push(front); // Add the current front point to the hull
                    break;
                case 1: // Clockwise
                    i--; // Process the current point again
                    break;
                case 0: // Collinear
                    convexHull.push(front); // Add the current point to the hull
                    break;
            }
        }

        convexHull.push(points[0]); // Close the hull by adding the starting point again

        // Debugging
        for (Point point : convexHull) {
            System.out.println("CH: " + point.x + ", " + point.y);
        }

        return convexHull.toArray(new Point[0]);
    }

    /*
     * Calculation of the orientation of the middle triplet of points, whether
     * they're oriented collinear, clockwise or counterclockwise to each other.
     * Calculate the slopes of lines formed by a,b and b,c using the cross product.
     */
    private int getOrientation(Point a, Point b, Point c) {
        // Calculate both slopes using cross product
        int slope1 = (b.y - a.y) * (c.x - b.x);
        int slope2 = (c.y - b.y) * (b.x - a.x);

        if (slope1 > slope2) {
            return 1; // Clockwise
        } else if (slope1 < slope2) {
            return 2; // Counterclockwise
        } else {
            return 0; // Collinear
        }
    }

    // Calculate the Manhattan / squared distance between 2 points
    private int getDistance(Point a, Point b) {
        return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
    }

    // Remove duplicate points by converting the array into a LinkedHashSet, which
    // can't have duplicates
    private Point[] removeDuplicates(Point[] points) {

        LinkedHashSet<Point> uniquePoints = new LinkedHashSet<>();

        for (int i = 0; i < points.length; i++) {
            uniquePoints.add(points[i]);
        }

        return uniquePoints.toArray(new Point[0]);
    }

    @Override
    public boolean checkCollision(BBActor otherActor) {

        if (otherActor.isCH()) {
            return isTouching(otherActor.getClass()); // TODO: Collision detection using SAT()
        } else if (otherActor.isAABB() || otherActor.isOBB()) {
            return false; // TODO: Later to avoid collision problem between isTouching() & SAT()
        }
        return false;
    }
}
