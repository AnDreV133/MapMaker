package src.Shapes;

import java.awt.*;

public class House extends Shape {
    public House(int sideSize) {
        super(sideSize);
    }

    @Override
    public void drawMapObject() {
        g.setColor(new Color(222, 184, 135));
        g.fillRect(0, 0, getSideSize(), getSideSize());

        g.setColor(new Color(192, 154, 105));
        boolean shift = false;
        for (int i = 1; i <= getSideSize(); i *= 3) {
            for (int j = 1; j <= getSideSize(); j *= 4) {
                g.fillRect(i + (shift ? getSideSize() / 5 : 0), j, getSideSize() / 5, 1);
            }

            shift = !shift;
        }
    }
}
