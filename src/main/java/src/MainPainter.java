package src;

import src.Assets.*;

import java.awt.*;
import java.awt.image.BufferedImage;


public class MainPainter {
    private Graphics2D graphics;
    private BufferedImage image;
    private final BackgroundGenerator backgroundGenerator;
    private final ForegroundGenerator foregroundGenerator;
    private int heightInCell, widthInCell;
    private float freq = 0.5f;
    private final int sizeCell;
    private IdObject currentIdObject;

    public MainPainter(int widthInCell, int heightInCell, int sizeCell) {
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

    private Asset defineAsset(IdObject idObject) {
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


    public void addObjsByAreaWithFill(Point begin, Point end) {
        foregroundGenerator.addObjsByAreaWithFill(begin, end, currentIdObject);

        redrawMapByArea(begin, end);
    }


    public void redrawMapByArea(Point begin, Point end) {
        for (int y = begin.getY(); y <= end.getY(); y++) {
            for (int x = begin.getX(); x <= end.getX(); x++) {
                drawObjByMatrices(x, y);
            }
        }
    }

    private void drawObjByMatrices(int x, int y) {
        if (backgroundGenerator.getMatrix().get(y).get(x) > freq) {
            drawObj(x, y, new Cell(sizeCell));
            drawObj(x, y, defineAsset(foregroundGenerator.getMatrix().get(y).get(x)));
        } else {
            drawObj(x, y, new Stone(sizeCell));
        }
    }

    private void drawObjByBackgroundMatrix(int x, int y) {
        if (backgroundGenerator.getMatrix().get(y).get(x) > freq) {
            drawObj(x, y, new Cell(sizeCell));
        } else {
            drawObj(x, y, new Stone(sizeCell));
        }
    }

    private void drawObj(int x, int y, Asset asset) {
        graphics.drawImage(
            asset.getImage(),
            x * sizeCell,
            y * sizeCell,
            null
        );
    }
}

//    private boolean hasOtherIndex(Point begin, Point end, int index) {
//        for (int i = begin.getX(); i < end.getX(); i++) {
//            for (int j = begin.getY(); j < end.getY(); j++) {
//                if (map.get(j).get(i) != index)
//                    return false; // todo доделать
//            }
//        }
//
//        return true;
//    }

//    private final HashMap<IdObject, Asset> objectsDict = new HashMap<>() {{
//        put(IdObject.CELL, new Cell(sizeCell));
//        put(IdObject.STONE, new ShadedCell(sizeCell));
//        put(IdObject.BLOCK, new Block(sizeCell));
////        put(IdObjects.FENCE, new Cell(sizeCell));
////        put(IdObjects.TOWER, new Cell(sizeCell));
////        put(IdObjects.HOUSE, new Cell(sizeCell));
////        put(IdObjects.WATER, new Cell(sizeCell));
//    }};


//    public void drawObjectsByEdge(Point begin, Point end, IdObjects idObject) {
//        System.out.println(begin.getX() + " " + begin.getY() + " - " + end.getX() + " " + end.getY());
//
//        switch (idObject) {
//            case CELL -> {
//                addObject(new Cell(sizeCell), );
//            }
//        }
//    } // todo потом доделать

//    public ArrayList<ArrayList<Character>> addIndexOfObjectByEdge(Point begin, Point end, char idObject) {
//        if (!(isDotInRange(begin) && isDotInRange(end)))
//            return map;
//        // todo swaps
//        for (int i = begin.getX(); i < end.getX(); i++) {
//            map.get(begin.getY()).set(i, idObject);
//            map.get(end.getY()).set(i, idObject);
//        }
//
//        for (int i = begin.getY(); i < end.getY(); i++) {
//            ArrayList<Integer> buf = new ArrayList<>(map.get(i));
//            buf.set(begin.getX(), idObject);
//            buf.set(end.getX(), idObject);
//
//            map.set(i, buf);
//        }
//
//        return map;
//    }
