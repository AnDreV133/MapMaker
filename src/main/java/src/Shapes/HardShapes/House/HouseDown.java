package src.Shapes.HardShapes.House;

import src.Shapes.Shape;

public class HouseDown extends Shape {
    public HouseDown(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        rotateShape(new HouseUp(SIDE_SIZE), 180);
    }
}
