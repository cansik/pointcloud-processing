package ch.bildspur.pointcloud.attribute;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class FloatAttribute extends PointCloudAttribute {
    private FloatBuffer buffer;

    public FloatAttribute(int elementSize) {
        super(elementSize);
    }

    @Override
    public void allocate(int length) {
        buffer = allocateDirectFloatBuffer(elementSize * length);
    }

    private FloatBuffer allocateDirectFloatBuffer(int n) {
        return ByteBuffer.allocateDirect(n * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }
}
