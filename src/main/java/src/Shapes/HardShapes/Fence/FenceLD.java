package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceLD extends Shape {
    public FenceLD(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        rotateShape(new FenceLU(SIDE_SIZE), 270);
    }
}
