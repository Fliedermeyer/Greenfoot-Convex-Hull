package me.fliedermeyer.actors;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

public class CHObject extends CHActor {

    private GreenfootImage image;

    public CHObject() { // Constructor for image
        // image = new GreenfootImage("");
        // setImage(image);
    }

    public void act() {
    }

    // Implementation of abstract method getPoints() to define all points of the
    // objects
    // -> Points of the edges of the rocket taken from the image
    @Override
    protected Point[] getPoints() {
        return new Point[] {
                // new Point(0, 0),
        };
    }
}
