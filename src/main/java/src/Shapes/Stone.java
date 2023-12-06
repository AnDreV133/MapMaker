package src.Shapes;

import java.awt.*;

public class Stone extends Shape {
    public Stone(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getSideSize(), getSideSize());
//        g.setColor(new Color(150, 150, 150));
//        g.drawRect(0, 0, getSideSize()-1, getSideSize()-1);
    }
}
