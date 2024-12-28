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

		int numTests = 100;
		long totalTime = 0;

		CHActor rocket = new CHRocket();
		CHActor asteroid = new CHAsteroid();

		addObject(rocket, getWidth() / 2, getHeight() - 45);
		addObject(asteroid, getWidth() / 2, 0);

		for (int i = 0; i < numTests; i++) {
			long startTime = System.nanoTime();
			rocket.checkCollision(asteroid);
			long endTime = System.nanoTime();
			System.out.println("Single test duration calling a method (ns): " + (endTime - startTime));
			totalTime += (endTime - startTime);
		}

		System.out.println("Average collision check duration calling a method (ns): " + (totalTime / numTests));
	}
}
