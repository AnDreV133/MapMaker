package src.Shapes.HardShapes.House;

import src.Shapes.Shape;

import java.awt.*;

public class HouseLeftUp extends Shape {
    public HouseLeftUp(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g.setColor(HouseColor.BASE);
        g.fillRect(0, 0, SIDE_SIZE, SIDE_SIZE);

        g.setColor(HouseColor.ROOF);
        g.fillRect(SIDE_SIZE / 2, SIDE_SIZE / 2, SIDE_SIZE / 2, SIDE_SIZE / 2);
    }
}
