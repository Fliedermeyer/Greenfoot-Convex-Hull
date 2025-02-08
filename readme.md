# Greenfoot convex hull
This is a starter project to improve the collision detection in [Greenfoot](https://www.greenfoot.org/overview).

Greenfoot is an IDE designed for beginners to learn object-oriented programming in Java by creating simple games and simulations. By standard, Greenfoot uses [Axis Aligned Bounding Boxes and Oriented Bounding Boxes](https://en.wikipedia.org/wiki/Minimum_bounding_box) to detect collisions. This leads to unprecise collision detections. 

This Greenfoot template enhances collision detection by integrating the [Graham Scan algorithm](https://en.wikipedia.org/wiki/Graham_scan) to construct [convex hulls](https://en.wikipedia.org/wiki/Convex_hull) for more precise collision detection using the [Separating Axis Theorem](https://programmerart.weebly.com/separating-axis-theorem.html).
To improve performance, the more demanding Separating Axis Theorem is only used when neccessary, therefore when the Axis Aligned Bounding Boxes are colliding. In all other cases, the less demanding Bounding Box collision detection is executed. Bounding Boxes are calculated more precisely by using the convex hulls to determine the minimum area. 

## Setup
This projects included the [Greenfoot Maven Starter](https://github.com/LeoTuet/greenfoot-maven-starter) template, so no separate installation is required. Just clone this repository. For setup and usage instructions, please refer to the [README](https://github.com/LeoTuet/greenfoot-maven-starter?tab=readme-ov-file#greenfoot-maven-starter). It is tailored to run in VSCode but should be usable in every environment where Maven is supported.

If you are planning to use this template in the Greenfoot IDE, just download the [Greenfoot_Convex_Hull.zip](https://github.com/user-attachments/files/18718142/Greenfoot_Convex_Hull.zip)
and open it in Greenfoot (`Scenario -> Open`).

## Usage


## Output
Which collision detection is currently being used is printed to the terminal. 
- When a collision is being detected -> *Hulls overlap*
- When  objects don't collide because of the traditional Axis Aligned Bounding Box collision detection -> *Hulls don't overlap because of BBOverlap*
- When objects don't collide while the more precise Separating Axis Theorem is needed -> *Hulls don't overlap because of SAT*

All points of the convex hull are printed to the terminal when inniating the programming.


> ⚠️ **Current limitations** ⚠️
> > Rotating convex hull actors are not supported.
> > All points must be defined manually in each object class.
