package src.Assets;

import java.awt.*;

public class RectangleCover extends Asset {

    public RectangleCover(int sideSize) {
        super(sideSize);
    }

    @Override
    public void drawMapObject() {
        g.setColor(Color.black);
        g.fillRect(0, 0, getSideSize(), getSideSize());
    }
}
