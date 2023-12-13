package src;

import java.util.ArrayList;

public class BackgroundGenerator {
    private ArrayList<ArrayList<Float>> matrix;
    public int getHeight() {
        return matrix.size();
    }

    public int getWidth() {
        return matrix.get(0).size();
    }

    public BackgroundGenerator(int widthInCell, int heightInCell) {
        initMatrix(widthInCell, heightInCell);
    }

    public ArrayList<ArrayList<Float>> getMatrix() {
        return matrix;
    }

    public void initMatrix(int widthInCell, int heightInCell) {
        matrix = new ArrayList<>();

        for (int y = 0; y < heightInCell; y++) {
            ArrayList<Float> buf = new ArrayList<>();
            for (int x = 0; x < widthInCell; x++) {
                buf.add(1f);
            }
            matrix.add(buf);
        }
    }

    public void makeEmptyMatrix() {
        for (int y = 0; y < getHeight(); y++) {
            ArrayList<Float> buf = new ArrayList<>();
            for (int x = 0; x < getWidth(); x++) {
                buf.add(1f);
            }
            matrix.set(y, buf);
        }
    }

    public void resizeMatrix(int newWidthInCell, int newHeightInCell) {
        matrix.clear();
        initMatrix(newWidthInCell, newHeightInCell);
    }

    public void makeRandomMatrix() {
        for (int y = 0; y < getHeight(); y++) {
            ArrayList<Float> buf = new ArrayList<>();
            for (int x = 0; x < getWidth(); x++) {
                buf.add((float) Math.random());
            }
            matrix.set(y, buf);
        }
    }

    public void interpolateMatrix() {
        float[][] tempMap = new float[getHeight()][getWidth()];
        for (int x = 1; x < getWidth() - 1; ++x)
            for (int y = 1; y < getHeight() - 1; ++y) {
                tempMap[y][x] = (matrix.get(y).get(x - 1)
                        + matrix.get(y - 1).get(x - 1) + matrix.get(y - 1).get(x)
                        + matrix.get(y - 1).get(x + 1) + matrix.get(y).get(x + 1)
                        + matrix.get(y + 1).get(x + 1) + matrix.get(y + 1).get(x)
                        + matrix.get(y + 1).get(x - 1) + matrix.get(y).get(x)) / 9;
            }

        for (int y = 0; y < getHeight(); y++) {
            ArrayList<Float> buf = new ArrayList<>();
            for (int x = 0; x < getWidth(); x++) {
                buf.add(tempMap[y][x]);
            }
            matrix.set(y, buf);
        }
    }

    public void setVal(int x, int y, float val) {
        matrix.get(y).set(x, val);
    }

    private void addOrUpdateStrokeOfMap(int index, ArrayList<Float> val) {
        if (index < matrix.size()) {
            matrix.set(index, val);
        } else {
            matrix.add(val);
        }
    }

    public float getVal(int x, int y) {
        return getVal(x, y, 0);
    }

    public float getVal(int x, int y, int shift) {
        if (x < shift && x >= getWidth() - shift || y < shift && y >= getHeight() - shift) return 1;

        return matrix.get(y).get(x);
    }
}
