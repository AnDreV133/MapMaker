package src.Shapes.Fence;

import src.Shapes.Shape;

public class FenceLeft extends Shape {
    public FenceLeft(int sideSize) {
        super(sideSize);
    }

    @Override
    public void drawMapObject() {
        g.setColor(FenceColor.getColor());
        g.fillRect(0, getSideSize() / 3, getSideSize() / 4, getSideSize() / 3);
    }
}
