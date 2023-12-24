package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceLRD extends Shape {
    public FenceLRD(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        rotateShape(new FenceRUD(SIDE_SIZE), 90);
    }
}
