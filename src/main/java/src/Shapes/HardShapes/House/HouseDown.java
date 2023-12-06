package src.Shapes.HardShapes.House;

import src.Shapes.Shape;

public class HouseDown extends Shape {
    public HouseDown(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g = new HouseUp(SIDE_SIZE).getGraphic();
        g.rotate(3.1415);
    }
}