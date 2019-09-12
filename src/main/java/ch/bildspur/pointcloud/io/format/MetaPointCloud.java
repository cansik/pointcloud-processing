package ch.bildspur.pointcloud.io.format;

import java.util.ArrayList;
import java.util.List;

public class MetaPointCloud {
    private ArrayList<MetaColumn> columns;

    public MetaPointCloud() {
        columns = new ArrayList<>();
    }

    int getLength() {
        if(columns.isEmpty())
            return 0;

        return columns.get(0).length;
    }

    public void addColumn(MetaColumn column) {
        this.columns.add(column);
    }

    public List<MetaColumn> getColumns() {
        return columns;
    }
}
