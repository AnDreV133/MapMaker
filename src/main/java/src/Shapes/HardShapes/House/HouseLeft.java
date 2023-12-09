package src.Shapes.HardShapes.House;

import src.Shapes.Shape;

public class HouseLeft extends Shape {
    public HouseLeft(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        rotateShape(new HouseUp(SIDE_SIZE), 270);
    }
}
