package src.Shapes.Fence;


import src.Shapes.Shape;

public class FenceMiddle extends Shape {
    public FenceMiddle(int sideSize) {
        super(sideSize);
    }

    @Override
    public void drawMapObject() {
        g.setColor(FenceColor.getColor());
        g.fillRect(getSideSize() / 4, getSideSize() / 4, getSideSize() / 2, getSideSize() / 2);
    }
}
