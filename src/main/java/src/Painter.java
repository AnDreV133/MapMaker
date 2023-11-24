package src;

import src.Shapes.*;
import src.Shapes.Shape;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Painter {
    private Graphics2D graphics;
    private BufferedImage image;
    private final BackgroundGenerator backgroundGenerator;
    private final ForegroundGenerator foregroundGenerator;
    private int heightInCell, widthInCell;
    private float freq = 0.5f;
    private final int sizeCell;
    private IdObject currentIdObject;

    public Painter(int widthInCell, int heightInCell, int sizeCell) {
        this.widthInCell = widthInCell;
        this.heightInCell = heightInCell;
        this.sizeCell = sizeCell;
        currentIdObject = IdObject.CELL;
        backgroundGenerator = new BackgroundGenerator(heightInCell, widthInCell);
        foregroundGenerator = new ForegroundGenerator(heightInCell, widthInCell);

        image = new BufferedImage(
            sizeCell * widthInCell,
            sizeCell * heightInCell,
            BufferedImage.TYPE_INT_ARGB
        );
        graphics = image.createGraphics();

        makeEmptyMap();
    }

    public BufferedImage getImage() {
        return image;
    }

    private Shape defineAsset(IdObject idObject) {
        switch (idObject) {
            case CELL -> {
                return new Cell(sizeCell);
            }
            case BLOCK -> {
                return new Block(sizeCell);
            }
            case STONE -> {
                return new Stone(sizeCell);
//            case FENCE ->return  new (sizeCell);
//            case TOWER ->return new (sizeCell);
//            case HOUSE -> return new (sizeCell);
//            case WATER ->return  new (sizeCell);
            }
            default -> {
                return new Empty(sizeCell);
            }
        }
    }

    public void resizeMap(int newWidthInCell, int newHeightInCell) {
        widthInCell = newWidthInCell;
        heightInCell = newHeightInCell;

        image = new BufferedImage(
            sizeCell * newWidthInCell,
            sizeCell * newHeightInCell,
            BufferedImage.TYPE_INT_ARGB
        );
        graphics = image.createGraphics();

        backgroundGenerator.resizeMatrix(newWidthInCell, newHeightInCell);
        foregroundGenerator.resizeMatrix(newWidthInCell, newHeightInCell);

        redrawBackgroundAndClearForeground();
    }

    public void makeRandomMap() {
        backgroundGenerator.makeRandomMatrix();

        redrawMap();
    }

    public void interpolateMap() {
        backgroundGenerator.interpolateMatrix();

        redrawMap();
    }



    public void makeEmptyMap() {
        backgroundGenerator.makeEmptyMatrix();
        foregroundGenerator.makeEmptyMatrix();

        redrawBackgroundAndClearForeground();
    }

    public void redrawBackgroundAndClearForeground() {
        for (int y = 0; y < heightInCell; y++) {
            for (int x = 0; x < widthInCell; x++) {
                drawObjByBackgroundMatrix(x, y);
            }
        }
    }

    public void setFreq(float freq) {
        this.freq = freq;

        redrawMap();
    }

    public void setCurrentIdObject(IdObject currentIdObject) {
        this.currentIdObject = currentIdObject;
    }

    public void redrawMap() {
        redrawMapByArea(
            new Point(0, 0),
            new Point(widthInCell - 1, heightInCell - 1)
        );
    }


    public void addShapesByAreaWithFill(Point begin, Point end) {
        foregroundGenerator.addShapesByAreaWithFill(begin, end, currentIdObject);

        redrawMapByArea(begin, end);
    }


    public void redrawMapByArea(Point begin, Point end) {
        for (int y = begin.getY(); y <= end.getY(); y++) {
            for (int x = begin.getX(); x <= end.getX(); x++) {
                drawShapeByMatrices(x, y);
            }
        }
    }

    private void drawShapeByMatrices(int x, int y) {
        if (backgroundGenerator.getMatrix().get(y).get(x) > freq) {
            drawShape(x, y, new Cell(sizeCell));
            drawShape(x, y, defineAsset(foregroundGenerator.getMatrix().get(y).get(x)));
        } else {
            drawShape(x, y, new Stone(sizeCell));
        }
    }

    private void drawObjByBackgroundMatrix(int x, int y) {
        if (backgroundGenerator.getMatrix().get(y).get(x) > freq) {
            drawShape(x, y, new Cell(sizeCell));
        } else {
            drawShape(x, y, new Stone(sizeCell));
        }
    }

    private void drawShape(int x, int y, Shape shape) {
        graphics.drawImage(
            shape.getImage(),
            x * sizeCell,
            y * sizeCell,
            null
        );
    }
}
