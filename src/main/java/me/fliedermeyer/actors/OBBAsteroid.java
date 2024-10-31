package me.fliedermeyer.actors;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

// Rotated Asteroid using an OBB

public class OBBAsteroid extends OBBActor {

    private GreenfootImage image;

    public OBBAsteroid() { // Constructor for image
        image = new GreenfootImage("Square.png");
        setImage(image);
    }

    public void act() {
        setLocation(getX(), getY() + 5); // Moving downwards
        setRotation(45); // Rotated by 45 degrees

        if (checkCollision(new AABBRocket())) {
            System.out.println("Game stopped at " + getX() + " | " + getY());
            Greenfoot.stop(); // Game stops when collision is detected
        }
    }
}
