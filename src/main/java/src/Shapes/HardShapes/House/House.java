package src.Shapes.HardShapes.House;

import src.Shapes.HardShapes.HardShape;
import src.Shapes.Shape;

public class House extends HardShape {
    public House(int sideSize) {
        super(sideSize);
    }

    @Override
    public Shape getShapeLU() {
        return new HouseLeftUp(SIDE_SIZE);
    }

    @Override
    public Shape getShapeU() {
        return new HouseUp(SIDE_SIZE);
    }

    @Override
    public Shape getShapeRU() {
        return new HouseRightUp(SIDE_SIZE);
    }

    @Override
    public Shape getShapeL() {
        return new HouseLeft(SIDE_SIZE);
    }

    @Override
    public Shape getShapeFill() {
        return new HouseFill(SIDE_SIZE);
    }

    @Override
    public Shape getShapeR() {
        return new HouseRight(SIDE_SIZE);
    }

    @Override
    public Shape getShapeLD() {
        return new HouseLeftDown(SIDE_SIZE);
    }

    @Override
    public Shape getShapeD() {
        return new HouseDown(SIDE_SIZE);
    }

    @Override
    public Shape getShapeRD() {
        return new HouseRightDown(SIDE_SIZE);
    }

    @Override
    public Shape getShapeH() {
        return new HouseFill(SIDE_SIZE);
    }

    @Override
    public Shape getShapeV() {
        return new HouseFill(SIDE_SIZE);

    }

    @Override
    public Shape getShapeOnlyU() {
        return new HouseFill(SIDE_SIZE);

    }

    @Override
    public Shape getShapeOnlyL() {
        return new HouseFill(SIDE_SIZE);

    }

    @Override
    public Shape getShapeOnlyR() {
        return new HouseFill(SIDE_SIZE);

    }

    @Override
    public Shape getShapeOnlyD() {
        return new HouseFill(SIDE_SIZE);

    }

    @Override
    public Shape getShapeOnlyC() {
        return new HouseFill(SIDE_SIZE);
    }
}
