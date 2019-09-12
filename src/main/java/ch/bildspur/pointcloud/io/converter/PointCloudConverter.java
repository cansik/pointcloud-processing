package ch.bildspur.pointcloud.io.converter;

import ch.bildspur.pointcloud.io.format.MetaCloudFormat;
import ch.bildspur.pointcloud.visual.PointCloudBuffer;

public class PointCloudConverter {
    // todo: add conversion table
    public PointCloudBuffer createBuffer(MetaCloudFormat format) {
        return new PointCloudBuffer(1);
    }

    public MetaCloudFormat createMetaCloud(PointCloudBuffer buffer) {
        return new MetaCloudFormat();
    }
}
