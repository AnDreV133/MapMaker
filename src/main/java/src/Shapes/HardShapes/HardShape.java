package src.Shapes.HardShapes;

import src.Shapes.Shape;

public abstract class HardShape {
    protected final int SIDE_SIZE;
    private final Shape DEFAULT_SHAPE;

    public HardShape(int sideSize, Shape defaultShape) {
        SIDE_SIZE = sideSize;
        DEFAULT_SHAPE = defaultShape;
    }

    public Shape getShapeRD() {
        return DEFAULT_SHAPE;
    }

    public Shape getShapeLRD() {
        return DEFAULT_SHAPE;
    }

    public Shape getShapeLD() {
        return DEFAULT_SHAPE;
    }

    public Shape getShapeRUD() {
        return DEFAULT_SHAPE;
    }

    public Shape getShapeLRUD() {
        return DEFAULT_SHAPE;
    }

    public Shape getShapeLUD() {
        return DEFAULT_SHAPE;
    }

    public Shape getShapeRU() {
        return DEFAULT_SHAPE;
    }

    public Shape getShapeLRU() {
        return DEFAULT_SHAPE;
    }

    public Shape getShapeLU() {
        return DEFAULT_SHAPE;
    }

    public Shape getShapeLR() {
        return DEFAULT_SHAPE;
    }

    public Shape getShapeUD() {
        return DEFAULT_SHAPE;
    }

    public Shape getShapeU() {
        return DEFAULT_SHAPE;
    }

    public Shape getShapeL() {
        return DEFAULT_SHAPE;
    }

    public Shape getShapeR() {
        return DEFAULT_SHAPE;
    }

    public Shape getShapeD() {
        return DEFAULT_SHAPE;
    }

    public Shape getShapeC() {
        return DEFAULT_SHAPE;
    }
}
