package me.fliedermeyer.worlds;

import greenfoot.World;
import me.fliedermeyer.actors.AABBAsteroid;
import me.fliedermeyer.actors.AABBRocket;
import me.fliedermeyer.actors.CHAsteroid;
import me.fliedermeyer.actors.CHRocket;
import me.fliedermeyer.actors.OBBAsteroid;
import javax.swing.JOptionPane;

// Class for the game world
public class MyWorld extends World {

	// Constructor for objects of MyWorld class
	public MyWorld() {
		super(1000, 700, 1); // Sets the boundaries of the world
	
		String input = JOptionPane.showInputDialog(null, "Do you want to test axis aligned bounding box collision [1] or convex hull collision [2]?");

		if (input.equals("1")){
			addObject(new AABBRocket(), getWidth() / 2, getHeight() - 45);

			addObject(new AABBAsteroid(), getWidth() / 2, 0);
			
			addObject(new AABBAsteroid(), getWidth() / 2 + 300, 0);
	
			addObject(new OBBAsteroid(), getWidth() / 2 + 150, 0);
		} else if (input.equals("2")){
			addObject(new CHAsteroid(), getWidth() / 2 - 150, 0);

			addObject(new CHAsteroid(), getWidth() / 2 - 300, 0);

			addObject(new CHRocket(), getWidth() / 2, getHeight() - 45);
		} else {
			System.out.println("Unvalid input; Make sure you either type in 1 or 2");
		}
		

	}
}
