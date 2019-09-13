package ch.bildspur.pointcloud.io.reader;

import ch.bildspur.pointcloud.attribute.FloatAttribute;
import ch.bildspur.pointcloud.io.format.MetaPointCloud;
import ch.bildspur.pointcloud.visual.PointCloudBuffer;
import org.jengineering.sjmply.PLY;
import org.jengineering.sjmply.PLYElementList;
import org.jengineering.sjmply.PLYFormat;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.jengineering.sjmply.PLYType.FLOAT32;
import static org.jengineering.sjmply.PLYType.UINT8;

public class PLYReader implements PointCloudReader {
    private PLY ply;

    public PLYReader(PLYFormat format) {
        ply = new PLY();
        ply.setFormat(format);
    }

    @Override
    public PointCloudBuffer read(String filePath) {
        Path path = Paths.get(filePath);

        try
        {
            ply = PLY.load(path);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        PLYElementList vertex = ply.elements("vertex");

        // coordinates
        float[] x = vertex.property(FLOAT32, "x");
        float[] y = vertex.property(FLOAT32, "y");
        float[] z = vertex.property(FLOAT32, "z");

        // colors
        byte[] r = new byte[0];
        byte[] g = new byte[0];
        byte[] b = new byte[0];
        boolean colorLoaded = false;

        try {
            r = vertex.property(UINT8, "red");
            g = vertex.property(UINT8, "green");
            b = vertex.property(UINT8, "blue");

            colorLoaded = true;
        }
        catch(Exception ex) {
            // todo: remove println
            System.out.println("no color information!");
        }

        PointCloudBuffer cloud = new PointCloudBuffer(vertex.size);
        FloatAttribute position = new FloatAttribute("position", 4);
        FloatAttribute color = new FloatAttribute("color", 4);

        cloud.addAttribute(position);
        cloud.addAttribute(color);

        cloud.allocate();

        for (int i = 0; i < x.length; i++)
        {
            if (colorLoaded) {
                float rv = (r[i] & 0xFF) / 255f;
                float gv = (g[i] & 0xFF) / 255f;
                float bv = (b[i] & 0xFF) / 255f;
                color.set(i * color.getElementSize(), rv, gv, bv, 1.0f);
            }
            position.set(i * position.getElementSize(), x[i], -y[i], z[i], 1.0f);
        }

        return cloud;
    }
}
