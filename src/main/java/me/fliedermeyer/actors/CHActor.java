package me.fliedermeyer.actors;

import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Stack;

// TODO: Point class to my own private class
// TODO: Optimize SAT collision

public abstract class CHActor extends BBActor {

    // Every subclass needs to implement this method to provide an array of Point
    // values representing locations (x,y)
    // -> Every convex hull object must deliver points to calculate the convex hull
    protected abstract Point[] getPoints();

    private Point[] convexHull;

    public CHActor() {
        this.convexHull = calculateConvexHull();

        // Debugging: Print all points of the convex hull
        for (int i = 0; i < convexHull.length - 1; i++) {
            System.out.println(
                    getClass().getSimpleName() + " Convex hull point: " + convexHull[i].x + ", " + convexHull[i].y);
        }
    }

    protected Point[] getConvexHull() {
        return convexHull;
    }

    // Calculate the complete convex hull by processing points triplet by triplet
    protected Point[] calculateConvexHull() {

        Point[] points = getPoints();
        points = removeDuplicates(points);

        int numOfPts = points.length; // Number of points in the array

        // Convex hull cannot be constructed if there are less than 3 points
        // -> In that case it's only a point or a line -> Return empty array
        if (numOfPts < 3) {
            System.out.println("Convex Hull cannot be constructed with less than 3 points");
            return new Point[0];
        }

        int minY = 0; // Index of the lowest point

        // Find the index of lowest point
        // -> If two points have the same minimum y-value, take the one with the larger
        // x-value
        for (int i = 1; i < numOfPts; i++) {
            if (points[i].y < points[minY].y) {
                minY = i;
            } else if (points[i].y == points[minY].y && points[i].x > points[minY].x) {
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
                    i--; // Process the current point again
                    break;
                case 0: // Collinear
                    convexHull.push(front); // Add the current point to the hull
                    break;
            }
        }

        convexHull.push(points[0]); // Close the hull by adding the starting point again

        return convexHull.toArray(new Point[0]);
    }

    /*
     * Calculate the orientation of the middle triplet of points, whether
     * they're oriented collinear, clockwise or counterclockwise
     */
    private static int getOrientation(Point a, Point b, Point c) {
        int orientation = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);

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
        return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
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

    @Override
    public boolean checkCollision(BBActor otherActor) {
        if (otherActor instanceof CHActor otherCHActor) {
            Point[] thisHull = getMovingConvexHull();
            Point[] otherHull = otherCHActor.getMovingConvexHull();

            // Check for a separating axis between this actor and the other one
            // -> If a separating axis between both convex hulls can be drawn, then there is
            // no collision
            if (hasSeparatingAxis(thisHull, otherHull)) {
                return false;
            }

            // No separating axis can be drawn -> objects must collide
            return true;
        } else if (otherActor instanceof AABBActor || otherActor instanceof OBBActor) {
            return isTouching(otherActor.getClass());
        } else {
            return false;
        }

    }

    private boolean hasSeparatingAxis(Point[] hullA, Point[] hullB) {
        // Check each edge of hullA to draw potential separating axes
        for (int i = 0; i < hullA.length; i++) {
            Point p1 = hullA[i];
            Point p2 = hullA[(i + 1) % hullA.length];

            // Calculate the orthogonal (normal) vector to the current edge as a
            // potential separating axis
            Point axis = new Point(-(p2.y - p1.y), p2.x - p1.x);

            // Project both hulls onto the orthogonal vector (axis)
            int[] projectionA = projectHullonAxis(hullA, axis);
            int[] projectionB = projectHullonAxis(hullB, axis);

            // Check if there is a gap between the projections on this axis
            // -> If a gap is found, this is the separating axis -> no collision on this
            // axis
            if (projectionA[1] < projectionB[0] || projectionB[1] < projectionA[0]) {
                return true;
            }
        }
        // No separating axes at all -> objects must collide
        return false;
    }

    private static int[] projectHullonAxis(Point[] hull, Point axis) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        // Project each point of the hull onto the axis and find the min and max values
        for (int i = 0; i < hull.length; i++) {
            int projection = hull[i].x * axis.x + hull[i].y * axis.y;

            if (projection < min) {
                min = projection;
            }
            if (projection > max) {
                max = projection;
            }
        }

        // Return the projection range (min -> max) on this axis
        return new int[] { min, max };
    }

    protected Point[] getMovingConvexHull() {
        Point[] originalHull = getConvexHull();
        Point[] movingHull = new Point[originalHull.length];

        // Move each point of the original convex hull to the actor's current position
        // in the world
        for (int i = 0; i < originalHull.length; i++) {
            movingHull[i] = new Point(originalHull[i].x + getX(), originalHull[i].y + getY());
        }

        return movingHull;
    }
}