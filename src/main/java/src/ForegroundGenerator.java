package src;

import java.util.ArrayList;

public class ForegroundGenerator {
    private ArrayList<ArrayList<ShapeId>> matrix;

    private int heightInCell, widthInCell;

    public ForegroundGenerator(int heightInCell, int widthInCell) {
        this.heightInCell = heightInCell;
        this.widthInCell = widthInCell;

        initMatrix();
        makeEmptyMatrix();
    }

    public void initMatrix() {
        matrix = new ArrayList<>();

        for (int y = 0; y < heightInCell; y++) {
            ArrayList<ShapeId> buf = new ArrayList<>();
            for (int x = 0; x < widthInCell; x++) {
                buf.add(ShapeId.EMPTY);
            }
            matrix.add(buf);
        }
    }

    public void makeEmptyMatrix() {
        addShapesByAreaWithFill(
                new Point(1, 1),
                new Point(widthInCell - 2, heightInCell - 2),
                ShapeId.EMPTY
        );
    }

    public void resizeMatrix(int newWidthInCell, int newHeightInCell) {
        widthInCell = newWidthInCell;
        heightInCell = newHeightInCell;

        matrix.clear();
        initMatrix();
        makeEmptyMatrix();
    }

    private void movePointToBound(Point point) {
        if (point.getX() < 0)
            point.setX(0);
        if (point.getX() >= widthInCell)
            point.setX(widthInCell - 1);
        if (point.getY() < 0)
            point.setY(0);
        if (point.getY() >= heightInCell)
            point.setY(heightInCell - 1);
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

    public void addShapesByAreaWithFill(Point begin, Point end, ShapeId shapeId) {
        correctingPoints(begin, end);

        for (int y = begin.getY(); y <= end.getY(); y++) {
            for (int x = begin.getX(); x <= end.getX(); x++) {
                matrix.get(y).set(x, shapeId);
            }
        }
    }

    public void addShapesAtEdge(Point begin, Point end, ShapeId shapeId) {
        correctingPoints(begin, end);

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
        correctingPoints(begin, end);

        // todo make by interpolate
    }

    public ArrayList<ArrayList<ShapeId>> getMatrix() {
        return matrix;
    }

    public void analyzeByIdAndAddShapes(Point begin, Point end, ShapeId shapeId) {
        switch (shapeId) {
            case CELL, BLOCK, STONE, HOUSE, ROAD -> addShapesByAreaWithFill(begin, end, shapeId);
            case FENCE -> addShapesAtEdge(begin, end, shapeId);
            case WATER -> addShapesAsBlot(begin, end, shapeId);
            default -> System.out.println("Not define shape");
//            case TOWER ->return new (sizeCell);
        }
    }



    public void addVillage(Point begin, Point end, ArrayList<ArrayList<Float>> B){
        // todo место для реализации наполеонских затей
    }
}
