package me.fliedermeyer.actors;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

public class OBBAsteroid extends OBBActor {

    private GreenfootImage image;

    public OBBAsteroid() {
        image = new GreenfootImage("Square.png");
        setImage(image);
    }

    public void act() {
        setLocation(getX(), getY() + 5);
        setRotation(45);

        if (checkCollision(new AABBRocket())) {
            Greenfoot.stop();
        }
    }
}
