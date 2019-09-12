package ch.bildspur.pointcloud.io.reader;

import ch.bildspur.pointcloud.io.format.MetaPointCloud;
import ch.bildspur.pointcloud.visual.PointCloudBuffer;

public interface PointCloudReader {
    PointCloudBuffer read(String filePath);

    // TODO: add this method later
    //MetaCloudFormat read(Byte[] data);
}
