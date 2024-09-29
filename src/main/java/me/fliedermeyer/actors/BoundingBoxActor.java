package me.fliedermeyer.actors;

import greenfoot.Actor;

public abstract class BoundingBoxActor extends Actor {

    public abstract boolean checkCollision(BoundingBoxActor other);
}
