package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceD extends Shape {
    public FenceD(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        rotateShape(new FenceL(SIDE_SIZE), 270);
    }
}
