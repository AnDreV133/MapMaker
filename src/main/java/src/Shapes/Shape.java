package src.Shapes;

import src.Shapes.HardShapes.Fence.FenceLR;

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
    }

    public abstract void draw();

    protected void rotateShape(Shape shape, int angle) {
        AffineTransform trans = new AffineTransform();
        trans.rotate(Math.toRadians(angle), SIDE_SIZE / 2.0, SIDE_SIZE / 2.0);
        g.drawImage(shape.getImage(), trans, null);
    }

    protected void addShape(Shape shape) {
        g.drawImage(shape.getImage(), 0, 0, null);
    }

    public int getSideSize() {
        return SIDE_SIZE;
    }

    public BufferedImage getImage() {
        draw();
        return image;
    }
}
