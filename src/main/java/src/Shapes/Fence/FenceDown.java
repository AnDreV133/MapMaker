package src.Shapes.Fence;

import src.Shapes.Shape;

public class FenceDown extends Shape {
    public FenceDown(int sideSize) {
        super(sideSize);
    }

    @Override
    public void drawMapObject() {
        g.setColor(FenceColor.getColor());
        g.fillRect(getSideSize() / 3, getSideSize() / 4 * 3, getSideSize() / 3, getSideSize() / 4);
    }
}
