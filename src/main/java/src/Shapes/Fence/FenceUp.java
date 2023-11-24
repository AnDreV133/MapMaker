package src.Shapes.Fence;

import src.Shapes.Shape;

public class FenceUp extends Shape {
    public FenceUp(int sideSize) {
        super(sideSize);
    }

    @Override
    protected void drawMapObject() {
        g.setColor(FenceColor.getColor());
        g.fillRect(getSideSize() / 3, 0, getSideSize() / 3, getSideSize() / 4);
    }
}
