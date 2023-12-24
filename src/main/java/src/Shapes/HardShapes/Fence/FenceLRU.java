package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceLRU extends Shape {
    public FenceLRU(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        rotateShape(new FenceRUD(SIDE_SIZE), 270);
    }
}
