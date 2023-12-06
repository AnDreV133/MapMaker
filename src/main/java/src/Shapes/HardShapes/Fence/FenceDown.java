package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceDown extends Shape {
    public FenceDown(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g.setColor(FenceColor.getColor());
        g.fillRect(SIDE_SIZE / 3, SIDE_SIZE / 4 * 3, SIDE_SIZE / 3, SIDE_SIZE / 4);
    }
}
