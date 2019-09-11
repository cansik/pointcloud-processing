package ch.bildspur.pointcloud.buffer;

import ch.bildspur.pointcloud.attribute.PointCloudAttribute;

import java.util.HashMap;
import java.util.Map;

public abstract class PointCloudBuffer {
    int length;
    HashMap<String, PointCloudAttribute> attributes = new HashMap<>();

    public PointCloudBuffer(int length) {
        this.length = length;
    }

    public abstract void allocate();

    public void addAttribute(String name, PointCloudAttribute attribute) {
        this.attributes.put(name, attribute);
    }

    public <T extends PointCloudAttribute> T getAttribute(String name) {
        return (T)attributes.get(name);
    }

    public Map<String, PointCloudAttribute> getAttributes() {
        return attributes;
    }

    public int getLength() {
        return length;
    }
}