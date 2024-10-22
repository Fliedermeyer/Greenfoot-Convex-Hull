package me.fliedermeyer.actors;

// Every actor that inherits from this class uses AABB collision detection

public class AABBActor extends BBActor {

    @Override
    public boolean checkCollision(BBActor otherActor) {

        if (otherActor.isAABB() || otherActor.isOBB()) {
            return isTouching(otherActor.getClass()); // Collision detection using Greenfoots isTouching() method
        } else if (otherActor.isCH()) {
            return false; // TODO: Later to avoid collision problems between isTouching() & SAT()
        }
        return false;

    }
}
