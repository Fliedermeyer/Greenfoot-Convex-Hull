package me.fliedermeyer.actors;

// Class to store points
public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getPointX() {
        return x;
    }

    public int getPointY() {
        return y;
    }

    /*
     * Setters currently not in use
     * 
     * public void setPointX(int x) {
     * this.x = x;
     * }
     * 
     * public void setPointY(int y) {
     * this.y = y;
     * }
     */
}
