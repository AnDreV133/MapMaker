package src.Shapes.HardShapes.Fence;


import src.Shapes.Shape;

public class FenceCentral extends Shape {
    public FenceCentral(int sideSize) {
        super(sideSize);
    }

    @Override
    public void draw() {
        g.setColor(FenceColor.getColor());
        g.fillRect(SIDE_SIZE / 4, SIDE_SIZE / 4, SIDE_SIZE / 2, SIDE_SIZE / 2);
    }
}
