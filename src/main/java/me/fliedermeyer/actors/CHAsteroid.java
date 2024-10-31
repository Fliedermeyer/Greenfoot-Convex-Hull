package me.fliedermeyer.actors;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import java.awt.Point;

public class CHAsteroid extends CHActor {

    private GreenfootImage image;

    public CHAsteroid() { // Constructor
        image = new GreenfootImage("Triangle.png"); // Set the image of the object
        setImage(image);
    }

    public void act() {
        setLocation(getX(), getY() + 5);

        for (AABBRocket rocket : getWorld().getObjects(AABBRocket.class)) {
            if (checkCollision(rocket)) {
                Greenfoot.stop();
                System.out.println("Game stopped at " + getX() + " | " + getY());
            }
        }
    }

    // Implementation of abstract method getPoints() to define all points of the
    // objects
    @Override
    protected Point[] getPoints() {
        return new Point[] {
                new Point(45, 0),
                new Point(0, 90),
                new Point(90, 90)
        };
    }

}
