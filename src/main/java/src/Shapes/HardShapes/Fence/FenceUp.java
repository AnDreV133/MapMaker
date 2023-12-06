package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceUp extends Shape {
    public FenceUp(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g.setColor(FenceColor.getColor());
        g.fillRect(SIDE_SIZE / 3, 0, SIDE_SIZE / 3, SIDE_SIZE / 4);
    }
}
