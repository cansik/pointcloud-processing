package ch.bildspur.pointcloud.io.format;

import java.util.ArrayList;

public class MetaCloudFormat {
    private ArrayList<MetaColumn> columns;

    public MetaCloudFormat() {
        columns = new ArrayList<>();
    }

    public void addColumn(MetaColumn column) {
        this.columns.add(column);
    }
}
