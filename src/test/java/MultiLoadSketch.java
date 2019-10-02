import ch.bildspur.pointcloud.io.reader.PLYReader;
import ch.bildspur.pointcloud.visual.PointCloudBuffer;
import ch.bildspur.pointcloud.visual.PointCloudRenderer;
import org.jengineering.sjmply.PLYFormat;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.opengl.PJOGL;
import processing.opengl.PShader;

public class MultiLoadSketch extends PApplet {
    public static void main(String... args) {
        MultiLoadSketch sketch = new MultiLoadSketch();
        sketch.run();
    }

    PShader shader;
    PointCloudRenderer pclRenderer = new PointCloudRenderer(this);

    PointCloudBuffer kornhaus2mio;
    PointCloudBuffer kornhaus1mio;
    PointCloudBuffer kornhaus500k;

    PeasyCam cam;

    public void run() {
        runSketch();
    }

    @Override
    public void settings() {
        size(1024, 600, P3D);
        PJOGL.profile = 2;
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
        kornhaus2mio = reader.read("readme/Kornhaus_2.5m.ply");
        kornhaus1mio = reader.read("readme/Kornhaus_1m.ply");
        kornhaus500k = reader.read("readme/Kornhaus_500k.ply");

        pclRenderer.attach(kornhaus2mio);
        pclRenderer.attach(kornhaus1mio);
        pclRenderer.attach(kornhaus500k);
    }

    @Override
    public void draw() {
        background(100, 178, 205);

        renderCloud(kornhaus500k, map(2, 0, 8, -width / 2f, +width / 2f), 0);
        renderCloud(kornhaus1mio, map(4, 0, 8, -width / 2f, +width / 2f), 0);
        renderCloud(kornhaus2mio, map(6, 0, 8, -width / 2f, +width / 2f), 0);

        cam.beginHUD();
        text("FPS: " + frameRate, 30, 30);
        cam.endHUD();
    }

    private void renderCloud(PointCloudBuffer cloud, float x, float y) {
        push();
        translate(x, y);
        scale(1f);
        rotateZ(PI);
        rotateZ(frameCount / 1000f);
        pclRenderer.render(cloud);
        pop();
    }
}