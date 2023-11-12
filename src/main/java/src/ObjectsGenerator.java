package src;

import java.util.ArrayList;

public class ObjectsGenerator {
    private ArrayList<ArrayList<Integer>> map;

    private int heightInCell, widthInCell;

    public ObjectsGenerator(int heightInCell, int widthInCell) {
        this.heightInCell = heightInCell;
        this.widthInCell = widthInCell; //  todo add empty matrix
    }

    public ArrayList<ArrayList<Integer>> getEmptyMap() {
        map.clear();

        for (int y = 0; y < heightInCell; y++) {
            ArrayList<Integer> buf = new ArrayList<>();
            for (int x = 0; x < widthInCell; x++) {
                buf.add(-1);
            }
            map.add(buf);
        }

        return map;
    }

    public ArrayList<ArrayList<Integer>> getAddedObjects() {
        return map;
    }

    private boolean isDotInRange(int x, int y) {
        return x >= 1 || y >= 1 || x < widthInCell - 1 || y < heightInCell - 1;
    }

    public ArrayList<ArrayList<Integer>> addIndexOfObjectByEdge(int xBegin, int yBegin, int xEnd, int yEnd, int index) {
        if (!(isDotInRange(xBegin, yBegin) && isDotInRange(xEnd, yEnd)))
            return map;

        for (int i = xBegin; i < xEnd; i++) {
            map.get(yBegin).set(i, index);
            map.get(yEnd).set(i, index);
        }

        for (int i = yBegin; i < yEnd; i++) {
            ArrayList<Integer> buf = new ArrayList<>(map.get(i));
            buf.set(xBegin, index);
            buf.set(xEnd, index);

            map.set(i, buf);
        }

        return map;
    }

    public ArrayList<ArrayList<Integer>> addIndexOfObject(int xBegin, int yBegin, int xEnd, int yEnd, int index) {
        if (!(isDotInRange(xBegin, yBegin) && isDotInRange(xEnd, yEnd)))
            return map;

        ArrayList<Integer> buf = new ArrayList<>();
        while (xEnd - xBegin > 0) {
            buf.add(index);
            xBegin++;
        }

        while (yEnd - yBegin > 0) {
            map.set(yBegin, buf);
            yBegin++;
        }

        return map;
    }

    public ArrayList<ArrayList<Integer>> resize(int newWidthInCell, int newHeightInCell) {
        map.clear();

        for (int y = 0; y < newHeightInCell; y++) {
            ArrayList<Integer> buf = new ArrayList<>();
            for (int x = 0; x < newWidthInCell; x++) {
                buf.add(-1);
            }
            map.add(buf);
        }

        widthInCell = newWidthInCell;
        heightInCell = newHeightInCell;

        return map;
    }

}
