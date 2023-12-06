package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceLeft extends Shape {
    public FenceLeft(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g.setColor(FenceColor.getColor());
        g.fillRect(0, SIDE_SIZE / 3, SIDE_SIZE / 4, SIDE_SIZE / 3);
    }
}
