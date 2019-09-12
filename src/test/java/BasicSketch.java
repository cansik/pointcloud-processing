import ch.bildspur.pointcloud.PointCloudRenderer;
import ch.bildspur.pointcloud.attribute.FloatAttribute;
import ch.bildspur.pointcloud.PointCloudBuffer;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PVector;
import processing.opengl.PJOGL;
import processing.opengl.PShader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;


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
        size(800, 600, P3D);
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
        pclBuffer = new PointCloudBuffer(1000 * 1000);
        FloatAttribute positionAttribute = new FloatAttribute("position", 4);

        pclBuffer.addAttribute(positionAttribute);

        pclBuffer.allocate();

        // fill with random points
        for(int i = 0; i < pclBuffer.getLength(); i++) {
            PVector v = PVector.random3D();
            v.mult(500);
            positionAttribute.set(i * positionAttribute.getElementSize(), v.x, v.y, v.z, 1.0f);
        }

        pclRenderer.attach(pclBuffer);
    }

    @Override
    public void draw() {
        background(22);
        pclRenderer.render(pclBuffer);

        cam.beginHUD();
        text("FPS: " + frameRate + "\nVertices: " + pclBuffer.getLength(), 30, 30);
        cam.endHUD();
    }
}
