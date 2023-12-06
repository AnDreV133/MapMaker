package src.Shapes.HardShapes.Door;

import src.Shapes.HardShapes.House.HouseFill;
import src.Shapes.Shape;

import java.awt.*;

public class DoorRight extends Shape {
    public DoorRight(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        new HouseFill(getSideSize()).draw();
        g.setColor(new Color(93, 64, 26));
        g.fillRect(getSideSize(), 0, 1, getSideSize());
    }
}
