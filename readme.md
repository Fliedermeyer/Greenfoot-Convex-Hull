# Greenfoot convex hull
This is a starter project to improve the collision detection in [Greenfoot](https://www.greenfoot.org/overview). Greenfoot is an IDE designed for beginners to learn object-oriented programming in Java by creating simple games and simulations. By standard, Greenfoot uses [Axis Aligned Bounding Boxes and Oriented Bounding Boxes](https://en.wikipedia.org/wiki/Minimum_bounding_box) to detect collisions. This leads to unprecise collision detections. 

This Greenfoot template enhances collision detection by integrating the [Graham Scan algorithm](https://en.wikipedia.org/wiki/Graham_scan) to construct [convex hulls](https://en.wikipedia.org/wiki/Convex_hull) for more precise collision detection using the [Separating Axis Theorem](https://programmerart.weebly.com/separating-axis-theorem.html).

To improve performance, the more demanding Separating Axis Theorem is only used when neccessary, therefore when the Axis Aligned Bounding Box are colliding. In all other cases, the less demanding Bounding Box collision is being executed. Bounding Boxes are calculated more precisely by using the convex hulls to determine the minimum area. 

## Output
Which collision detection is currently being used is printed to the terminal. 
- When a collision is being detected -> *Hulls overlap*
- When  objects don't collide because of the traditional Axis Aligned Bounding Box collision detection -> *Hulls don't overlap because of BBOverlap*
- When objects don't collide while the more precise Separating Axis Theorem is needed -> *Hulls don't overlap because of SAT*

All points of the convex hull are printed to the terminal when inniating the programming.

It is impossible to create a convex object with less than 3 points.
- If the provided Point[] array has less than 3 points -> *Convex Hull cannot be constructed with less than 3 points*

⚠️ **This implementation does currently not support rotating convex hull actors** ⚠️
