package src.Shapes.HardShapes.House;

import src.Shapes.HardShapes.HardShape;
import src.Shapes.Shape;

public class House extends HardShape {
    public House(int sideSize) {
        super(sideSize, new HouseFill(sideSize));
    }

    @Override
    public Shape getShapeRD() {
        return new HouseLeftUp(SIDE_SIZE);
    }

    @Override
    public Shape getShapeLRD() {
        return new HouseUp(SIDE_SIZE);
    }

    @Override
    public Shape getShapeLD() {
        return new HouseRightUp(SIDE_SIZE);
    }

    @Override
    public Shape getShapeRUD() {
        return new HouseLeft(SIDE_SIZE);
    }

    @Override
    public Shape getShapeLRUD() {
        return new HouseFill(SIDE_SIZE);
    }

    @Override
    public Shape getShapeLUD() {
        return new HouseRight(SIDE_SIZE);
    }

    @Override
    public Shape getShapeRU() {
        return new HouseLeftDown(SIDE_SIZE);
    }

    @Override
    public Shape getShapeLRU() {
        return new HouseDown(SIDE_SIZE);
    }

    @Override
    public Shape getShapeLU() {
        return new HouseRightDown(SIDE_SIZE);
    }
}
