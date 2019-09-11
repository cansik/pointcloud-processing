package ch.bildspur.pointcloud.test;

import ch.bildspur.pointcloud.PointCloudRenderer;
import ch.bildspur.pointcloud.attribute.FloatAttribute;
import ch.bildspur.pointcloud.PointCloudBuffer;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PVector;
import processing.opengl.PJOGL;
import processing.opengl.PShader;


public class BasicSketch extends PApplet {
    public static void main(String... args) {
        BasicSketch sketch = new BasicSketch();
        sketch.run();
    }


    PShader shader;
    PointCloudRenderer pclRenderer = new PointCloudRenderer(this);
    PointCloudBuffer pclBuffer;

    PeasyCam cam;

    public void run()
    {
        runSketch();
    }

    @Override
    public void settings()
    {
        size(500, 500, P3D);
        PJOGL.profile = 4;
    }

    @Override
    public void setup()
    {
        cam = new PeasyCam(this, 400);

        // setup renderer
        shader = loadShader("shader/basicPointFrag.glsl", "shader/basicPointVertex.glsl");
        pclRenderer.setShader(shader);

        // setup pointcloud
        pclBuffer = new PointCloudBuffer(500);
        FloatAttribute positionAttribute = new FloatAttribute("position", 4);

        pclBuffer.addAttribute(positionAttribute);

        pclBuffer.allocate();

        // fill with random points
        for(int i = 0; i < pclBuffer.getLength(); i++) {
            PVector v = PVector.random3D();
            v.mult(10);
            positionAttribute.set(i * positionAttribute.getElementSize(), v.x, v.y, v.z);
        }

        pclRenderer.attach(pclBuffer);
    }

    @Override
    public void draw() {
        background(100);

        box(50);

        g.push();
        g.translate(0, 0, 0);
        pclRenderer.render(pclBuffer);
        g.pop();
    }
}
