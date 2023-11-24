package src;

public class Point {
    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        Point point = (Point) o;
        return xEqual(point) && yEqual(point);
    }

    public boolean xEqual(Point point) {
        return getX() == point.getX();
    }

    public boolean yEqual(Point point) {
        return getY() == point.getY();
    }

    public boolean biggerThan(Point point) {
        return xBiggerThan(point) && yBiggerThan(point);
    }

    public boolean biggerOrEqualThan(Point point) {
        return (xBiggerThan(point) || xEqual(point)) && (yBiggerThan(point) || yEqual(point));
    }

    public boolean xBiggerThan(Point point) {
        return getX() > point.getX();
    }

    public boolean yBiggerThan(Point point) {
        return getY() > point.getY();
    }

    public boolean lessThan(Point point) {
        return xLessThan(point) && yLessThan(point);
    }
    public boolean lessOrEqualThan(Point point) {
        return (xLessThan(point) || xEqual(point)) && (yLessThan(point) || yEqual(point));
    }

    public boolean xLessThan(Point point) {
        return getX() < point.getX();
    }

    public boolean yLessThan(Point point) {
        return getY() < point.getY();
    }


    public void swapWith(PointWrapper point) {
        Point temp = new Point(getX(), getY());
        setXY(point.p.getX(), point.p.getY());
        point.p.setXY(temp.getX(), temp.getY());
    }
}

class PointWrapper {
    public Point p;

    public PointWrapper(Point p) {
        this.p = p;
    }
}