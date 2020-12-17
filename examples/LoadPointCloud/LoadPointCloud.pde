import ch.bildspur.pointcloud.attribute.*;
import ch.bildspur.pointcloud.visual.*;
import processing.opengl.*;
import ch.bildspur.pointcloud.io.reader.PLYReader;
import org.jengineering.sjmply.PLYFormat;

PShader shader;
PointCloudRenderer pclRenderer;
PointCloudBuffer pclBuffer;

void setting() {
  PJOGL.profile = 2;
}

void setup() {
  size(800, 800, P3D);

  // setup renderer
  pclRenderer = new PointCloudRenderer(this);
  shader = loadShader("shader/basicPointFrag.glsl", "shader/basicPointVertex.glsl");
  pclRenderer.setShader(shader);

  // load pointcloud
  PLYReader reader = new PLYReader(PLYFormat.BINARY_LITTLE_ENDIAN);
  pclBuffer = reader.read(sketchPath("bunny.ply"));

  // attach pointcloud
  pclRenderer.attach(pclBuffer);
}

void draw() {
  background(0);

  push();
  translate(width / 2, height / 1.3);
  rotateY(frameCount / 1000.0);
  scale(0.5);
  pclRenderer.render(pclBuffer);
  pop();

  surface.setTitle("FPS: " + nfp(frameRate, 2, 2) + "\tVertices: " + pclBuffer.getLength());
}
