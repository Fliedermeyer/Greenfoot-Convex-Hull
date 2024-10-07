package me.fliedermeyer.worlds;

import greenfoot.World;
import me.fliedermeyer.actors.AABBAsteroid;
import me.fliedermeyer.actors.AABBRocket;
import me.fliedermeyer.actors.CHAsteroid;
import me.fliedermeyer.actors.OBBAsteroid;

// Class for the game world
public class MyWorld extends World {

	// Constructor for objects of MyWorld class
	public MyWorld() {
		super(1000, 800, 1); // Sets the boundaries of the world

		AABBRocket rocket = new AABBRocket();
		addObject(rocket, getWidth() / 2, 800 - 45);

		AABBAsteroid a1 = new AABBAsteroid();
		addObject(a1, getWidth() / 2, 0);

		OBBAsteroid a2 = new OBBAsteroid();
		addObject(a2, getWidth() / 2 + 150, 0);

		CHAsteroid a3 = new CHAsteroid();
		addObject(a3, getWidth() / 2 - 150, 0);

	}
}
