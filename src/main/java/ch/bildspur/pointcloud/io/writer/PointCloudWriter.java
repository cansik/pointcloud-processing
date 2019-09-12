package ch.bildspur.pointcloud.io.writer;

import ch.bildspur.pointcloud.io.format.MetaCloudFormat;

public interface PointCloudWriter {
    public void write(MetaCloudFormat metaCloud, String fileName);
}
