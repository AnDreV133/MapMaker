package src.Shapes.Fence;

import src.Shapes.Shape;

public class FenceRight extends Shape {
    public FenceRight(int sideSize) {
        super(sideSize);
    }

    @Override
    protected void drawMapObject() {
        g.setColor(FenceColor.getColor());
        g.fillRect(getSideSize() / 4 * 3, getSideSize() / 3, getSideSize() / 4, getSideSize() / 3);
    }
}
