package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceLUD extends Shape {
    public FenceLUD(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        rotateShape(new FenceRUD(SIDE_SIZE), 180);
    }
}
