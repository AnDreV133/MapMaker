package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceLR extends Shape {
    public FenceLR(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g.setColor(FenceColor.getColor());
        g.fillRect(SIDE_SIZE / 4, SIDE_SIZE / 4, SIDE_SIZE / 2, SIDE_SIZE / 2);
        g.fillRect(0, SIDE_SIZE / 3, SIDE_SIZE, SIDE_SIZE / 3);
    }
}
