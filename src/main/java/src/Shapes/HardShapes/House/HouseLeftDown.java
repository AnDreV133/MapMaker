package src.Shapes.HardShapes.House;

import src.Shapes.Shape;

public class HouseLeftDown extends Shape {
    public HouseLeftDown(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        rotateShape(new HouseLeftUp(SIDE_SIZE), 270);
    }
}
