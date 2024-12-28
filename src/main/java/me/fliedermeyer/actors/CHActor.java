package me.fliedermeyer.actors;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Stack;

public abstract class CHActor extends BBActor {

    // Every subclass needs to implement this method to provide an array of Point
    // values representing locations (x,y)
    // -> Every convex hull object must deliver points to calculate the convex hull
    protected abstract Point[] getPoints();

    private Point[] convexHull;


    public CHActor() {
        this.convexHull = calculateConvexHull();

        // Debugging: Output all points of the convex hull
        for (int i = 0; i < convexHull.length - 1; i++) {
            System.out.println(
                    getClass().getSimpleName() + " Convex hull point: " + convexHull[i].getPointX() + ", "
                            + convexHull[i].getPointY());
        }
    }

    private Point[] getConvexHull() {
        return convexHull;
    }

    // Calculate the convex hull using the Graham Scan algorithm
    protected Point[] calculateConvexHull() {

        Point[] points = getPoints(); // Get points from the subclass
        points = removeDuplicates(points);

        int numOfPts = points.length; // Number of points in the array

        // Convex hull cannot be constructed with less than 3 points
        if (numOfPts < 3) {
            System.out.println("Convex Hull cannot be constructed with less than 3 points");
            return new Point[0];
        }

        // Find the index of the lowest point
        // -> If two points have the same minimum y-value, take the one with the larger
        // x-value
        int minY = 0;
        for (int i = 1; i < numOfPts; i++) {
            if (points[i].getPointY() < points[minY].getPointY()) {
                minY = i;
            } else if (points[i].getPointY() == points[minY].getPointY()
                    && points[i].getPointX() > points[minY].getPointX()) {
                minY = i;
            }
        }

        final Point base = points[minY];

        // Sort points based on their orientation / angle relative to the base point
        Arrays.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {
                int orientation = getOrientation(base, a, b);

                // If points are collinear, sort them by the nearest distance from the base
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
                    i--; // Retry with a new middle point
                    break;
                case 0: // Collinear
                    convexHull.push(front); // Add the current point to the hull
                    break;
            }
        }

        // Close the hull by adding the starting point again
        convexHull.push(points[0]);

        return convexHull.toArray(new Point[0]);
    }

    // Calculate the orientation of the middle triplet of points, whether
    // they're oriented collinear, clockwise or counterclockwise
    private static int getOrientation(Point a, Point b, Point c) {
        int orientation = (b.getPointX() - a.getPointX()) * (c.getPointY() - a.getPointY())
                - (b.getPointY() - a.getPointY()) * (c.getPointX() - a.getPointX());

        if (orientation == 0) {
            return 0; // Collinear
        } else if (orientation < 0) {
            return 1; // Clockwise
        } else {
            return 2; // Counterclockwise
        }
    }

    // Calculate the squared distance between 2 points
    private static int getDistance(Point a, Point b) {
        return (a.getPointX() - b.getPointX()) * (a.getPointX() - b.getPointX())
                + (a.getPointY() - b.getPointY()) * (a.getPointY() - b.getPointY());
    }

    // Remove duplicate points by converting the array into a LinkedHashSet, which
    // does not allow duplicates
    private static Point[] removeDuplicates(Point[] points) {

        LinkedHashSet<Point> uniquePoints = new LinkedHashSet<>();

        for (int i = 0; i < points.length; i++) {
            uniquePoints.add(points[i]);
        }

        return uniquePoints.toArray(new Point[0]);
    }

    // Check collision between this actor and another
    @Override
    public boolean checkCollision(BBActor otherActor) {
        if (otherActor instanceof CHActor otherCHActor) {

            Point[] thisHull = getMovingConvexHull();
            Point[] otherHull = otherCHActor.getMovingConvexHull();

            // Check for a separating axis between this actor and the other one
            // -> If a separating axis between both convex hulls can be drawn, then there is
            // no collision
            if (hasSeparatingAxis(thisHull, otherHull)) {
                System.out.println("Hulls don't overlap because of SAT");
                return false;
            }

            // Bounding box overlaps, no separating axis -> hulls must overlap
            System.out.println("Hulls overlap");
            return true;

        } else {
            return false; // No collision check for non-CHActors
        }

    }

    // Check for a separating axis using the Separating Axis Theorem
    private boolean hasSeparatingAxis(Point[] hullA, Point[] hullB) {
        // Check each edge of hullA to draw potential separating axes
        for (int i = 0; i < hullA.length; i++) {
            // Calculate the orthogonal (normal) vector to the current edge of hullA as a
            // potential separating axis
            Point axis = getSeparatingAxis(hullA, i);

            // Check projections for both hulls
            if (hasGapBetweenProjections(hullA, hullB, axis)) {
                return true; // Found one separating axis -> early exit the algoritm
            }
        }

        // Check each edge of hullB to draw potential separating axes
        for (int i = 0; i < hullB.length; i++) {
            // Calculate the orthogonal (normal) vector to the current edge of hullB as a
            // potential separating axis
            Point axis = getSeparatingAxis(hullB, i);

            // Check projections for both hulls
            if (hasGapBetweenProjections(hullA, hullB, axis)) {
                return true; // Found one separating axis -> early exit the algoritm
            }
        }

        // No separating axis -> objects must collide
        return false;
    }

    // Calculate the separating axis (normal vector) for a given edge
    private Point getSeparatingAxis(Point[] hull, int i) {
        Point p1 = hull[i];
        Point p2 = hull[(i + 1) % hull.length];
        return new Point(-(p2.getPointY() - p1.getPointY()), p2.getPointX() - p1.getPointX());
    }

    // Check if there is a gap between the projections of the two hulls on a given
    // axis
    private boolean hasGapBetweenProjections(Point[] hullA, Point[] hullB, Point axis) {
        int[] projectionA = projectHullonAxis(hullA, axis);
        int[] projectionB = projectHullonAxis(hullB, axis);

        // Check if there is a gap between the projections on this axis
        // -> If a gap is found, this is the separating axis -> no collision on this
        // axis
        return projectionA[1] < projectionB[0] || projectionB[1] < projectionA[0];
    }

    // Project the convex hull onto a given axis and returns the min and max
    // projection
    private static int[] projectHullonAxis(Point[] hull, Point axis) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < hull.length; i++) {
            int projection = hull[i].getPointX() * axis.getPointX() + hull[i].getPointY() * axis.getPointY();

            if (projection < min) {
                min = projection;
            }
            if (projection > max) {
                max = projection;
            }
        }

        return new int[] { min, max };
    }

    // Return the convex hull adjusted to the actor's current position
    private Point[] getMovingConvexHull() {
        Point[] originalHull = getConvexHull();
        Point[] movingHull = new Point[originalHull.length];

        for (int i = 0; i < originalHull.length; i++) {
            movingHull[i] = new Point(originalHull[i].getPointX() + getX(), originalHull[i].getPointY() + getY());
        }

        return movingHull;
    }

}