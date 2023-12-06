package src.Shapes;

import java.awt.*;

public class Road extends Shape {
    public Road(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g.setColor(new Color(255, 228, 195));
        g.fillRect(0, 0, getSideSize(), getSideSize());
    }
}
