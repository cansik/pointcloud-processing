package ch.bildspur.pointcloud.attribute;

import com.jogamp.opengl.GL;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class IntAttribute extends PointCloudAttribute{
    private IntBuffer buffer;

    public IntAttribute(String name, int elementSize) {
        super(name, elementSize);
    }

    public void set(int index, int value) {
        buffer.rewind();
        buffer.put(index, value);
        buffer.rewind();
    }

    public void set(int index, int... values) {
        buffer.rewind();
        for(int i = 0; i < values.length; i++) {
            buffer.put(index + i, values[i]);
        }
        buffer.rewind();
    }

    @Override
    public void allocate(int length) {
        buffer = allocateDirectIntBuffer(elementSize * length);
    }

    @Override
    public int getGLType() {
        return GL.GL_UNSIGNED_INT;
    }

    @Override
    public int getSizeOfType() {
        return Integer.BYTES;
    }

    @Override
    public Buffer getBuffer() {
        return buffer;
    }

    private IntBuffer allocateDirectIntBuffer(int n) {
        return ByteBuffer.allocateDirect(n * Integer.BYTES).order(ByteOrder.nativeOrder()).asIntBuffer();
    }
}
