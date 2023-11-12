package src.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Cell extends Asset {

    public Cell(int sideSize) {
        super(sideSize);
    }

    @Override
    public void drawMapObject() {
        g.setColor(Color.white);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(Color.gray);
        g.drawRect(0, 0, image.getWidth()-1, image.getHeight()-1);
    }
}
