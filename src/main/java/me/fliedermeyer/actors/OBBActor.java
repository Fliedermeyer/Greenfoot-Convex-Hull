package me.fliedermeyer.actors;

// Every actor that inherits from this class uses AABB collision detection

public class OBBActor extends BBActor {

    @Override
    public boolean checkCollision(BBActor otherActor) {

        if (otherActor instanceof AABBActor || otherActor instanceof OBBActor) {
            return isTouching(otherActor.getClass()); // Collision detection using Greenfoots isTouching() method
        }
        // TODO: Maybe throw Exception
        System.out.println("Collision detection between different kinds of bounding boxes does not work");
        return false;
    }
}
