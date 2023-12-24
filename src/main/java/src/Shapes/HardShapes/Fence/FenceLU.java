package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceLU extends Shape {
    public FenceLU(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g.setColor(FenceColor.getColor());
        g.fillRect(SIDE_SIZE / 4, SIDE_SIZE / 4, SIDE_SIZE / 2, SIDE_SIZE / 2);
        g.fillRect(0, SIDE_SIZE / 3, SIDE_SIZE / 4, SIDE_SIZE / 3);
        g.fillRect(SIDE_SIZE / 3, 0, SIDE_SIZE / 3, SIDE_SIZE / 4);
    }
}
