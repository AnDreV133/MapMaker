package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceLRUD extends Shape {
    public FenceLRUD(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        addShape(new FenceLR(SIDE_SIZE));
        addShape(new FenceUD(SIDE_SIZE));
    }
}
