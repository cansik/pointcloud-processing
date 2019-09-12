import ch.bildspur.pointcloud.attribute.FloatAttribute;
import ch.bildspur.pointcloud.io.reader.PLYReader;
import ch.bildspur.pointcloud.visual.PointCloudBuffer;
import ch.bildspur.pointcloud.visual.PointCloudRenderer;
import org.jengineering.sjmply.PLYFormat;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PVector;
import processing.opengl.PJOGL;
import processing.opengl.PShader;

public class LoadCloudSketch extends PApplet {
    public static void main(String... args) {
        LoadCloudSketch sketch = new LoadCloudSketch();
        sketch.run();
    }

    PShader shader;
    PointCloudRenderer pclRenderer = new PointCloudRenderer(this);
    PointCloudBuffer pclBuffer;

    PeasyCam cam;

    public void run() {
        runSketch();
    }

    @Override
    public void settings() {
        size(800, 600, P3D);
        //fullScreen(P3D);
        PJOGL.profile = 2;
    }

    @Override
    public void setup() {
        cam = new PeasyCam(this, 400);

        // setup renderer
        shader = loadShader("shader/basicPointFrag.glsl", "shader/basicPointVertex.glsl");
        pclRenderer.setShader(shader);

        // setup pointcloud
        PLYReader reader = new PLYReader(PLYFormat.BINARY_LITTLE_ENDIAN);
        pclBuffer = reader.read("readme/Kornhaus_5m.ply");

        pclRenderer.attach(pclBuffer);
    }

    @Override
    public void draw() {
        background(100, 178, 205);

        push();
        scale(10f);
        rotateZ(PI);
        rotateX(PI/-2);
        pclRenderer.render(pclBuffer);
        pop();

        cam.beginHUD();
        text("FPS: " + frameRate + "\nVertices: " + pclBuffer.getLength(), 30, 30);
        cam.endHUD();
    }
}