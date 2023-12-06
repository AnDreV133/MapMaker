package src.Shapes.HardShapes.Door;

import src.Shapes.HardShapes.House.HouseFill;
import src.Shapes.Shape;

import java.awt.*;

public class DoorDown extends Shape {
    public DoorDown(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        new HouseFill(getSideSize()).draw();
        g.setColor(new Color(93, 64, 26));
        g.fillRect(0, getSideSize(), getSideSize(), 1);
    }
}
