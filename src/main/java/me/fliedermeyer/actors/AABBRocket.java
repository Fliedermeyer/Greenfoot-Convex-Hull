package me.fliedermeyer.actors;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

public class AABBRocket extends AABBActor {

    private GreenfootImage image;

    public AABBRocket() {
        image = new GreenfootImage("Triangle.png");
        setImage(image);
    }

    public void act() {
        if (isTouching(AABBAsteroid.class)) {
            Greenfoot.stop();
        }
        move();
    }

    public void move() {
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
}
