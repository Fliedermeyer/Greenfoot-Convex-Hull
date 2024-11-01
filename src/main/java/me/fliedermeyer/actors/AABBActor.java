package me.fliedermeyer.actors;

// Every actor that inherits from this class uses Axis Aligned Bounding Box (AABB) collision detection

public class AABBActor extends BBActor {

    @Override
    public boolean checkCollision(BBActor otherActor) {

        if (otherActor instanceof AABBActor || otherActor instanceof OBBActor || otherActor instanceof CHActor) {
            return isTouching(otherActor.getClass()); // Collision detection using Greenfoots isTouching() method
        } else {
            return false;
        }
    }
}
