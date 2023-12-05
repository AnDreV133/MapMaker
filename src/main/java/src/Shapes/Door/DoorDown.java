package src.Shapes.Door;

import src.Shapes.House;
import src.Shapes.Shape;

import java.awt.*;

public class DoorDown extends Shape {
    public DoorDown(int sideSize) {
        super(sideSize);
    }

    @Override
    public void drawMapObject() {
        new House(getSideSize()).drawMapObject();
        g.setColor(new Color(93, 64, 26));
        g.fillRect(0, getSideSize(), getSideSize(), 1);
    }
}
