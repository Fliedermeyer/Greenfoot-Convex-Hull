package me.fliedermeyer.actors;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public abstract class CHActor extends BBActor {

    // Every subclass needs to implement this method using Point values representing
    // a location x,y in coordinate space
    // -> Every convex hull object must deliver points to calculate the convex hull
    protected abstract Point[] getPoints();

    // Calculate the complete convex hull triplet by triplet
    protected void calculateConvexHull(Point[] points) { // ToDo: Void to be changed later

        points = getPoints();
        points = removeDuplicates(points);

        int numOfPts = points.length; // Number of points in the array

        // Convex Hull isn't constructable if there are less than 3 points, because in
        // that case it's only a point or a line -> Exit the calculation
        if (numOfPts < 3) {
            return;
        }

        ArrayList<Point> convexHull = new ArrayList<Point>();

        int minY = 0; // Save the lowest point (y-coordinate)

        // Find the lowest point (y-coordinate)
        for (int i = 1; i < numOfPts; i++) {
            if (points[i].y < points[minY].y) {
                minY = i;
            }

            // If 2 points have the same minimum y-value, take the point with the larger
            // x-value
            else if (points[i].y == points[minY].y && points[i].x < points[minY].x) {
                minY = i;
            }

            // ToDo: Calculating only the essential points of the CH
        }

        int current = minY; // Initializes the first / current point to be the lowest point
        int next;

        // Start from the lowest point and move counterclockwise until reaching the
        // start point again
        do {
            convexHull.add(points[current]); // Add the current point from the loop to the convex hull

            /*
             * Search for the next point so that the orientation of the points looks like
             * this [current -> potential -> next]; Watch the last visited most
             * counterclockwise point "next"; If any point "potential" in between those 2
             * points is more counterclockwise
             * than "next", update "next" to the value of "potential";
             */
            next = (current + 1) % numOfPts;

            for (int potential = 0; potential < numOfPts; potential++) {
                if (orientation(points[current], points[potential], points[next]) == 2) {
                    next = potential;
                }
            }

            current = next;

        } while (current != minY); // Run this loop while not reaching the start point again

        for (int i = 0; i < convexHull.size(); i++) { // ToDo: Convex hull needs to be implemented
            System.out.println("(" + convexHull.get(i).x + "|" + convexHull.get(i).y + ")");
        }
    }

    /*
     * Calculation of the orientation of the current triplet of points, whether
     * they're oriented collinear, clockwise or counterclockwise to each other.
     * Calculate the slopes of lines formed by a,b and b,c using the cross product.
     */
    private int orientation(Point a, Point b, Point c) {
        int slope1 = (b.y - a.y) * (c.x - b.x);
        int slope2 = (c.y - b.y) * (b.x - a.x);

        if (slope1 == slope2) {
            return 0; // Collinear
        } else if (slope1 > slope2) {
            return 1; // Clockwise
        } else {
            return 2; // Counterclockwise
        }
    }

    // Remove duplicate points by converting the array into a LinkedHashSet, which
    // cannot have duplicates
    protected Point[] removeDuplicates(Point[] points) {

        LinkedHashSet<Point> uniquePoints = new LinkedHashSet<Point>();

        for (int i = 0; i < points.length; i++) {
            uniquePoints.add(points[i]);
        }

        return uniquePoints.toArray(new Point[0]);
    }

    @Override
    public boolean checkCollision(BBActor otherActor) {

        if (otherActor.isCH()) {
            return isTouching(otherActor.getClass()); // ToDo: Collision detection using SAT()
        } else if (otherActor.isAABB() || otherActor.isOBB()) {
            return false; // ToDo: Later to avoid collision problem between isTouching() & SAT()
        }
        return false;
    }
}
