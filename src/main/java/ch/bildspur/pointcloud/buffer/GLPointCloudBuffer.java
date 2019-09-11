package ch.bildspur.pointcloud.buffer;

import ch.bildspur.pointcloud.attribute.PointCloudAttribute;

public class GLPointCloudBuffer extends PointCloudBuffer {
    public GLPointCloudBuffer(int length) {
        super(length);
    }

    @Override
    public void allocate() {
        // allocate storage for each attribute
        for(PointCloudAttribute attribute : getAttributes()) {
            attribute.allocate(length);
        }
    }
}
