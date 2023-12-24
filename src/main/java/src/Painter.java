package src;

import src.Background.BackgroundGenerator;
import src.Foreground.ForegroundGenerator;
import src.Shapes.*;
import src.Shapes.HardShapes.Fence.*;
import src.Shapes.HardShapes.HardShape;
import src.Shapes.HardShapes.House.House;
import src.Shapes.Shape;
import src.Shapes.HardShapes.House.HouseFill;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Painter {
    private final BackgroundGenerator backgroundGenerator;
    private final ForegroundGenerator foregroundGenerator;
    private final int sizeCell;
    private final UndoRedo undoRedo;
    private Graphics2D graphics;
    private BufferedImage image;
    private int heightInCell, widthInCell;
    private ShapeId currentShapeId;

    public Painter(int widthInCell, int heightInCell, int sizeCell) {
        this.widthInCell = widthInCell;
        this.heightInCell = heightInCell;
        this.sizeCell = sizeCell;
        currentShapeId = ShapeId.CELL;
        backgroundGenerator = new BackgroundGenerator(widthInCell, heightInCell);
        foregroundGenerator = new ForegroundGenerator(widthInCell, heightInCell);
        undoRedo = new UndoRedo(backgroundGenerator, foregroundGenerator);

        updateBufferOfImage();

        makeEmptyMap();
    }

    private void updateBufferOfImage() {
        image = new BufferedImage(
                sizeCell * widthInCell,
                sizeCell * heightInCell,
                BufferedImage.TYPE_INT_ARGB
        );
        graphics = image.createGraphics();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void resizeMap(int newWidthInCell, int newHeightInCell) {
        widthInCell = newWidthInCell;
        heightInCell = newHeightInCell;

        updateBufferOfImage();

        backgroundGenerator.resizeMatrix(newWidthInCell, newHeightInCell);
        foregroundGenerator.resizeMatrix(newWidthInCell, newHeightInCell);

        undoRedo.add(backgroundGenerator, foregroundGenerator);

        redrawMap();
    }

    public void makeRandomMap() {
        backgroundGenerator.makeRandomMatrix();

        undoRedo.add(backgroundGenerator);

        redrawMap();
    }

    public void interpolateMap() {
        backgroundGenerator.interpolateMatrix();

        undoRedo.add(backgroundGenerator);

        redrawMap();
    }

    public void makeEmptyMap() {
        backgroundGenerator.makeEmptyMatrix();
        foregroundGenerator.makeEmptyMatrix();

        undoRedo.add(backgroundGenerator, foregroundGenerator);

        redrawMap();
    }

    public void setFreq(float freq) {
        backgroundGenerator.setFreq(freq);

        undoRedo.add(backgroundGenerator);

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
        correctingPoints(begin, end);
        foregroundGenerator.addShapes(begin, end, currentShapeId);

        undoRedo.add(foregroundGenerator);

        redrawMapByArea(begin, end);
    }

    public void drawShapesForce(Point begin, Point end) {
        correctingPoints(begin, end);

        for (int y = begin.getY(); y <= end.getY(); y++)
            for (int x = begin.getX(); x <= end.getX(); x++)
                backgroundGenerator.setVal(x, y, currentShapeId == ShapeId.STONE ? 0.0f : 1.0f);

        foregroundGenerator.addShapes(begin, end, currentShapeId);

        undoRedo.add(backgroundGenerator, foregroundGenerator);

        redrawMapByArea(begin, end);
    }

    public void redrawMapByArea(Point begin, Point end) {
        for (int y = begin.getY(); y <= end.getY(); y++) {
            for (int x = begin.getX(); x <= end.getX(); x++) {
                if (backgroundGenerator.getVal(x, y) > backgroundGenerator.getFreq()) {
                    drawShape(x, y, new Cell(sizeCell));
                    drawShapeWithDefine(x, y);
                } else {
                    drawShape(x, y, new Stone(sizeCell));
                }
            }
        }
    }

    public void undo() {
        var pairGrounds = undoRedo.undo();

        if (widthInCell != pairGrounds.background().getWidth()
                || heightInCell != pairGrounds.background().getHeight()) {
            widthInCell = pairGrounds.background().getWidth();
            heightInCell = pairGrounds.background().getHeight();

            updateBufferOfImage();
        }

        backgroundGenerator.setBackground(pairGrounds.background());
        foregroundGenerator.setForeground(pairGrounds.foreground());

        redrawMap();
    }

    public void redo() {
        var pairGrounds = undoRedo.redo();

        if (widthInCell != pairGrounds.background().getWidth()
                || heightInCell != pairGrounds.background().getHeight()) {
            widthInCell = pairGrounds.background().getWidth();
            heightInCell = pairGrounds.background().getHeight();

            updateBufferOfImage();
        }

        backgroundGenerator.setBackground(pairGrounds.background());
        foregroundGenerator.setForeground(pairGrounds.foreground());

        redrawMap();
    }

    private void drawShapeWithDefine(int x, int y) {
        switch (foregroundGenerator.getVal(x, y)) {
            case FENCE -> drawShapeByOrientation(x, y, ShapeId.FENCE, new Fence(sizeCell));
            case HOUSE -> drawShapeByOrientation(x, y, ShapeId.HOUSE, new House(sizeCell));
            default -> drawShape(x, y, defineShape(foregroundGenerator.getVal(x, y)));
        }
    }


    private void drawShapeByOrientation(int x, int y, ShapeId shapeId, HardShape hardShape) {
        boolean isShapeOnLeft = false;
        boolean isShapeOnRight = false;
        boolean isShapeOnUp = false;
        boolean isShapeOnDown = false;
        if (foregroundGenerator.getVal(x - 1, y) == shapeId)
            isShapeOnLeft = true;
        if (foregroundGenerator.getVal(x + 1, y) == shapeId)
            isShapeOnRight = true;
        if (foregroundGenerator.getVal(x, y - 1) == shapeId)
            isShapeOnUp = true;
        if (foregroundGenerator.getVal(x, y + 1) == shapeId)
            isShapeOnDown = true;

        if (isShapeOnLeft && isShapeOnRight && isShapeOnUp && isShapeOnDown)
            drawShape(x, y, hardShape.getShapeLRUD());
        else if (isShapeOnLeft && isShapeOnRight && isShapeOnUp && !isShapeOnDown)
            drawShape(x, y, hardShape.getShapeLRU());
        else if (isShapeOnLeft && isShapeOnRight && !isShapeOnUp && isShapeOnDown)
            drawShape(x, y, hardShape.getShapeLRD());
        else if (isShapeOnLeft && !isShapeOnRight && isShapeOnUp && isShapeOnDown)
            drawShape(x, y, hardShape.getShapeLUD());
        else if (!isShapeOnLeft && isShapeOnRight && isShapeOnUp && isShapeOnDown)
            drawShape(x, y, hardShape.getShapeRUD());
        else if (isShapeOnLeft && isShapeOnRight && !isShapeOnUp && !isShapeOnDown)
            drawShape(x, y, hardShape.getShapeLR());
        else if (!isShapeOnLeft && !isShapeOnRight && isShapeOnUp && isShapeOnDown)
            drawShape(x, y, hardShape.getShapeUD());
        else if (isShapeOnLeft && !isShapeOnRight && isShapeOnUp && !isShapeOnDown)
            drawShape(x, y, hardShape.getShapeLU());
        else if (isShapeOnLeft && !isShapeOnRight && !isShapeOnUp && isShapeOnDown)
            drawShape(x, y, hardShape.getShapeLD());
        else if (!isShapeOnLeft && isShapeOnRight && isShapeOnUp && !isShapeOnDown)
            drawShape(x, y, hardShape.getShapeRU());
        else if (!isShapeOnLeft && isShapeOnRight && !isShapeOnUp && isShapeOnDown)
            drawShape(x, y, hardShape.getShapeRD());
        else if (!isShapeOnLeft && !isShapeOnRight && !isShapeOnUp && isShapeOnDown)
            drawShape(x, y, hardShape.getShapeD());
        else if (!isShapeOnLeft && !isShapeOnRight && isShapeOnUp && !isShapeOnDown)
            drawShape(x, y, hardShape.getShapeU());
        else if (!isShapeOnLeft && isShapeOnRight && !isShapeOnUp && !isShapeOnDown)
            drawShape(x, y, hardShape.getShapeR());
        else if (isShapeOnLeft && !isShapeOnRight && !isShapeOnUp && !isShapeOnDown)
            drawShape(x, y, hardShape.getShapeL());
        else
            drawShape(x, y, hardShape.getShapeC());
    }

    private void drawShape(int x, int y, Shape shape) {
        try {
            graphics.drawImage(
                    shape.getImage(),
                    x * sizeCell,
                    y * sizeCell,
                    null
            );
        } catch (Exception ignored) {
        }
    }

    private Shape defineShape(ShapeId shapeId) {
        return switch (shapeId) {
            case CELL -> new Cell(sizeCell);
            case BLOCK -> new Block(sizeCell);
            case STONE -> new Stone(sizeCell);
            case HOUSE -> new HouseFill(sizeCell);
            case ROAD -> new Road(sizeCell);
            default -> new Empty(sizeCell);

//            case TOWER ->return new (sizeCell);
//            case WATER ->return  new (sizeCell);
        };
    }

    private void correctingPoints(Point begin, Point end) {
        movePointToBound(begin);
        movePointToBound(end);

        if (begin.equals(end) || begin.lessThan(end)) {
            return;
        }

        // e . .
        // . . .
        // . . b
        if (begin.biggerOrEqualThan(end)) {
            Point temp = new Point(begin.getX(), begin.getY());
            begin.setXY(end.getX(), end.getY());
            end.setXY(temp.getX(), temp.getY());

            // . . b
            // . . .
            // e . .
        } else if (begin.xBiggerThan(end) && begin.yLessThan(end)) {
            int temp = begin.getX();
            begin.setX(end.getX());
            end.setX(temp);

            // . . e
            // . . .
            // b . .
        } else if (begin.xLessThan(end) && begin.yBiggerThan(end)) {
            int temp = begin.getY();
            begin.setY(end.getY());
            end.setY(temp);
        }
    }

    private void movePointToBound(Point point) {
        if (point.getX() < 0)
            point.setX(0);
        if (point.getX() >= foregroundGenerator.getWidth())
            point.setX(foregroundGenerator.getWidth() - 1);
        if (point.getY() < 0)
            point.setY(0);
        if (point.getY() >= foregroundGenerator.getHeight())
            point.setY(foregroundGenerator.getHeight() - 1);
    }
}
