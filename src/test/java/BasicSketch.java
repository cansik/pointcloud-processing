import ch.bildspur.pointcloud.visual.PointCloudRenderer;
import ch.bildspur.pointcloud.attribute.FloatAttribute;
import ch.bildspur.pointcloud.visual.PointCloudBuffer;
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

    boolean drawRect = false;

    public void run()
    {
        runSketch();
    }

    @Override
    public void settings()
    {
        size(800, 600, P3D);
        //fullScreen(P3D);
        PJOGL.profile = 2;
    }

    @Override
    public void setup()
    {
        cam = new PeasyCam(this, 400);

        // setup renderer
        shader = loadShader("shader/basicPointFrag.glsl", "shader/basicPointVertex.glsl");
        pclRenderer.setShader(shader);

        // setup pointcloud
        pclBuffer = new PointCloudBuffer(1000);
        FloatAttribute positionAttribute = new FloatAttribute("position", 4);

        pclBuffer.addAttribute(positionAttribute);

        pclBuffer.allocate();

        // fill with random points
        float pointPerSide = sqrt(pclBuffer.getLength()) / 2;
        float size = 500;
        float sizeStep = size / pointPerSide;

        for(int i = 0; i < pclBuffer.getLength(); i++) {
            float x = sizeStep * (i % pointPerSide);
            float y = sizeStep * floor(i / pointPerSide);

            PVector v = new PVector();
            v.x = x;
            v.y = y;
            positionAttribute.set(i * positionAttribute.getElementSize(), v.x, v.y, v.z, 1.0f);
        }

        pclRenderer.attach(pclBuffer);
    }

    @Override
    public void draw() {
        background(22);
        pclRenderer.render(pclBuffer);

        if(drawRect) {
            noFill();
            stroke(0, 255, 0);
            rect(0, 0, 500, 500);
        }

        cam.beginHUD();
        text("FPS: " + frameRate + "\nVertices: " + pclBuffer.getLength() +"\nBB: " + drawRect, 30, 30);
        cam.endHUD();
    }

    @Override
    public void keyPressed() {
        drawRect = !drawRect;
    }
}
