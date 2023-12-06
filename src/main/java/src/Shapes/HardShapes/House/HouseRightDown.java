package src.Shapes.HardShapes.House;

import src.Shapes.Shape;

public class HouseRightDown extends Shape {
    public HouseRightDown(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g = new HouseLeftUp(SIDE_SIZE).getGraphic();
        g.rotate(3.1415);
    }
}
