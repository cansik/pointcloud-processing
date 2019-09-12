package ch.bildspur.pointcloud.io.converter;

import ch.bildspur.pointcloud.io.format.MetaPointCloud;
import ch.bildspur.pointcloud.visual.PointCloudBuffer;

public class PointCloudConverter {

    public PointCloudBuffer createBuffer(MetaPointCloud meta) {
        // todo: add conversion table
        return new PointCloudBuffer(1);
    }

    public MetaPointCloud createMetaCloud(PointCloudBuffer buffer) {
        // todo: add conversion table
        return new MetaPointCloud();
    }
}
