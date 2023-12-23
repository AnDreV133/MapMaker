package src.Foreground;

import src.ShapeId;

import java.util.ArrayList;

public class Foreground {
    protected ArrayList<ArrayList<ShapeId>> matrix;

    public Foreground(ArrayList<ArrayList<ShapeId>> matrix) {
        this.matrix = matrix;
    }

    public Foreground(Foreground foreground) {
//        this.matrix = new ArrayList<>(foreground.getMatrix());

        this.matrix = new ArrayList<>();
        for (int y = 0; y < foreground.getHeight(); y++) {
            var buf = new ArrayList<ShapeId>();
            for (int x = 0; x < foreground.getWidth(); x++) {
                buf.add(foreground.getMatrix().get(y).get(x));
            }

            matrix.add(buf);
        }
    }

    public Foreground() {
    }

    public int getHeight() {
        return matrix.size();
    }

    public int getWidth() {
        return matrix.get(0).size();
    }

    public ArrayList<ArrayList<ShapeId>> getMatrix() {
        return matrix;
    }

    public void setMatrix(ArrayList<ArrayList<ShapeId>> matrix) {
        this.matrix = matrix;
    }

    public void setForeground(Foreground foreground) {
        this.matrix = foreground.getMatrix();
    }
}
