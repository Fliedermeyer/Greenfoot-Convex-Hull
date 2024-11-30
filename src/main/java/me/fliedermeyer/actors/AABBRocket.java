package me.fliedermeyer.actors;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

// Rocket using an AABB

public class AABBRocket extends AABBActor {

    private GreenfootImage image;

    public AABBRocket() { // Constructor for image
        image = new GreenfootImage("Rocket.png");
        setImage(image);
    }

    public void act() {
        if (checkCollision(new AABBAsteroid())) {
            System.out.println("Game stopped at " + getX() + " | " + getY());
            Greenfoot.stop(); // Game stops when collision is detected
        }
        if (checkCollision(new OBBAsteroid())) {
            System.out.println("Game stopped at " + getX() + " | " + getY());
            Greenfoot.stop(); // Game stops when collision is detected
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
}
