import ch.bildspur.pointcloud.io.reader.PLYReader;
import ch.bildspur.pointcloud.visual.PointCloudBuffer;
import ch.bildspur.pointcloud.visual.PointCloudRenderer;
import org.jengineering.sjmply.PLYFormat;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.opengl.PJOGL;
import processing.opengl.PShader;

public class OffScreenSketch extends PApplet {
    public static void main(String... args) {
        OffScreenSketch sketch = new OffScreenSketch();
        sketch.run();
    }

    PShader shader;
    PointCloudRenderer pclRenderer = new PointCloudRenderer(this);
    PointCloudBuffer pclBuffer;

    public void run() {
        runSketch();
    }

    @Override
    public void settings() {
        size(800, 600, P3D);
        PJOGL.profile = 4;
    }

    @Override
    public void setup() {
        perspective(PConstants.PI / 3.0f, (float)width / height, 0.1f, 100000f);

        // setup renderer
        shader = loadShader("shader/basicPointFrag.glsl", "shader/basicPointVertex.glsl");
        pclRenderer.setShader(shader);

        // setup pointcloud
        PLYReader reader = new PLYReader(PLYFormat.BINARY_LITTLE_ENDIAN);
        pclBuffer = reader.read("examples/LoadPointCloud/bunny.ply");

        pclRenderer.attach(pclBuffer);
    }

    @Override
    public void draw() {
        background(100, 178, 205);

        push();
        translate(width / 2f, height * 0.9f, -100);
        scale(0.5f);
        rotateY(millis() / 5000.0f);
        pclRenderer.render(pclBuffer);
        pop();

        surface.setTitle("FPS: " + round(frameRate) + " Vertices: " + pclBuffer.getLength());
    }
}