package src;

import src.Assets.Asset;
import src.Assets.Cell;
import src.Assets.ShadedCell;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LandscapePainter {
     private final int SIZE_CELL = 12;
     private final LandscapeGenerator generator = new LandscapeGenerator(SettingsPanel.DEFAULT_SIZE_MAP, SettingsPanel.DEFAULT_SIZE_MAP);
     private BufferedImage image;
     private Graphics2D graphics;

     private void addObject(Asset a, int x, int y) {
        graphics.drawImage(a.getMapObject(), x * SIZE_CELL, y * SIZE_CELL, null);
    }

     public BufferedImage getResizeImage(int newWidthInCell, int newHeightInCell, float blockFreq) {
        generator.resize(newWidthInCell, newHeightInCell);
        return getImageFromMap(blockFreq);
    }

     public BufferedImage getInterpolatedImage(float blockFreq) {
        generator.getInterpolateMatrix();
        return getImageFromMap(blockFreq);
    }

     public BufferedImage getRandomDotsImage(float blockFreq) {
        generator.getRandomMatrix();
        return getImageFromMap(blockFreq);
    }

     public BufferedImage getEmptyImage() {
        generator.getEmptyMatrix();
        return getImageFromMap(0.1f);
    }

     public BufferedImage getImageFromMap(float blockFreq) {
        image = new BufferedImage(
            SIZE_CELL * generator.getWidthInCell(),
            SIZE_CELL * generator.getHeightInCell(),
            BufferedImage.TYPE_INT_ARGB
        );
        graphics = image.createGraphics();

        ArrayList<ArrayList<Float>> matrix = generator.getMap();

        for (int y = 0; y < generator.getHeightInCell(); y++) {
            for (int x = 0; x < generator.getWidthInCell(); x++) {
                if (matrix.get(y).get(x) < blockFreq)
                    addObject(new ShadedCell(SIZE_CELL), x, y);
                else
                    addObject(new Cell(SIZE_CELL), x, y);
            }
        }

        return image;
    }

// todo

     public BufferedImage getImage() {
        return image;
    }
}
