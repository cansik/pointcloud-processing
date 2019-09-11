package ch.bildspur.pointcloud.attribute;

import java.nio.Buffer;

public abstract class PointCloudAttribute {
    String name;
    int elementSize;
    int vboId;
    int shaderLocation;

    public PointCloudAttribute(String name, int elementSize) {
        this.name = name;
        this.elementSize = elementSize;
    }

    public abstract void allocate(int length);

    public abstract int getGLType();

    public abstract int getSizeOfType();

    public abstract Buffer getBuffer();

    public int getElementSize() {
        return elementSize;
    }

    public String getName() {
        return name;
    }

    public int getVboId() {
        return vboId;
    }

    public void setVboId(int vboId) {
        this.vboId = vboId;
    }

    public int getShaderLocation() {
        return shaderLocation;
    }

    public void setShaderLocation(int shaderLocation) {
        this.shaderLocation = shaderLocation;
    }
}
