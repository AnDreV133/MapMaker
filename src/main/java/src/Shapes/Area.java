package src.Shapes;

import java.awt.*;

public class Area extends Shape {
    public Area(int sideSize) {
        super(sideSize);
    }

    @Override
    public void drawMapObject() {
        g.setColor(new Color(128, 128, 128, 50));
        g.fillRect(0,0, getSideSize(), getSideSize());
    }
}
