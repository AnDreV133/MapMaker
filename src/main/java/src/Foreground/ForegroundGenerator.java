package src.Foreground;

import src.Point;
import src.ShapeId;

import java.util.ArrayList;

public class ForegroundGenerator extends Foreground {
    public ForegroundGenerator(int widthInCell, int heightInCell) {
        initMatrix(widthInCell, heightInCell);
    }

    public void initMatrix(int widthInCell, int heightInCell) {
        matrix = new ArrayList<>();

        for (int y = 0; y < heightInCell; y++) {
            ArrayList<ShapeId> buf = new ArrayList<>();
            for (int x = 0; x < widthInCell; x++) {
                buf.add(ShapeId.CELL);
            }
            matrix.add(buf);
        }
    }

    public void makeEmptyMatrix() {
        addShapesByAreaWithFill(
                new Point(1, 1),
                new Point(getWidth() - 2, getHeight() - 2),
                ShapeId.CELL
        );
    }

    public void resizeMatrix(int newWidthInCell, int newHeightInCell) {
        matrix.clear();
        initMatrix(newWidthInCell, newHeightInCell);
    }

    public void addShapesByAreaWithFill(Point begin, Point end, ShapeId shapeId) {
        for (int y = begin.getY(); y <= end.getY(); y++) {
            for (int x = begin.getX(); x <= end.getX(); x++) {
                matrix.get(y).set(x, shapeId);
            }
        }
    }

    public void addShapesAtEdge(Point begin, Point end, ShapeId shapeId) {
        for (int x = begin.getX(); x <= end.getX(); x++) {
            matrix.get(begin.getY()).set(x, shapeId);
            matrix.get(end.getY()).set(x, shapeId);
        }

        for (int y = begin.getY(); y <= end.getY(); y++) {
            matrix.get(y).set(begin.getX(), shapeId);
            matrix.get(y).set(end.getX(), shapeId);
        }
    }

    public void addShapesAsBlot(Point begin, Point end, ShapeId shapeId) {
        // todo make by interpolate
    }

    public ArrayList<ArrayList<ShapeId>> getMatrix() {
        return matrix;
    }

    public void addShapes(Point begin, Point end, ShapeId shapeId) {
        switch (shapeId) {
            case CELL, BLOCK, STONE, HOUSE, ROAD -> addShapesByAreaWithFill(begin, end, shapeId);
            case FENCE -> addShapesAtEdge(begin, end, shapeId);
            case WATER -> addShapesAsBlot(begin, end, shapeId);
        }
    }

    public ShapeId getVal(int x, int y) {
        return getVal(x, y, 0);
    }

    public ShapeId getVal(int x, int y, int shift) {
        if (x < shift || x >= getWidth() - shift || y < shift || y >= getHeight() - shift) return ShapeId.CELL;

        return matrix.get(y).get(x);
    }
}
