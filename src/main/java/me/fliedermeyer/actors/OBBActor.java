package me.fliedermeyer.actors;

// Every actor that inherits from this class uses AABB collision detection

public class OBBActor extends BBActor {

    @Override
    public boolean checkCollision(BBActor otherActor) {
        return isTouching(otherActor.getClass()); // Collision detection using Greenfoots isTouching() method
    }
}
