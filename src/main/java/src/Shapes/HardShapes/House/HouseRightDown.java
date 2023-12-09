package src.Shapes.HardShapes.House;

import src.Shapes.Shape;

public class HouseRightDown extends Shape {
    public HouseRightDown(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        rotateShape(new HouseLeftUp(SIDE_SIZE), 180);
    }
}
