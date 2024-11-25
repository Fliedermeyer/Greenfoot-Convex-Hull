package me.fliedermeyer.actors;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import java.awt.Point;

public class CHAsteroid extends CHActor {

    private GreenfootImage image;

    public CHAsteroid() { // Constructor
        image = new GreenfootImage("Asteroid.png"); // Set the image of the object
        setImage(image);
    }

    public void act() {
        setLocation(getX(), getY() + 2);

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
                new Point(39, 11),
                new Point(21, 14),
                new Point(9, 23),
                new Point(6, 42),
                new Point(9, 60),
                new Point(19, 71),
                new Point(33, 79),
                new Point(54, 80),
                new Point(67, 71),
                new Point(79, 63),
                new Point(84, 45),
                new Point(82, 34),
                new Point(73, 20),
                new Point(69, 13),
                new Point(53, 17),
        };
    }

}
