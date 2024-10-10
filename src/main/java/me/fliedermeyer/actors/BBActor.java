package me.fliedermeyer.actors;

import greenfoot.Actor;

// Abstract base class for all types of bounding boxes
// Each subclass must implement its own version of collision detection

public abstract class BBActor extends Actor {

    // Abstract method for collision detection to be implemented
    public abstract boolean checkCollision(BBActor otherActor);

    // Helper methods to dinstinguish types of bounding boxes
    protected boolean isAABB() {
        return this instanceof AABBActor;
    }

    protected boolean isOBB() {
        return this instanceof OBBActor;
    }

    protected boolean isCH() {
        return this instanceof CHActor;
    }
}
