package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceRight extends Shape {
    public FenceRight(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g.setColor(FenceColor.getColor());
        g.fillRect(SIDE_SIZE / 4 * 3, SIDE_SIZE / 3, SIDE_SIZE / 4, SIDE_SIZE / 3);
    }
}
