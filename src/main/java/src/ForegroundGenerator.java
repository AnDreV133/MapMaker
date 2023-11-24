package src;

import java.util.ArrayList;

public class ForegroundGenerator {
    private ArrayList<ArrayList<IdObject>> matrix;

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
            ArrayList<IdObject> buf = new ArrayList<>();
            for (int x = 0; x < widthInCell; x++) {
                buf.add(IdObject.EMPTY);
            }
            matrix.add(buf);
        }
    }

    public void makeEmptyMatrix() {
        addShapesByAreaWithFill(
            new Point(1, 1),
            new Point(widthInCell - 2, heightInCell - 2),
            IdObject.EMPTY
        );
    }

    public void resizeMatrix(int newWidthInCell, int newHeightInCell) {
        widthInCell = newWidthInCell;
        heightInCell = newHeightInCell;

        matrix.clear();
        initMatrix();
        makeEmptyMatrix();
    }

    private boolean isPointInBound(Point point) {
        return point.getX() > 0 && point.getY() > 0 && point.getX() < widthInCell - 1 && point.getY() < heightInCell - 1;
    }

    private void correctingPoints(Point begin, Point end) {
        if (begin.equals(end) || begin.lessThan(end)) {
            return;
        }

        if (begin.biggerOrEqualThan(end)) {
            Point temp = new Point(begin.getX(), begin.getY());
            begin.setXY(end.getX(), end.getY());
            end.setXY(temp.getX(), temp.getY());
        } else if (begin.xBiggerThan(end) && begin.yLessThan(end)) {
            int temp = begin.getX();
            begin.setX(end.getX());
            end.setX(temp);
        } else if (begin.xLessThan(end) && begin.yBiggerThan(end)) {
            int temp = begin.getY();
            begin.setY(end.getY());
            end.setY(temp);
        }
    }

    public void addShapesByAreaWithFill(Point begin, Point end, IdObject idObject) {
        if (!(isPointInBound(begin) && isPointInBound(end)))
            return;

        correctingPoints(begin, end);

        ArrayList<IdObject> buf = new ArrayList<>();
        for (int y = begin.getY(); y <= end.getY(); y++) {
            for (int x = 0; x < widthInCell; x++) {
                if (x >= begin.getX() && x <= end.getX())
                    buf.add(idObject);
            }
            matrix.set(y, buf);
        }
    }

    public void addObjAtEdge(Point begin, Point end, IdObject idObject) {
        for (int x = begin.getX(); x <= end.getX(); x++) {
            matrix.get(begin.getY()).set(x, idObject);
            matrix.get(end.getY()).set(x, idObject);
        }

        for (int i = begin.getY(); i <= end.getY(); i++) {
            ArrayList<IdObject> buf = new ArrayList<>(matrix.get(i));
            buf.set(begin.getX(), idObject);
            buf.set(end.getX(), idObject);

            matrix.set(i, buf);
        }
    }

    public void addObjAsBlot(Point begin, Point end, IdObject idObject) {
        // todo надо придумать
    }

    public ArrayList<ArrayList<IdObject>> getMatrix() {
        return matrix;
    }
}
