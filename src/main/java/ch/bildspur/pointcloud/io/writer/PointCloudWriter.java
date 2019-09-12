package ch.bildspur.pointcloud.io.writer;

import ch.bildspur.pointcloud.io.format.MetaPointCloud;

public interface PointCloudWriter {
    public void write(MetaPointCloud metaCloud, String fileName);
}
