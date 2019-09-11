package ch.bildspur.pointcloud.test;

import processing.core.PApplet;


public class BasicPointCloudSketch extends PApplet {
    public static void main(String... args) {
        BasicPointCloudSketch sketch = new BasicPointCloudSketch();
        sketch.run();
    }

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
        colorMode(HSB, 360, 100, 100);
    }

    @Override
    public void draw() {
        int c = color(frameCount % 360, 50, 50);

        background(c);
    }
}
