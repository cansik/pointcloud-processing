package ch.bildspur.pointcloud.attribute;

import com.jogamp.opengl.GL;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class FloatAttribute extends PointCloudAttribute {
    private FloatBuffer buffer;

    public FloatAttribute(String name, int elementSize) {
        super(name, elementSize);
    }

    public void set(int index, float value) {
        buffer.rewind();
        buffer.put(index, value);
        buffer.rewind();
    }

    public void set(int index, float... values) {
        buffer.rewind();
        for(int i = 0; i < values.length; i++) {
            buffer.put(index + i, values[i]);
        }
        buffer.rewind();
    }

    public void set(float[] values) {
        buffer.rewind();
        buffer.put(values);
        buffer.rewind();
    }

    @Override
    public int getGLType() {
        return GL.GL_FLOAT;
    }

    @Override
    public int getSizeOfType() {
        return Float.BYTES;
    }

    @Override
    public Buffer getBuffer() {
        return buffer;
    }

    @Override
    public void allocate(int length) {
        buffer = allocateDirectFloatBuffer(elementSize * length);
    }

    private FloatBuffer allocateDirectFloatBuffer(int n) {
        return ByteBuffer.allocateDirect(n * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }
}
