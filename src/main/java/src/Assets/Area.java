package src.Assets;

import java.awt.*;

public class Area extends Asset{
    public Area(int sideSize) {
        super(sideSize);
    }

    @Override
    protected void drawMapObject() {
        g.setColor(new Color(128, 128, 128, 50));
        g.fillRect(0,0, getSideSize(), getSideSize());
    }
}
