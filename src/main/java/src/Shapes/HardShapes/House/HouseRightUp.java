package src.Shapes.HardShapes.House;

import src.Shapes.Shape;

public class HouseRightUp extends Shape {
    public HouseRightUp(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        rotateShape(new HouseLeftUp(SIDE_SIZE), 90);
    }
}
