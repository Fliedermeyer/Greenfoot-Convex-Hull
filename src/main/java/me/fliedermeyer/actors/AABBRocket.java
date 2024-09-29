package me.fliedermeyer.actors;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

// Rocket using an AABB

public class AABBRocket extends AABBActor {

    private GreenfootImage image;

    public AABBRocket() { // Constructor for image
        image = new GreenfootImage("Triangle.png");
        setImage(image);
    }

    public void act() {
        if (checkCollision(new AABBAsteroid())) {
            Greenfoot.stop(); // game stops when collision is detected
        }
        move();
    }

    public void move() { // moving the rocket with the keys W,A,S,D
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
