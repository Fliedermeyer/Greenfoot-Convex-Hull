package me.fliedermeyer.worlds;

import greenfoot.World;
import me.fliedermeyer.actors.AABBAsteroid;
import me.fliedermeyer.actors.AABBRocket;
import me.fliedermeyer.actors.CHAsteroid;
import me.fliedermeyer.actors.CHRocket;
import me.fliedermeyer.actors.OBBAsteroid;

// Class for the game world
public class MyWorld extends World {

	// Constructor for objects of MyWorld class
	public MyWorld() {
		super(1000, 700, 1); // Sets the boundaries of the world

		//AABBRocket aabbRocket = new AABBRocket();
		//addObject(aabbRocket, getWidth() / 2, getHeight() - 45);

		addObject(new AABBAsteroid(), getWidth() / 2, 0);
		
		addObject(new AABBAsteroid(), getWidth() / 2 + 300, 0);

		addObject(new OBBAsteroid(), getWidth() / 2 + 150, 0);

		addObject(new CHAsteroid(), getWidth() / 2 - 150, 0);

		addObject(new CHAsteroid(), getWidth() / 2 - 250, 0);

		addObject(new CHRocket(), getWidth() / 2, getHeight() - 45);

	}
}
