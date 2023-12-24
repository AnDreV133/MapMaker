package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceRD extends Shape {
    public FenceRD(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        rotateShape(new FenceLU(SIDE_SIZE), 180);
    }
}
