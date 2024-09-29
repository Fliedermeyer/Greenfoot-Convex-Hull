package me.fliedermeyer.actors;

public class AABBActor extends BoundingBoxActor {

    public boolean checkCollision(BoundingBoxActor other) {

        return isTouching(other.getClass());
    }
}
