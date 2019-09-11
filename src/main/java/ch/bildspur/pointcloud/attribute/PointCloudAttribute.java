package ch.bildspur.pointcloud.attribute;

public abstract class PointCloudAttribute {
    int elementSize;
    String name;

    public PointCloudAttribute(int elementSize) {
        this.elementSize = elementSize;
    }

    public abstract void allocate(int length);

    public int getElementSize() {
        return elementSize;
    }

    public String getName() {
        return name;
    }
}
