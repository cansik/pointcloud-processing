import ch.bildspur.pointcloud.attribute.*;
import ch.bildspur.pointcloud.visual.*;
import processing.opengl.*;
import ch.bildspur.pointcloud.io.reader.PLYReader;
import org.jengineering.sjmply.PLYFormat;

PShader shader;
PointCloudRenderer pclRenderer;
PointCloudBuffer pclBuffer;

PGraphics canvas;

void setting() {
  PJOGL.profile = 2;
}

void setup() {
  size(800, 800, P2D);

  // setup canvas
  canvas = createGraphics(width, height, P3D);

  // setup renderer
  shader = loadShader("shader/basicPointFrag.glsl", "shader/basicPointVertex.glsl");
  pclRenderer = new PointCloudRenderer(canvas);
  pclRenderer.setShader(shader);

  // load pointcloud
  PLYReader reader = new PLYReader(PLYFormat.BINARY_LITTLE_ENDIAN);
  pclBuffer = reader.read(sketchPath("bunny.ply"));

  // it's necessary that the canvas is in draw mode!
  canvas.beginDraw();
  pclRenderer.attach(pclBuffer);
  canvas.endDraw();
}

void draw() {
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
