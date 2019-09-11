package ch.bildspur.pointcloud.attribute;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class IntAttribute extends PointCloudAttribute{
    private IntBuffer buffer;

    public IntAttribute(int elementSize) {
        super(elementSize);
    }

    public void set(int index, int value) {
        buffer.put(index, value);
    }

    public void set(int index, int... values) {
        for(int i = 0; i < values.length; i++) {
            buffer.put(index + i, values[i]);
        }
    }

    @Override
    public void allocate(int length) {
        buffer = allocateDirectIntBuffer(elementSize * length);
    }

    private IntBuffer allocateDirectIntBuffer(int n) {
        return ByteBuffer.allocateDirect(n * Integer.BYTES).order(ByteOrder.nativeOrder()).asIntBuffer();
    }
}
