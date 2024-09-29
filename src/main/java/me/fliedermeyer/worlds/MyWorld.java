package me.fliedermeyer.worlds;

import greenfoot.World;
import me.fliedermeyer.actors.AABBAsteroid;
import me.fliedermeyer.actors.AABBRocket;

public class MyWorld extends World {

	// Constructor for objects of class MyWorld.

	public MyWorld() {
		super(1000, 800, 1);

		AABBRocket rocket = new AABBRocket();
		addObject(rocket, getWidth() / 2, 800 - 45);

		AABBAsteroid a1 = new AABBAsteroid();
		addObject(a1, getWidth() / 2, 0);
	}
}
