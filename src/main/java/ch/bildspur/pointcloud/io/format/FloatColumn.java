package ch.bildspur.pointcloud.io.format;

import java.nio.Buffer;
import java.nio.FloatBuffer;

public class FloatColumn extends MetaColumn {

    public FloatColumn(String name, int length) {
        super(name, length, MetaColumnDataType.FLOAT);
    }

    @Override
    public Buffer createBuffer() {
        return FloatBuffer.allocate(length);
    }
}
