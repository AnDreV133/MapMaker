package src.Shapes.HardShapes.Fence;

import src.Shapes.HardShapes.HardShape;
import src.Shapes.Shape;

public class Fence extends HardShape {
    public Fence(int sideSize) {
        super(sideSize, new FenceC(sideSize));
    }

    @Override
    public Shape getShapeRD() {
        return new FenceRD(SIDE_SIZE);
    }

    @Override
    public Shape getShapeLRD() {
        return new FenceLRD(SIDE_SIZE);
    }

    @Override
    public Shape getShapeLD() {
        return new FenceLD(SIDE_SIZE);
    }

    @Override
    public Shape getShapeRUD() {
        return new FenceRUD(SIDE_SIZE);
    }

    @Override
    public Shape getShapeLRUD() {
        return new FenceLRUD(SIDE_SIZE);
    }

    @Override
    public Shape getShapeLUD() {
        return new FenceLUD(SIDE_SIZE);
    }

    @Override
    public Shape getShapeRU() {
        return new FenceRU(SIDE_SIZE);
    }

    @Override
    public Shape getShapeLRU() {
        return new FenceLRU(SIDE_SIZE);
    }

    @Override
    public Shape getShapeLU() {
        return new FenceLU(SIDE_SIZE);
    }

    @Override
    public Shape getShapeLR() {
        return new FenceLR(SIDE_SIZE);
    }

    @Override
    public Shape getShapeUD() {
        return new FenceUD(SIDE_SIZE);
    }

    @Override
    public Shape getShapeU() {
        return new FenceU(SIDE_SIZE);
    }

    @Override
    public Shape getShapeL() {
        return new FenceL(SIDE_SIZE);
    }

    @Override
    public Shape getShapeR() {
        return new FenceR(SIDE_SIZE);
    }

    @Override
    public Shape getShapeD() {
        return new FenceD(SIDE_SIZE);
    }

    @Override
    public Shape getShapeC() {
        return new FenceC(SIDE_SIZE);
    }
}
