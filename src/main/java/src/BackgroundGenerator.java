package src;

import java.util.ArrayList;

public class BackgroundGenerator {
    private ArrayList<ArrayList<Float>> matrix;
    private int heightInCell, widthInCell;

    public BackgroundGenerator(int heightInCell, int widthInCell) {
        this.heightInCell = heightInCell;
        this.widthInCell = widthInCell;

        initMatrix();
        makeEmptyMatrix();
    }

    public ArrayList<ArrayList<Float>> getMatrix() {
        return matrix;
    }

    public int getHeightInCell() {
        return heightInCell;
    }

    public int getWidthInCell() {
        return widthInCell;
    }

    public void initMatrix() {
        matrix = new ArrayList<>();

        for (int y = 0; y < heightInCell; y++) {
            matrix.add(new ArrayList<>(widthInCell));
        }
    }

    public void makeEmptyMatrix() {
        for (int y = 0; y < heightInCell; y++) {
            ArrayList<Float> buf = new ArrayList<>();
            for (int x = 0; x < widthInCell; x++) {
                buf.add(1.0f);
            }
            matrix.set(y, buf);
        }
    }

    public void resizeMatrix(int newWidthInCell, int newHeightInCell) {
        widthInCell = newWidthInCell;
        heightInCell = newHeightInCell;

        matrix.clear();
        initMatrix();
        makeEmptyMatrix();
    }

    public void makeRandomMatrix() {
        for (int y = 0; y < heightInCell; y++) {
            ArrayList<Float> buf = new ArrayList<>();
            for (int x = 0; x < widthInCell; x++) {
                buf.add((float) Math.random());
            }
            matrix.set(y, buf);
        }
    }

    public void interpolateMatrix() {
        float[][] tempMap = new float[heightInCell][widthInCell];
        for (int x = 1; x < widthInCell - 1; ++x)
            for (int y = 1; y < heightInCell - 1; ++y) {
                tempMap[y][x] = (matrix.get(y).get(x - 1)
                    + matrix.get(y - 1).get(x - 1) + matrix.get(y - 1).get(x)
                    + matrix.get(y - 1).get(x + 1) + matrix.get(y).get(x + 1)
                    + matrix.get(y + 1).get(x + 1) + matrix.get(y + 1).get(x)
                    + matrix.get(y + 1).get(x - 1) + matrix.get(y).get(x)) / 9;
            }

        for (int y = 0; y < heightInCell; y++) {
            ArrayList<Float> buf = new ArrayList<>();
            for (int x = 0; x < widthInCell; x++) {
                buf.add(tempMap[y][x]);
            }
            matrix.set(y, buf);
        }
    }

    private void addOrUpdateStrokeOfMap(int index, ArrayList<Float> val) {
        if (index < matrix.size()) {
            matrix.set(index, val);
        } else {
            matrix.add(val);
        }
    }
}
