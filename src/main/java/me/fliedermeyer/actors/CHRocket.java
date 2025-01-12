package me.fliedermeyer.actors;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

public class CHRocket extends CHActor {

    private GreenfootImage image;

    public CHRocket() { // Constructor for image
        image = new GreenfootImage("Rocket.png");
        setImage(image);
    }

    public void act() {
        for (CHAsteroid asteroid : getWorld().getObjects(CHAsteroid.class)) {
            if (checkCollision(asteroid)) {
                Greenfoot.stop(); // Game stops when collision is detected
                System.out.println("Game stopped at " + getX() + " | " + getY());
            }
        }

        move();
    }

    private void move() { // Moving the rocket with the keys W,A,S,D
        if (Greenfoot.isKeyDown("W")) {
            setLocation(getX(), getY() - 5);
        }
        if (Greenfoot.isKeyDown("A")) {
            setLocation(getX() - 5, getY());
        }
        if (Greenfoot.isKeyDown("S")) {
            setLocation(getX(), getY() + 5);
        }
        if (Greenfoot.isKeyDown("D")) {
            setLocation(getX() + 5, getY());
        }
    }

    // Implementation of abstract method getPoints() to define all points of the
    // objects
    // -> Points of the edges of the rocket taken from the image
    @Override
    protected Point[] getPoints() {
        return new Point[] {
                new Point(42, 0),
                new Point(36, 11),
                new Point(32, 25),
                new Point(31, 42),
                new Point(23, 51),
                new Point(29, 67),
                new Point(34, 62),
                new Point(31, 70),
                new Point(31, 79),
                new Point(36, 75),
                new Point(38, 88),
                new Point(44, 79),
                new Point(49, 87),
                new Point(51, 75),
                new Point(57, 80),
                new Point(57, 69),
                new Point(56, 61),
                new Point(62, 67),
                new Point(69, 51),
                new Point(61, 43),
                new Point(59, 27),
                new Point(53, 13),
        };
    }
}
