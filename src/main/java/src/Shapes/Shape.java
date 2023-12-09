package src.Shapes;

import src.Shapes.HardShapes.House.HouseLeftUp;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class Shape {
    protected final int SIDE_SIZE;
    protected Graphics2D g;
    protected BufferedImage image;

    public Shape(int sideSize) {
        this.SIDE_SIZE = sideSize;
        image = new BufferedImage(sideSize, sideSize, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        draw();
    }

    public int getSideSize() {
        return SIDE_SIZE;
    }

//    public Graphics2D getGraphic() {
//        return g;
//    }

    public BufferedImage getImage() {
        return image;
    }

    public abstract void draw();

    protected void rotateShape(Shape shape, int angle) {
        AffineTransform trans = new AffineTransform();
        trans.rotate(Math.toRadians(angle), getSideSize() / 2.0, getSideSize() / 2.0);
        g.drawImage(shape.getImage(), trans, null);
    }
}
