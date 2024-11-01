package me.fliedermeyer.actors;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import java.awt.Point;

public class CHRocket extends CHActor {

    private GreenfootImage image;

    public CHRocket() { // Constructor for image
        image = new GreenfootImage("Diamond.png");
        setImage(image);
    }

    public void act() {
        for (CHAsteroid asteroid : getWorld().getObjects(CHAsteroid.class)) {
            if (checkCollision(asteroid)) {
                Greenfoot.stop();
                System.out.println("Game stopped at " + getX() + " | " + getY());
            }
        }

        move();
    }

    public void move() { // Moving the rocket with the keys W,A,S,D
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

    @Override
    protected Point[] getPoints() {
        return new Point[] {
                new Point(0, 45),
                new Point(90, 45),
                new Point(45, 0),
                new Point(45, 90)
        };
    }
}
