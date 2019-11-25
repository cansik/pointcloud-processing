package ch.bildspur.pointcloud.visual;

import ch.bildspur.pointcloud.attribute.IntAttribute;
import ch.bildspur.pointcloud.attribute.PointCloudAttribute;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;
import processing.core.PApplet;
import processing.opengl.PGL;
import processing.opengl.PJOGL;
import processing.opengl.PShader;

import java.nio.IntBuffer;
import java.util.Collection;

public class PointCloudRenderer {
    private PApplet app;
    private PShader shader;

    public PointCloudRenderer(PApplet app) {
        this.app = app;
    }

    public void loadShader(String fragmentShaderPath, String vertexShaderPath) {
        this.shader = app.loadShader(fragmentShaderPath, vertexShaderPath);
    }

    public void attach(PointCloudBuffer buffer) {
        PJOGL pgl = (PJOGL) app.beginPGL();
        GL4 gl = pgl.gl.getGL4();

        // generate vbo ids (+1 for indices)
        IntBuffer intBuffer = IntBuffer.allocate(buffer.getAttributes().size() + 1);
        gl.glGenBuffers(buffer.getAttributes().size() + 1, intBuffer);

        // set indices vboid
        buffer.getIndicesAttribute().setVboId(intBuffer.get(0));

        // allocate storage and set ids
        int i = 0;
        shader.bind();
        for (PointCloudAttribute attribute : buffer.getAttributes().values()) {
            int location = gl.glGetAttribLocation(shader.glProgram, attribute.getName());

            attribute.setVboId(intBuffer.get(i + 1));
            attribute.setShaderLocation(location);

            i++;
        }
        shader.unbind();
        app.endPGL();

        // set dirty flag
        buffer.setDirty(true);
    }

    public void render(PointCloudBuffer buffer) {
        PJOGL pgl = (PJOGL) app.beginPGL();
        GL4 gl = pgl.gl.getGL4();

        Collection<PointCloudAttribute> attributes = buffer.getAttributes().values();

        shader.bind();
        // enable vbos and add buffer
        for (PointCloudAttribute attribute : attributes) {
            gl.glEnableVertexAttribArray(attribute.getShaderLocation());

            gl.glBindBuffer(GL.GL_ARRAY_BUFFER, attribute.getVboId());
            if (buffer.isDirty())
                gl.glBufferData(
                        GL.GL_ARRAY_BUFFER,
                        attribute.getSizeOfType() * attribute.getElementSize() * buffer.getLength(),
                        attribute.getBuffer(),
                        GL.GL_DYNAMIC_DRAW);
            gl.glVertexAttribPointer(attribute.getShaderLocation(), attribute.getElementSize(), attribute.getGLType(),
                    false, attribute.getElementSize() * attribute.getSizeOfType(), 0);
        }

        // enable point size
        gl.glEnable(GL4.GL_PROGRAM_POINT_SIZE);

        // draw indexes
        IntAttribute indices = buffer.getIndicesAttribute();
        gl.glBindBuffer(PGL.ELEMENT_ARRAY_BUFFER, indices.getVboId());
        if (buffer.isDirty())
            gl.glBufferData(
                    PGL.ELEMENT_ARRAY_BUFFER,
                    indices.getSizeOfType() * indices.getElementSize() * buffer.getLength(),
                    indices.getBuffer(),
                    GL.GL_DYNAMIC_DRAW);

        // draw elements
        gl.glDrawElements(PGL.POINTS, buffer.getLength(), indices.getGLType(), 0);
        gl.glBindBuffer(PGL.ELEMENT_ARRAY_BUFFER, 0);

        // disable vbos
        for (PointCloudAttribute attribute : attributes) {
            gl.glDisableVertexAttribArray(attribute.getShaderLocation());
        }

        shader.unbind();
        app.endPGL();

        // reset dirty flag
        if(buffer.isDirty())
            buffer.setDirty(false);
    }

    public PShader getShader() {
        return shader;
    }

    public void setShader(PShader shader) {
        this.shader = shader;
    }
}
