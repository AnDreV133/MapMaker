package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceR extends Shape {
    public FenceR(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        rotateShape(new FenceL(SIDE_SIZE), 180);
    }
}
