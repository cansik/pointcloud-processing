package ch.bildspur.pointcloud.test;

import ch.bildspur.pointcloud.PointCloudRenderer;
import ch.bildspur.pointcloud.attribute.FloatAttribute;
import ch.bildspur.pointcloud.PointCloudBuffer;
import processing.core.PApplet;
import processing.core.PVector;
import processing.opengl.PShader;


public class BasicSketch extends PApplet {
    public static void main(String... args) {
        BasicSketch sketch = new BasicSketch();
        sketch.run();
    }


    PShader shader;
    PointCloudRenderer pclRenderer = new PointCloudRenderer(this);
    PointCloudBuffer pclBuffer;

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
        shader = loadShader("shader/basicPointFrag.glsl", "shader/basicPointVertex.glsl");
        pclRenderer.setShader(shader);

        // setup pointcloud
        pclBuffer = new PointCloudBuffer(100);
        FloatAttribute positionAttribute = new FloatAttribute("position", 4);

        pclBuffer.addAttribute(positionAttribute);
        pclBuffer.allocate();

        pclRenderer.attach(pclBuffer);

        // fill with random points
        for(int i = 0; i < pclBuffer.getLength(); i++) {
            PVector v = PVector.random3D();
            v.mult(100);

            positionAttribute.set(i * positionAttribute.getElementSize(), v.x, v.y, v.z);
        }
    }

    @Override
    public void draw() {
        background(0);
        pclRenderer.render(pclBuffer);
    }
}
