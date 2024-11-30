package me.fliedermeyer.actors;

// Every actor that inherits from this class uses Oriented Bounding Box (OBB) collision detection

public class OBBActor extends BBActor {

    @Override
    public boolean checkCollision(BBActor otherActor) {

        if (otherActor instanceof AABBActor || otherActor instanceof OBBActor) {
            return isTouching(otherActor.getClass()); // Collision detection using Greenfoots isTouching() method
        } else {
            return false;
        }
    }
}
