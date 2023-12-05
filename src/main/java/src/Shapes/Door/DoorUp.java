package src.Shapes.Door;

import src.Shapes.House;
import src.Shapes.Shape;

import java.awt.*;

public class DoorUp extends Shape {
    public DoorUp(int sideSize) {
        super(sideSize);
    }

    @Override
    public void drawMapObject() {
        new House(getSideSize()).drawMapObject();
        g.setColor(new Color(93, 64, 26));
        g.fillRect(0, 0, getSideSize(), 1);
    }
}
