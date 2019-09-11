package ch.bildspur.pointcloud.test;

import ch.bildspur.pointcloud.attribute.FloatAttribute;
import ch.bildspur.pointcloud.buffer.GLPointCloudBuffer;
import ch.bildspur.pointcloud.buffer.PointCloudBuffer;
import processing.core.PApplet;
import processing.core.PVector;


public class BasicSketch extends PApplet {
    public static void main(String... args) {
        BasicSketch sketch = new BasicSketch();
        sketch.run();
    }

    PointCloudBuffer pointCloudBuffer;

    public void run()
    {
        runSketch();
    }

    @Override
    public void settings()
    {
        size(500, 500, P3D);
    }

    @Override
    public void setup()
    {
        // setup renderer


        // setup pointcloud
        pointCloudBuffer = new GLPointCloudBuffer(100);
        FloatAttribute positionAttribute = new FloatAttribute(4);

        pointCloudBuffer.addAttribute("position", positionAttribute);
        pointCloudBuffer.allocate();

        // fill with random points
        for(int i = 0; i < pointCloudBuffer.getLength(); i++) {
            PVector v = PVector.random3D();
            v.mult(100);

            positionAttribute.set(i * positionAttribute.getElementSize(), v.x, v.y, v.z);
        }
    }

    @Override
    public void draw() {
        int c = color(frameCount % 360, 50, 50);

        background(c);
    }
}
