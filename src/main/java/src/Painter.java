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

        redrawBackground();
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

        redrawBackground();
    }

    public void redrawBackground() { // todo возможно убрать
        for (int y = 0; y < heightInCell; y++) {
            for (int x = 0; x < widthInCell; x++) {
                drawShapeByBackgroundMatrix(x, y);
            }
        }
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
        foregroundGenerator.addShapes(begin, end, currentShapeId);
        if (currentShapeId == ShapeId.CELL) {
            for (int y = begin.getY(); y <= end.getY(); y++)
                for (int x = begin.getX(); x <= end.getX(); x++)
                    backgroundGenerator.setVal(x, y, (float) (backgroundGenerator.getFreq() + (1 - backgroundGenerator.getFreq()) * Math.random()));

            undoRedo.add(backgroundGenerator, foregroundGenerator);
        } else
            undoRedo.add(foregroundGenerator);

        redrawMapByArea(begin, end);
    }


    public void redrawMapByArea(Point begin, Point end) {
        for (int y = begin.getY(); y <= end.getY(); y++) {
            for (int x = begin.getX(); x <= end.getX(); x++) {
                if (backgroundGenerator.getVal(x, y) > backgroundGenerator.getFreq()) {
                    drawShape(x, y, new Cell(sizeCell));
                    drawShapeWithDefine(x, y, foregroundGenerator.getVal(x, y));
                } else {
                    drawShape(x, y, new Stone(sizeCell));
                }
            }
        }
    }

    public void forceDrawShapes(Point begin, Point end) {
        for (int y = begin.getY(); y <= end.getY(); y++)
            for (int x = begin.getX(); x <= end.getX(); x++)
                backgroundGenerator.setVal(x, y, currentShapeId == ShapeId.STONE ? 0.0f : 1.0f);

        foregroundGenerator.addShapes(begin, end, currentShapeId);

        undoRedo.add(backgroundGenerator, foregroundGenerator);

        redrawMapByArea(begin, end);
    }

    public void undo(){
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

    public void redo(){
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

    private void drawShapeWithDefine(int x, int y, ShapeId shapeId) {
        switch (shapeId) {
            case FENCE -> drawShapeWithOrientationModify(x, y, ShapeId.FENCE, new FenceCentral(sizeCell),
                    new FenceLeft(sizeCell), new FenceRight(sizeCell),
                    new FenceUp(sizeCell), new FenceDown(sizeCell));
            case HOUSE -> drawShapeWithOrientation(x, y, ShapeId.HOUSE, new House(sizeCell));
            default -> drawShape(x, y, defineShape(foregroundGenerator.getVal(x, y)));
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

    private void drawShapeWithOrientation(int x, int y, ShapeId shapeId, HardShape hardShape) {
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
            drawShape(x, y, hardShape.getShapeFill());
        else if (isShapeOnLeft && isShapeOnRight && isShapeOnUp && !isShapeOnDown)
            drawShape(x, y, hardShape.getShapeD());
        else if (isShapeOnLeft && isShapeOnRight && !isShapeOnUp && isShapeOnDown)
            drawShape(x, y, hardShape.getShapeU());
        else if (isShapeOnLeft && !isShapeOnRight && isShapeOnUp && isShapeOnDown)
            drawShape(x, y, hardShape.getShapeR());
        else if (!isShapeOnLeft && isShapeOnRight && isShapeOnUp && isShapeOnDown)
            drawShape(x, y, hardShape.getShapeL());
        else if (isShapeOnLeft && isShapeOnRight && !isShapeOnUp && !isShapeOnDown)
            drawShape(x, y, hardShape.getShapeH());
        else if (!isShapeOnLeft && !isShapeOnRight && isShapeOnUp && isShapeOnDown)
            drawShape(x, y, hardShape.getShapeV());
        else if (isShapeOnLeft && !isShapeOnRight && isShapeOnUp && !isShapeOnDown)
            drawShape(x, y, hardShape.getShapeRD());
        else if (isShapeOnLeft && !isShapeOnRight && !isShapeOnUp && isShapeOnDown)
            drawShape(x, y, hardShape.getShapeRU());
        else if (!isShapeOnLeft && isShapeOnRight && isShapeOnUp && !isShapeOnDown)
            drawShape(x, y, hardShape.getShapeLD());
        else if (!isShapeOnLeft && isShapeOnRight && !isShapeOnUp && isShapeOnDown)
            drawShape(x, y, hardShape.getShapeLU());
        else if (!isShapeOnLeft && !isShapeOnRight && !isShapeOnUp && isShapeOnDown)
            drawShape(x, y, hardShape.getShapeOnlyD());
        else if (!isShapeOnLeft && !isShapeOnRight && isShapeOnUp && !isShapeOnDown)
            drawShape(x, y, hardShape.getShapeOnlyU());
        else if (!isShapeOnLeft && isShapeOnRight && !isShapeOnUp && !isShapeOnDown)
            drawShape(x, y, hardShape.getShapeOnlyR());
        else if (isShapeOnLeft && !isShapeOnRight && !isShapeOnUp && !isShapeOnDown)
            drawShape(x, y, hardShape.getShapeOnlyL());
        else
            drawShape(x, y, hardShape.getShapeOnlyC());
    }

    private boolean drawShapeWithOrientationModify(int x, int y, ShapeId neighborShapeId, Shape middleShape,
                                                   Shape leftShape, Shape rightShape,
                                                   Shape upShape, Shape downShape) {
        boolean isSubShapeAdd = false;
        drawShape(x, y, middleShape);
        if (foregroundGenerator.getVal(x - 1, y) == neighborShapeId) {
            drawShape(x, y, leftShape);
            isSubShapeAdd = true;
        }
        if (foregroundGenerator.getVal(x + 1, y) == neighborShapeId) {
            drawShape(x, y, rightShape);
            isSubShapeAdd = true;
        }
        if (foregroundGenerator.getVal(x, y - 1) == neighborShapeId) {
            drawShape(x, y, upShape);
            isSubShapeAdd = true;
        }
        if (foregroundGenerator.getVal(x, y + 1) == neighborShapeId) {
            drawShape(x, y, downShape);
            isSubShapeAdd = true;
        }
        return isSubShapeAdd;
    }

    private void drawShapeByBackgroundMatrix(int x, int y) {
        if (backgroundGenerator.getVal(x, y) > backgroundGenerator.getFreq()) {
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
