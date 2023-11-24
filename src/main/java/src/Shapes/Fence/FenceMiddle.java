package src.Shapes.Fence;


import src.Shapes.Shape;

import java.awt.*;

import static java.awt.Color.BLUE;

public class FenceMiddle extends Shape {
    public FenceMiddle(int sideSize) {
        super(sideSize);
    }

    @Override
    protected void drawMapObject() {
        g.setColor(FenceColor.getColor());
        g.fillRect(getSideSize() / 4, getSideSize() / 4, getSideSize() / 2, getSideSize() / 2);
    }
}
