package me.fliedermeyer.actors;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import java.awt.Point;

public class CHAsteroid extends CHActor {

    private GreenfootImage image;

    public CHAsteroid() { // Constructor
        image = new GreenfootImage("Triangle.png"); // Set the image of the object
        setImage(image);

        calculateConvexHull(getPoints(), getPoints().length);
    }

    public void act() {
        setLocation(getX(), getY() + 5);

        if (checkCollision(new AABBRocket())) {
            Greenfoot.stop();
        }
    }

    // Implementation of abstract method getPoints() to define all points of the
    // objects
    @Override
    protected Point[] getPoints() {
        return new Point[] {
                new Point(0, 3),
                new Point(1, 1),
                new Point(2, 2),
                new Point(4, 4),
                new Point(0, 0),
                new Point(1, 2),
                new Point(3, 1),
                new Point(3, 3),
        };
    }

}
