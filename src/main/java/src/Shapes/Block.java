package src.Shapes;

import java.awt.*;

public class Block extends Shape {
    public Block(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g.setColor(new Color(228, 157, 36));
        g.fillRect(0, 0, getSideSize(), getSideSize());
//        g.setColor(new Color(150, 150, 150));
//        g.drawRect(0, 0, getSideSize()-1, getSideSize()-1);
    }
}
