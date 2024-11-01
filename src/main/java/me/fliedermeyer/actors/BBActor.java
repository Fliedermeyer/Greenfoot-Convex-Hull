package me.fliedermeyer.actors;

import greenfoot.Actor;

// Abstract base class for all types of bounding boxes (BB)

public abstract class BBActor extends Actor {

    // Abstract method for the collision detection with another actor
    // Subclasses must implement their own version of the collision detection method
    public abstract boolean checkCollision(BBActor otherActor);
}
