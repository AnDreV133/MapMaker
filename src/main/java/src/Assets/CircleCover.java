package src.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CircleCover extends Asset{
    public CircleCover(int sideSize) {
        super(sideSize);
    }

    @Override
    public void drawMapObject() {
        g.setColor(new Color(128, 64, 48));
        g.fillOval(0, 0, getSideSize(), getSideSize());
    }
}
