package src.Shapes.HardShapes.House;

import src.Shapes.Shape;

public class HouseLeft extends Shape {
    public HouseLeft(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g = new HouseUp(SIDE_SIZE).getGraphic();
        g.rotate(3.1415 / 2);
    }
}
