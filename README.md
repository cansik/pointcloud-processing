# Point Cloud for Processing [![Build Status](https://travis-ci.org/cansik/pointcloud-processing.svg?branch=master)](https://travis-ci.org/cansik/pointcloud-processing) [![Build status](https://ci.appveyor.com/api/projects/status/nbuo6sxyx40weisi?svg=true)](https://ci.appveyor.com/project/cansik/pointcloud-processing)
A point cloud visualisation and analysis library for the [Processing](https://processing.org/) framework.

![Example](readme/example.png)

*Figure 1: 5'000'000 points with 60 FPS rendered on a Radeon Pro 460*

### Idea
This framework allows you to display millions of points within your Processing sketch. The idea was to build a simple, although a performant framework, which can load, display, and even analyze point cloud data.

#### Speed

[PShape](https://github.com/processing/processing/blob/master/core/src/processing/opengl/PShapeOpenGL.java) can display points with the point shader, but because processing tessellates each point on the CPU, manipulations to the point cloud data are slow, plus there is overhead because even more vertices are allocated the needed on modern systems. This pipeline uses the OpenGL commands to generate the billboarding & tessellation directly on the graphics card, which leads to a faster drawing and simple one-to-one vertex indexing.

#### Attributes
With the original PShape, it was possible to add custom vertex attributes to a mesh. However, in `POINTS` mode, the tessellation would not care about these additional custom vertex attributes, as shown [here](https://github.com/processing/processing/issues/5895). Instead, this library offers you the full potential of vertex attributes for point vertices.

### Library Modules
The plan is to have three modules to work with point clouds. The most important is the visualization module, which contains an advanced OpenGL renderer for point clouds.

- I/O 🐙
	- read PLY files
- Visualize 🎆
	- OpenGL renderer ✅
- Analyze 🧮
	- Show cloud statistics
		- dimensions
		- pointcount

### Example
I am working on examples!

### About

