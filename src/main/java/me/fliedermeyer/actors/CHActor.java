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

    // Static extreme points of the convex hull -> not changing
    private int staticMinX, staticMinY, staticMaxX, staticMaxY;

    public CHActor() {
        this.convexHull = calculateConvexHull();
        calculateBoundingBox();

        // Debugging: Output all points of the convex hull
        for (int i = 0; i < convexHull.length; i++) {
            System.out.println(
                    getClass().getSimpleName() + " Convex hull point: " + convexHull[i].getPointX() + ", "
                            + convexHull[i].getPointY());
        }
    }

    private Point[] getConvexHull() {
        return convexHull;
    }

    // Calculate the convex hull using the Graham algorithm
    protected Point[] calculateConvexHull() {

        Point[] points = getPoints(); // Get points from the subclass
        points = removeDuplicates(points);

        int numOfPts = points.length; // Number of points in the array

        // Convex hull cant be constructed with less than 3 points
        if (numOfPts < 3) {
            System.out.println("Convex Hull cannot be constructed with less than 3 points");
            return new Point[0];
        }

        // Find the index of the lowest point -> starting point
        // -> If two points have the same minimum y-value, take the one with the larger
        // x-value
        // !!! Invert the y-coordinates because the coordinate system starts at the top
        // left
        int minY = 0;
        for (int i = 1; i < numOfPts; i++) {
            if (points[i].getPointY() > points[minY].getPointY()) {
                minY = i;
            } else if (points[i].getPointY() == points[minY].getPointY()
                    && points[i].getPointX() > points[minY].getPointX()) {
                minY = i;
            }
        }

        final Point base = points[minY];

        // Sort points based on their polarangle relative to the base point / starting
        // point
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

        // Debbuging: Output all sorted points
        for (int i = 0; i < points.length; i++) {
            System.out.println(
                    getClass().getSimpleName() + " Sorted point: " + points[i].getPointX() + ", "
                            + points[i].getPointY());
        }

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
    // !!! Invert the y-coordinates because the coordinate system starts at the top
    // left
    private static int getOrientation(Point a, Point b, Point c) {
        int orientation = (b.getPointX() - a.getPointX()) * (-c.getPointY() - -a.getPointY())
                - (-b.getPointY() - -a.getPointY()) * (c.getPointX() - a.getPointX());

        if (orientation == 0) {
            return 0; // Collinear
        } else if (orientation < 0) {
            return 1; // Clockwise
        } else {
            return 2; // Counterclockwise
        }
    }

    // Calculate the squared distance between 2 points (root calculation would be
    // less efficient)
    private static int getDistance(Point a, Point b) {
        return (a.getPointX() - b.getPointX()) * (a.getPointX() - b.getPointX())
                + (a.getPointY() - b.getPointY()) * (a.getPointY() - b.getPointY());
    }

    // Remove duplicate points by converting the array into a LinkedHashSet, which
    // does not allow duplicates but keeps the order
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
            // Adjust the bounding box values around the convex hull to the actors position
            int thisMinX = staticMinX + getX();
            int thisMaxX = staticMaxX + getX();
            int thisMinY = staticMinY + getY();
            int thisMaxY = staticMaxY + getY();

            int otherMinX = otherCHActor.staticMinX + otherCHActor.getX();
            int otherMaxX = otherCHActor.staticMaxX + otherCHActor.getX();
            int otherMinY = otherCHActor.staticMinY + otherCHActor.getY();
            int otherMaxY = otherCHActor.staticMaxY + otherCHActor.getY();

            // Check if the bounding boxes around the convex hull actors are overlapping
            if (!overlapBoundingBox(thisMinX, thisMaxX, thisMinY, thisMaxY,
                    otherMinX, otherMaxX, otherMinY, otherMaxY)) {
                System.out.println("Hulls don't overlap because of BBOverlap");
                return false;
            }

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
            // Calculate the orthogonal (normal) vector to the current edge of hullA
            Point axis = getNormalVector(hullA, i);

            // Check projections for both hulls
            if (hasGapBetweenProjections(hullA, hullB, axis)) {
                return true; // Found one separating axis -> early exit the algoritm
            }
        }

        // Check each edge of hullB to draw potential separating axes
        for (int i = 0; i < hullB.length; i++) {
            // Calculate the orthogonal (normal) vector to the current edge of hullB
            Point axis = getNormalVector(hullB, i);

            // Check projections for both hulls
            if (hasGapBetweenProjections(hullA, hullB, axis)) {
                return true; // Found one separating axis -> early exit the algoritm
            }
        }

        // No separating axis -> objects must collide
        return false;
    }

    // Calculate the normal vector for a given edge
    private Point getNormalVector(Point[] hull, int i) {
        Point p1 = hull[i];
        Point p2 = hull[(i + 1) % hull.length];
        return new Point(-(p2.getPointY() - p1.getPointY()), p2.getPointX() - p1.getPointX());
    }

    // Check if there is a gap between the projections of the two hulls on a given
    // normal vector
    private boolean hasGapBetweenProjections(Point[] hullA, Point[] hullB, Point axis) {
        int[] projectionA = projectHullonAxis(hullA, axis);
        int[] projectionB = projectHullonAxis(hullB, axis);

        // Check if there is a gap between the projections on this vector
        // -> If a gap is found, this is the separating axis -> no collision on this
        // normal vector
        return projectionA[1] < projectionB[0] || projectionB[1] < projectionA[0];
    }

    // Project the convex hull onto a given vector and return the min and max
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

    // Return the convex hull adjusted to the actors current position
    private Point[] getMovingConvexHull() {
        Point[] originalHull = getConvexHull();
        Point[] movingHull = new Point[originalHull.length];

        for (int i = 0; i < originalHull.length; i++) {
            movingHull[i] = new Point(originalHull[i].getPointX() + getX(), originalHull[i].getPointY() + getY());
        }

        return movingHull;
    }

    // Check if two bounding boxes overlap
    // Inverse > and < because of the coordinate system starting at the top left
    private boolean overlapBoundingBox(int minX1, int maxX1, int minY1, int maxY1,
            int minX2, int maxX2, int minY2, int maxY2) {
        return !(minX1 > maxX2 || maxX1 < minX2 || minY1 > maxY2 || maxY1 < minY2);
    }

    // Calculate the static bounding box based on the convex hulls max and min x & y
    // values -> not changing
    private void calculateBoundingBox() {
        staticMinX = Integer.MAX_VALUE;
        staticMinY = Integer.MAX_VALUE;
        staticMaxX = Integer.MIN_VALUE;
        staticMaxY = Integer.MIN_VALUE;

        for (Point point : convexHull) {
            staticMinX = Math.min(staticMinX, point.getPointX());
            staticMaxX = Math.max(staticMaxX, point.getPointX());
            staticMinY = Math.min(staticMinY, point.getPointY());
            staticMaxY = Math.max(staticMaxY, point.getPointY());
        }
    }
}