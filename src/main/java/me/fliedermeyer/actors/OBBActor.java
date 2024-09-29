package me.fliedermeyer.actors;

// Every actor that inherits from this class uses AABB collision detection

public class OBBActor extends BoundingBoxActor {

    public boolean checkCollision(BoundingBoxActor other) {
        return isTouching(other.getClass()); // Collision detection using Greenfoots isTouching() method
    }
}
