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
        setLocation(getX(), getY() + 5); // moving downwards
        setRotation(45); // rotated by 45 degrees

        if (checkCollision(new AABBRocket())) {
            Greenfoot.stop(); // game stops when collision is detected
        }
    }
}
