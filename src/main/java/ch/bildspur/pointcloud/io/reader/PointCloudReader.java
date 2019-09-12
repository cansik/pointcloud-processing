package ch.bildspur.pointcloud.io.reader;

import ch.bildspur.pointcloud.io.format.MetaCloudFormat;

public interface PointCloudReader {
    MetaCloudFormat read(String filePath);

    // TODO: add this method later
    //MetaCloudFormat read(Byte[] data);

    int getLength();
}
