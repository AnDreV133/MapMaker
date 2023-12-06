package src.Shapes.HardShapes.House;

import src.Shapes.Shape;

public class HouseLeftDown extends Shape {
    public HouseLeftDown(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g = new HouseLeftUp(SIDE_SIZE).getGraphic();
        g.rotate(3.1415 / 2);
    }
}
