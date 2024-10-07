package me.fliedermeyer.actors;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

// Asteroid using an AABB

public class AABBAsteroid extends AABBActor {

    private GreenfootImage image;

    public AABBAsteroid() { // Constructor for image
        image = new GreenfootImage("Square.png");
        setImage(image);
    }

    public void act() {
        setLocation(getX(), getY() + 5); // Moving downwards

        if (checkCollision(new AABBRocket())) {
            Greenfoot.stop(); // Game stops when collision is detected
        }
    }

}
