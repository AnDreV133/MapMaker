package src.Assets;

import java.awt.*;

public class ShadedCell extends Asset {
    public ShadedCell(int sideSize) {
        super(sideSize);
    }

    @Override
    protected void drawMapObject() {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getSideSize(), getSideSize());
//        g.setColor(new Color(150, 150, 150));
//        g.drawRect(0, 0, getSideSize()-1, getSideSize()-1);
    }
}
