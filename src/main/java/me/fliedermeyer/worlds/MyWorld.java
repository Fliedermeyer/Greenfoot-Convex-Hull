package me.fliedermeyer.worlds;

import greenfoot.World;
import me.fliedermeyer.actors.AABBAsteroid;
import me.fliedermeyer.actors.AABBRocket;
import me.fliedermeyer.actors.OBBAsteroid;

public class MyWorld extends World {

	public MyWorld() { // Constructor for objects of class MyWorld
		super(1000, 800, 1);

		AABBRocket rocket = new AABBRocket();
		addObject(rocket, getWidth() / 2, 800 - 45);

		AABBAsteroid a1 = new AABBAsteroid();
		addObject(a1, getWidth() / 2, 0);

		OBBAsteroid a2 = new OBBAsteroid();
		addObject(a2, getWidth() / 2 + 150, 0);
	}
}
