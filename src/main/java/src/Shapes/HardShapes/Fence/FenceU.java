package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceU extends Shape {
    public FenceU(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        rotateShape(new FenceL(SIDE_SIZE), 90);
    }
}
