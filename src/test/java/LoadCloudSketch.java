import ch.bildspur.pointcloud.io.reader.PLYReader;
import ch.bildspur.pointcloud.visual.PointCloudBuffer;
import ch.bildspur.pointcloud.visual.PointCloudRenderer;
import org.jengineering.sjmply.PLYFormat;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PConstants;
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
        PJOGL.profile = 4;
        pixelDensity(2);
    }

    @Override
    public void setup() {
        cam = new PeasyCam(this, 400);
        perspective(PConstants.PI / 3.0f, (float)width / height, 0.1f, 100000f);

        // setup renderer
        shader = loadShader("shader/basicPointFrag.glsl", "shader/basicPointVertex.glsl");
        pclRenderer.setShader(shader);

        // setup pointcloud
        PLYReader reader = new PLYReader(PLYFormat.BINARY_LITTLE_ENDIAN);
        // pclBuffer = reader.read("readme/Kornhaus_2.5m.ply");
        // pclBuffer = reader.read("readme/Kornhaus_1m.ply");
        //pclBuffer = reader.read("readme/Kornhaus_500k.ply");
        pclBuffer = reader.read("readme/StadtmuseumAarau_8mio.ply");

        pclRenderer.attach(pclBuffer);
    }

    @Override
    public void draw() {
        background(100, 178, 205);

        push();
        scale(50f);
        rotateZ(PI);
        rotateX(PI/-2);
        pclRenderer.render(pclBuffer);
        pop();

        cam.beginHUD();
        text("FPS: " + frameRate + "\nVertices: " + pclBuffer.getLength(), 30, 30);
        cam.endHUD();
    }
}