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
  size(720, 720, P3D);

  frameRate(1000);

  // setup camera
  cam = new PeasyCam(this, 350);
  cam.setMinimumDistance(100);
  cam.setMaximumDistance(500);

  // remove clipping
  perspective((PI / 2.5f), (float)width / height, 0.001f, 1000f);

  // setup fx
  fx = new PostFX(this);

  // setup renderer
  pclRenderer = new PointCloudRenderer(this);
  shader = loadShader("shader/basicPointFrag.glsl", "shader/basicPointVertex.glsl");
  pclRenderer.setShader(shader);

  // load pointcloud
  PLYReader reader = new PLYReader(PLYFormat.BINARY_LITTLE_ENDIAN);
  pclBuffer = reader.read(sketchPath("forest-blk360_centered.ply"));

  // attach pointcloud
  pclRenderer.attach(pclBuffer);
}

void draw() {
  background(0);

  shader.set("time", millis() / 1000f);

  push();
  translate(0, height * 0.1);
  //scale(0.5);
  scale(500);
  pclRenderer.render(pclBuffer);
  pop();

  cam.pan(0.1, 0);

  cam.beginHUD();
  fx.render()
    .noise(0.2, 0.3)
    .blur(4, 0.6)
    //.vignette(0.5, 0.5)
    .rgbSplit(20)
    .bloom(0.6, 20, 50)
    .compose();

  // show info
  fill(255);
  textSize(20);
  //text("FPS: " + frameRate, 50, 50);
  cam.endHUD();

  surface.setTitle("FPS: " + nfp(frameRate, 2, 2) + "\tVertices: " + pclBuffer.getLength());
}
