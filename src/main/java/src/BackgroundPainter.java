package src;

import src.Assets.Asset;
import src.Assets.Cell;
import src.Assets.Stone;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BackgroundPainter {
    private final BackgroundGenerator generator;
    private BufferedImage image;
    private Graphics2D graphics;
    private final int sizeCell;

    public BackgroundPainter(int heightInCell, int widthInCell, int sizeCell) {
        generator = new BackgroundGenerator(heightInCell, widthInCell);
        this.sizeCell = sizeCell;
    }

    private void addObject(Asset a, int x, int y) {
        graphics.drawImage(a.getImage(), x * sizeCell, y * sizeCell, null);
    }

    public BufferedImage getResizeImage(int newWidthInCell, int newHeightInCell, float blockFreq) {
        generator.resizeMatrix(newWidthInCell, newHeightInCell);
        return getImageFromMap(blockFreq);
    }

    public BufferedImage getInterpolatedImage(float blockFreq) {
        generator.interpolateMatrix();
        return getImageFromMap(blockFreq);
    }

    public BufferedImage getRandomDotsImage(float blockFreq) {
        generator.makeRandomMatrix();
        return getImageFromMap(blockFreq);
    }

    public BufferedImage makeEmptyMap() {
        generator.makeEmptyMatrix();
        return getImageFromMap(0.1f);
    }

    public BufferedImage getImageFromMap(float blockFreq) {
        image = new BufferedImage(
            sizeCell * generator.getWidthInCell(),
            sizeCell * generator.getHeightInCell(),
            BufferedImage.TYPE_INT_ARGB
        );
        graphics = image.createGraphics();

        ArrayList<ArrayList<Float>> matrix = generator.getMatrix();

        for (int y = 0; y < generator.getHeightInCell(); y++) {
            for (int x = 0; x < generator.getWidthInCell(); x++) {
                if (matrix.get(y).get(x) < blockFreq)
                    addObject(new Stone(sizeCell), x, y);
                else
                    addObject(new Cell(sizeCell), x, y);
            }
        }

        return image;
    }

// todo

    public BufferedImage getImage() {
        return image;
    }
}
