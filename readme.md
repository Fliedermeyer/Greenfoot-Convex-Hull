# Greenfoot Convex Hull
This is a starter project to improve the collision detection in [Greenfoot](https://www.greenfoot.org/overview).

Greenfoot is an IDE designed for beginners to learn object-oriented programming in Java by creating simple games and simulations. By default, Greenfoot uses [Axis Aligned Bounding Boxes and Oriented Bounding Boxes](https://en.wikipedia.org/wiki/Minimum_bounding_box) to detect collisions. However, this approach can result in imprecise collision detection.

This Greenfoot template enhances collision detection by integrating the [Graham Scan algorithm](https://en.wikipedia.org/wiki/Graham_scan) to construct [convex hulls](https://en.wikipedia.org/wiki/Convex_hull) for more precise collision detection using the [Separating Axis Theorem (SAT)](https://programmerart.weebly.com/separating-axis-theorem.html).

To improve performance, the more computationally expensive SAT is only applied when neccessary, therefore when the Axis Aligned Bounding Boxes (AABB) are colliding. In all other cases, the less demanding AABB collision detection is used. Additionally, bounding boxes are calculated more precisely by using convex hulls to determine the minimum area.

The `main` branch serves as a template for implementing more precise collision detection. The  `testing-scenario` branch visualizes the differences between AABB and convex hull collision detection.

## Setup
This projects includes the [Greenfoot Maven Starter](https://github.com/LeoTuet/greenfoot-maven-starter) template, so no separate installation is required. For setup and usage instructions, please refer to the corresponding [README](https://github.com/LeoTuet/greenfoot-maven-starter?tab=readme-ov-file#greenfoot-maven-starter).

If you plan to use this template in the Greenfoot IDE, download the [Greenfoot_Convex_Hull.zip](https://github.com/user-attachments/files/18718142/Greenfoot_Convex_Hull.zip)
and open it in Greenfoot.

## Usage
- Clone the `main` branch of this repository.

### CHObject.java (Convex Hull actor)
- `CHObject.java` serves as a template for any actor in the world.
- Duplicate and rename the class based on the number of actor types needed.
- Provide an image for the actor in the constructor or via the Greenfoot IDE GUI.
```java
public CHObject() { 
	image = new GreenfootImage("image.png");
	setImage(image);
}
```
- Define all vertices of your actor to construct the convex hull. You can use a picture editing program (e.g., Paint) to determine the object's vertices accurately.
- Note that the coordinate system has its origin in the top left corner.
- Add the points of your actor using `new Point(x, y)`.
```java
@Override
    protected Point[] getPoints() {
        return new Point[] {
            new Point(45, 0),
            new Point(0, 90),
            new Point(90, 90),
        };
    }
```
- Implement the collision detection logic.
```java
public void act() {
	for (CHObject object : getWorld().getObjects(CHObject.class)) {
		if (checkCollision(object)) {
			Greenfoot.stop();
		}
	}
}
```
- Implement additional actor behavior as needed using Greenfoot.

### MyWorld.java (Game world)
- `MyWorld.java` defines the environment where actors are placed.
- Configure the world using: `super(worldWidth, worldHeight, cellSize)`.
- Add instances/objects of your classes with their locations.
```java
public MyWorld() {
	super(1000, 700, 1);
	addObject(new CHObject(), 500, 100);
}
```
- Implement your own game logic with Greenfoot.

> [!NOTE]
> `AABBObject.java` can be used as a template for traditional Greenfoot collision detection.

## Output
The type of collision detection currently being used is printed to the terminal.
| Condition  | Output |
| ------------- | ------------- |
| Collision is detected | `Hulls overlap`  |
| Objects do not collide due to AABB detection  | `Hulls don't overlap because of BBOverlap`  |
| Objects do not collide due to SAT detection  | `Hulls don't overlap because of SAT`  |

Additionally, all convex hull points are printed to the terminal when initializing the program for debugging purposes.

> [!WARNING]
> **Current limitations**
> - Rotating convex hull actors are not supported. 
> - All points must be defined manually in each object class.
> - Collisions between AABB and convex hulls objects are not supported.
