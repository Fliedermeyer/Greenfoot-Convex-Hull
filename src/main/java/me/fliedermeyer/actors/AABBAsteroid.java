package me.fliedermeyer.actors;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

public class AABBAsteroid extends AABBActor {

    private GreenfootImage image;

    public AABBAsteroid() {
        image = new GreenfootImage("Square.png");
        setImage(image);
    }

    public void act() {
        setLocation(getX(), getY() + 5);

        if (checkCollision(new AABBRocket())) {
            Greenfoot.stop();
        }
    }

}
