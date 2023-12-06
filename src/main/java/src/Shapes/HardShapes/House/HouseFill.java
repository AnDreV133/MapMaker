package src.Shapes.HardShapes.House;

import src.Shapes.Shape;

public class HouseFill extends Shape {
    public HouseFill(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g.setColor(HouseColor.ROOF);
        g.fillRect(0, 0, SIDE_SIZE , SIDE_SIZE);
    }
}
