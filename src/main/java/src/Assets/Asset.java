package src.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Asset {
    protected Graphics2D g;
    private final int sideSize;
    protected BufferedImage image;

    public Asset(int sideSize) {
        this.sideSize = sideSize;
        image = new BufferedImage(sideSize, sideSize, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        drawMapObject();
    }

    protected abstract void drawMapObject();

    public int getSideSize() {
        return sideSize;
    }

    public BufferedImage getMapObject() {
        return image;
    }
}
