package src;

import src.Shapes.*;
import src.Shapes.Shape;
import src.Shapes.Fence.*;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Painter {
    private final BackgroundGenerator backgroundGenerator;
    private final ForegroundGenerator foregroundGenerator;
    private final int sizeCell;
    private Graphics2D graphics;
    private BufferedImage image;
    private int heightInCell, widthInCell;
    private float freq = 0.5f;
    private ShapeId currentShapeId;

    public Painter(int widthInCell, int heightInCell, int sizeCell) {
        this.widthInCell = widthInCell;
        this.heightInCell = heightInCell;
        this.sizeCell = sizeCell;
        currentShapeId = ShapeId.CELL;
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

    private Shape defineAsset(ShapeId shapeId) {
        switch (shapeId) {
            case CELL -> {
                return new Cell(sizeCell);
            }
            case BLOCK -> {
                return new Block(sizeCell);
            }
            case STONE -> {
                return new Stone(sizeCell);
            }
            case FENCE -> {
                return new FenceUp(sizeCell);
            }

//            case TOWER ->return new (sizeCell);
//            case HOUSE -> return new (sizeCell);
//            case WATER ->return  new (sizeCell);
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
                drawShapeByBackgroundMatrix(x, y);
            }
        }
    }

    public void setFreq(float freq) {
        this.freq = freq;

        redrawMap();
    }

    public void setCurrentShapeId(ShapeId currentShapeId) {
        this.currentShapeId = currentShapeId;
    }

    public void redrawMap() {
        redrawMapByArea(
                new Point(0, 0),
                new Point(widthInCell - 1, heightInCell - 1)
        );
    }


    public void drawShapes(Point begin, Point end) {
        foregroundGenerator.analyzeByIdAndAddShapes(begin, end, currentShapeId);

        redrawMapByArea(begin, end);
    }


    public void redrawMapByArea(Point begin, Point end) {
        for (int y = begin.getY(); y <= end.getY(); y++) {
            for (int x = begin.getX(); x <= end.getX(); x++) {
                if (backgroundGenerator.getMatrix().get(y).get(x) > freq) {
                    drawShape(x, y, new Cell(sizeCell));
                    defineAndDrawShape(x, y, foregroundGenerator.getMatrix().get(y).get(x));
                } else {
                    foregroundGenerator.getMatrix().get(y).set(x, ShapeId.EMPTY);
                    drawShape(x, y, new Stone(sizeCell));
                }
            }
        }
    }

    public void forceDrawShapes(Point begin, Point end) {
        foregroundGenerator.analyzeByIdAndAddShapes(begin, end, currentShapeId);

        forceRedrawMapByArea(begin, end);
    }

    public void forceRedrawMapByArea(Point begin, Point end) {
        for (int y = begin.getY(); y <= end.getY(); y++) {
            for (int x = begin.getX(); x <= end.getX(); x++) {
                drawShape(x, y, new Cell(sizeCell));
                defineAndDrawShape(x, y, foregroundGenerator.getMatrix().get(y).get(x));
            }
        }
    }

    private void defineAndDrawShape(int x, int y, ShapeId shapeId) {
        switch (shapeId) {
            case FENCE -> drawShapeWithOrientation(x, y, shapeId, new FenceMiddle(sizeCell),
                    new FenceLeft(sizeCell), new FenceRight(sizeCell),
                    new FenceUp(sizeCell), new FenceDown(sizeCell));
            default -> drawShape(x, y, defineAsset(foregroundGenerator.getMatrix().get(y).get(x)));
        }
    }

    private void drawShapeWithOrientation(int x, int y, ShapeId shapeId, Shape middleShape,
                                          Shape leftShape, Shape rightShape,
                                          Shape upShape, Shape downShape) {
        drawShape(x, y, middleShape);
        if (foregroundGenerator.getMatrix().get(y).get(x - 1) == shapeId)
            drawShape(x, y, leftShape);
        if (foregroundGenerator.getMatrix().get(y).get(x + 1) == shapeId)
            drawShape(x, y, rightShape);
        if (foregroundGenerator.getMatrix().get(y - 1).get(x) == shapeId)
            drawShape(x, y, upShape);
        if (foregroundGenerator.getMatrix().get(y + 1).get(x) == shapeId)
            drawShape(x, y, downShape);

    }

    private void drawShapeByBackgroundMatrix(int x, int y) {
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
