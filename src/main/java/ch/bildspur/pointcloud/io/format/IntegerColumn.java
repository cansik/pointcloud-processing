package ch.bildspur.pointcloud.io.format;

import java.nio.Buffer;
import java.nio.IntBuffer;

public class IntegerColumn extends MetaColumn {

    public IntegerColumn(String name, int length) {
        super(name, length, MetaColumnDataType.INTEGER);
    }

    @Override
    public Buffer createBuffer() {
        return IntBuffer.allocate(length);
    }
}
