package me.fliedermeyer.actors;

public class OBBActor extends BoundingBoxActor {

    public boolean checkCollision(BoundingBoxActor other) {

        return isTouching(other.getClass());
    }
}
