package src.Shapes;

import java.awt.*;

public class Road extends Shape {
    public Road(int sideSize) {
        super(sideSize);
    }

    @Override
    public void drawMapObject() {
        g.setColor(new Color(210, 180, 140));
        g.fillRect(0, 0, getSideSize(), getSideSize());
    }
}
