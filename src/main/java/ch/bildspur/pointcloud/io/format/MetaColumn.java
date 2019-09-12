package ch.bildspur.pointcloud.io.format;

import java.nio.Buffer;

public abstract class MetaColumn {
    String name;
    Buffer data;
    MetaColumnDataType dataType;
    int length;

    public MetaColumn(String name, int length, MetaColumnDataType dataType) {
        this.name = name;
        this.dataType = dataType;
        this.length = length;
        this.data = createBuffer();
    }

    public abstract Buffer createBuffer();

    public String getName() {
        return name;
    }

    public Buffer getData() {
        return data;
    }

    public MetaColumnDataType getDataType() {
        return dataType;
    }
}
