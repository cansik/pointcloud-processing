import ch.bildspur.pointcloud.attribute.*;
import ch.bildspur.pointcloud.visual.*;
import processing.opengl.*;
import ch.bildspur.postfx.builder.*;
import ch.bildspur.postfx.pass.*;
import ch.bildspur.postfx.*;
import peasy.PeasyCam;

PeasyCam cam;

PShader shader;
PointCloudRenderer pclRenderer;
PointCloudBuffer pclBuffer;

PostFX fx;

void setting() {
  PJOGL.profile = 2;
}

void setup() {
  size(800, 800, P3D);

  // setup camera
  cam = new PeasyCam(this, 450);
  cam.setMinimumDistance(280);
  cam.setMaximumDistance(800);

  // setup fx
  fx = new PostFX(this);

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
  
  shader.set("time", millis() / 1000f);

  push();
  translate(width * 0.05, height * 0.3);
  //rotateY(frameCount / 1000.0);
  scale(0.5);
  //scale(500);
  pclRenderer.render(pclBuffer);
  pop();

  cam.beginHUD();
  fx.render()
    //.pixelate(100)
    //.sobel()
    .bloom(0.6, 20, 50)
    .compose();

  // show info
  fill(255);
  textSize(20);
  text("FPS: " + frameRate, 50, 50);
  cam.endHUD();

  surface.setTitle("FPS: " + nfp(frameRate, 2, 2) + "\tVertices: " + pclBuffer.getLength());
}
