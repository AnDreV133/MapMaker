package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceUD extends Shape {
    public FenceUD(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        rotateShape(new FenceLR(SIDE_SIZE), 90);
    }
}
