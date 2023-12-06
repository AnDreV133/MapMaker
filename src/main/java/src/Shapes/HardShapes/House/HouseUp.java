package src.Shapes.HardShapes.House;

import src.Shapes.Shape;

public class HouseUp extends Shape {
    public HouseUp(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g.setColor(HouseColor.BASE);
        g.fillRect(0, 0, SIDE_SIZE, SIDE_SIZE);

        g.setColor(HouseColor.ROOF);
        g.fillRect(0, SIDE_SIZE / 2, SIDE_SIZE, SIDE_SIZE / 2);
    }
}
