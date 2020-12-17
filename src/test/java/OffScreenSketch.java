import ch.bildspur.pointcloud.io.reader.PLYReader;
import ch.bildspur.pointcloud.visual.PointCloudBuffer;
import ch.bildspur.pointcloud.visual.PointCloudRenderer;
import org.jengineering.sjmply.PLYFormat;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.opengl.PJOGL;
import processing.opengl.PShader;

public class OffScreenSketch extends PApplet {
    public static void main(String... args) {
        OffScreenSketch sketch = new OffScreenSketch();
        sketch.run();
    }

    PShader shader;
    PointCloudRenderer pclRenderer;
    PointCloudBuffer pclBuffer;

    PGraphics canvas;

    public void run() {
        runSketch();
    }

    @Override
    public void settings() {
        size(800, 800, P2D);
        PJOGL.profile = 4;
    }

    @Override
    public void setup() {
        perspective(PConstants.PI / 3.0f, (float)width / height, 0.1f, 100000f);

        // setup canvas
        canvas = createGraphics(width, height, P3D);

        // setup renderer
        shader = loadShader("shader/basicPointFrag.glsl", "shader/basicPointVertex.glsl");
        pclRenderer = new PointCloudRenderer(canvas);
        pclRenderer.setShader(shader);

        // setup pointcloud
        PLYReader reader = new PLYReader(PLYFormat.BINARY_LITTLE_ENDIAN);
        pclBuffer = reader.read("examples/LoadPointCloud/bunny.ply");

        // it's necessary that the canvas is in draw mode!
        canvas.beginDraw();
        pclRenderer.attach(pclBuffer);
        canvas.endDraw();
    }

    @Override
    public void draw() {
        canvas.beginDraw();
        canvas.background(100, 178, 205);
        canvas.push();
        canvas.translate(width / 2f, height * 0.9f, -100);
        canvas.scale(0.5f);
        canvas.rotateY(millis() / 5000.0f);
        pclRenderer.render(pclBuffer);
        canvas.pop();
        canvas.endDraw();

        // render canvas
        image(canvas, 0, 0);

        text("FPS: " + round(frameRate) + " Vertices: " + pclBuffer.getLength(), 30, 30);
    }
}