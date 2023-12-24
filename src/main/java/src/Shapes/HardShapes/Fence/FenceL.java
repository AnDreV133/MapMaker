package src.Shapes.HardShapes.Fence;

import src.Shapes.Shape;

public class FenceL extends Shape {
    public FenceL(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        addShape(new FenceC(SIDE_SIZE));
        g.setColor(FenceColor.getColor());
        g.fillRect(0, SIDE_SIZE / 3, SIDE_SIZE / 4, SIDE_SIZE / 3);
    }
}
