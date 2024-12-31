package me.fliedermeyer.worlds;

import greenfoot.World;
import me.fliedermeyer.actors.CHActor;
import me.fliedermeyer.actors.CHAsteroid;
import me.fliedermeyer.actors.CHRocket;

// Class for the game world
public class MyWorld extends World {

    // Constructor for objects of MyWorld class
    public MyWorld() {
        super(1000, 700, 1); // Sets the boundaries of the world

        CHActor rocket = new CHRocket();
        CHActor asteroid = new CHAsteroid();

        addObject(rocket, getWidth() / 2, getHeight());
        addObject(asteroid, getWidth() / 2, 0);

        testCollision(rocket, asteroid);
    }

    private void testCollision(CHActor rocket, CHActor asteroid) {
        long totalDuration = 0;

        for (int i = 0; i < 1000; i++) {
            // Change the location of the actors every iteration
            asteroid.setLocation(getWidth() / 2 - 200, i);
            rocket.setLocation(getWidth() / 2 + 200, -i);

            // Measure the time it takes to check for 1 collision
            long startTime = System.nanoTime();
            rocket.checkCollision(asteroid);
            long endTime = System.nanoTime();
            totalDuration = totalDuration + (endTime - startTime);
			System.out.println("1 collision check: " + (endTime - startTime) + " nanoseconds");
        }

        long averageDuration = totalDuration / 1000;
        System.out.println("Average duration of 1 collision check: " + averageDuration + " nanoseconds");
    }
}
