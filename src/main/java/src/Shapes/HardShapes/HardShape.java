package src.Shapes.HardShapes;

import src.Shapes.Shape;

public abstract class HardShape{
    protected final int SIDE_SIZE;

    public HardShape(int sideSize) {
        SIDE_SIZE = sideSize;
    }

    public abstract Shape getShapeLU();
    public abstract Shape getShapeU();
    public abstract Shape getShapeRU();
    public abstract Shape getShapeL();
    public abstract Shape getShapeFill();
    public abstract Shape getShapeR();
    public abstract Shape getShapeLD();
    public abstract Shape getShapeD();
    public abstract Shape getShapeRD();
    public abstract Shape getShapeH();
    public abstract Shape getShapeV();
    public abstract Shape getShapeOnlyU();
    public abstract Shape getShapeOnlyL();
    public abstract Shape getShapeOnlyR();
    public abstract Shape getShapeOnlyD();
    public abstract Shape getShapeOnlyC();
}
