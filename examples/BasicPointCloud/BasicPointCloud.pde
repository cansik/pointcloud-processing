import ch.bildspur.pointcloud.attribute.*;
import ch.bildspur.pointcloud.visual.*;
import processing.opengl.*;

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

  // setup pointcloud
  pclBuffer = new PointCloudBuffer(1000 * 4000);
  FloatAttribute positionAttribute = new FloatAttribute("position", 4);

  pclBuffer.addAttribute(positionAttribute);
  pclBuffer.allocate();

  // fill with random points
  for (int i = 0; i < pclBuffer.getLength(); i++) {
    PVector v = PVector.random3D();
    v.mult(random(10, 300));
    positionAttribute.set(i * positionAttribute.getElementSize(), v.x, v.y, v.z, 1.0f);
  }

  pclRenderer.attach(pclBuffer);
}

void draw() {
  background(22);

  push();
  translate(width / 2, height / 2);
  rotateY(frameCount / 1000.0);
  pclRenderer.render(pclBuffer);
  pop();

  text(" ", 0, 0);
  surface.setTitle("FPS: " + nfp(frameRate, 2, 2) + "\tVertices: " + pclBuffer.getLength());
}
