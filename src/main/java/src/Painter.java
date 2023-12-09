package src;

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
                backgroundGenerator.setValInMatrix(x, y, currentShapeId == ShapeId.STONE ? 0.0f : 1.0f);

                drawShape(x, y, new Cell(sizeCell));
                defineAndDrawShape(x, y, foregroundGenerator.getMatrix().get(y).get(x));
            }
        }
    }

    private void defineAndDrawShape(int x, int y, ShapeId shapeId) {
        switch (shapeId) {
            case FENCE -> drawShapeWithOrientationModify(x, y, ShapeId.FENCE, new FenceCentral(sizeCell),
                    new FenceLeft(sizeCell), new FenceRight(sizeCell),
                    new FenceUp(sizeCell), new FenceDown(sizeCell));
            case HOUSE -> drawShapeWithOrientation(x, y, ShapeId.HOUSE, new House(sizeCell));
//            case ROAD -> drawRoad(x, y, shapeId.HOUSE, ,
//                    );
            default -> drawShape(x, y, defineShape(foregroundGenerator.getMatrix().get(y).get(x)));
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

    private void drawRoad(int x, int y, ShapeId neighborShapeId) {

        drawShape(x, y, new Road(sizeCell));
        if (foregroundGenerator.getMatrix().get(y).get(x - 1) == neighborShapeId) {
            drawShape(x - 1, y, new HouseFill(sizeCell));
            foregroundGenerator.getMatrix().get(y).set(x - 1, ShapeId.DOOR);
        }
        if (foregroundGenerator.getMatrix().get(y).get(x + 1) == neighborShapeId) {
            drawShape(x + 1, y, new HouseFill(sizeCell));
            foregroundGenerator.getMatrix().get(y).set(x + 1, ShapeId.DOOR);

        }
        if (foregroundGenerator.getMatrix().get(y - 1).get(x) == neighborShapeId) {
            drawShape(x, y - 1, new HouseFill(sizeCell));
            foregroundGenerator.getMatrix().get(y - 1).set(x, ShapeId.DOOR);

        }
        if (foregroundGenerator.getMatrix().get(y + 1).get(x) == neighborShapeId) {
            drawShape(x, y + 1, new HouseFill(sizeCell));
            foregroundGenerator.getMatrix().get(y + 1).set(x, ShapeId.DOOR);

        }
    }

    private void drawShapeWithOrientation(int x, int y, ShapeId shapeId, HardShape hardShape) {
        boolean isShapeOnLeft = false;
        boolean isShapeOnRight = false;
        boolean isShapeOnUp = false;
        boolean isShapeOnDown = false;
        if (foregroundGenerator.getMatrix().get(y).get(x - 1) == shapeId)
            isShapeOnLeft = true;
        if (foregroundGenerator.getMatrix().get(y).get(x + 1) == shapeId)
            isShapeOnRight = true;
        if (foregroundGenerator.getMatrix().get(y - 1).get(x) == shapeId)
            isShapeOnUp = true;
        if (foregroundGenerator.getMatrix().get(y + 1).get(x) == shapeId)
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
        if (foregroundGenerator.getMatrix().get(y).get(x - 1) == neighborShapeId) {
            drawShape(x, y, leftShape);
            isSubShapeAdd = true;
        }
        if (foregroundGenerator.getMatrix().get(y).get(x + 1) == neighborShapeId) {
            drawShape(x, y, rightShape);
            isSubShapeAdd = true;
        }
        if (foregroundGenerator.getMatrix().get(y - 1).get(x) == neighborShapeId) {
            drawShape(x, y, upShape);
            isSubShapeAdd = true;
        }
        if (foregroundGenerator.getMatrix().get(y + 1).get(x) == neighborShapeId) {
            drawShape(x, y, downShape);
            isSubShapeAdd = true;
        }
        return isSubShapeAdd;
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
