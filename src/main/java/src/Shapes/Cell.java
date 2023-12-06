package src.Shapes;

import java.awt.*;

public class Cell extends Shape {

    public Cell(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g.setColor(Color.white);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(Color.gray);
        g.drawRect(0, 0, image.getWidth()-1, image.getHeight()-1);
    }
}
