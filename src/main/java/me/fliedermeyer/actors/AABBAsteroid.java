package me.fliedermeyer.actors;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

public class AABBAsteroid extends AABBActor {

    private GreenfootImage image;

    public AABBAsteroid() {
        image = new GreenfootImage("AABB.png");
        setImage(image);
    }

    public void act() {
        if (isTouching(AABBRocket.class)) {
            Greenfoot.stop();
        }

        setLocation(getX(), getY() + 5);
    }

}
