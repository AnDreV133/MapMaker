package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceRUD extends Shape {
    public FenceRUD(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g.setColor(FenceColor.getColor());
        addShape(new FenceUD(SIDE_SIZE));
        addShape(new FenceR(SIDE_SIZE));
    }
}
