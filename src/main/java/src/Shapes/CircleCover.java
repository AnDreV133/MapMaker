package src.Shapes;

import java.awt.*;

public class CircleCover extends Shape {
    public CircleCover(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g.setColor(new Color(128, 64, 48));
        g.fillOval(0, 0, getSideSize(), getSideSize());
    }
}
