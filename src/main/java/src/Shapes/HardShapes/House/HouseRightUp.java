package src.Shapes.HardShapes.House;

import src.Shapes.Shape;

public class HouseRightUp extends Shape {
    public HouseRightUp(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g = new HouseLeftUp(SIDE_SIZE).getGraphic();
        g.rotate(3.1415 / 2 * 3);
    }
}
