package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceRU extends Shape {
    public FenceRU(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        rotateShape(new FenceLU(SIDE_SIZE), 90);
    }
}
