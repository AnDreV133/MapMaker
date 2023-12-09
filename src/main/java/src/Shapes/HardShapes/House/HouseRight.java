package src.Shapes.HardShapes.House;

import src.Shapes.Shape;

public class HouseRight extends Shape {
    public HouseRight(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        rotateShape(new HouseUp(SIDE_SIZE), 90);
    }
}
