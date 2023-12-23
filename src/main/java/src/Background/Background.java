package src.Background;

import java.util.ArrayList;

public class Background {
    protected ArrayList<ArrayList<Float>> matrix;
    protected float freq;

    public Background(ArrayList<ArrayList<Float>> matrix, float freq) {
        this.matrix = matrix;
        this.freq = freq;
    }

    public Background(Background background) {
        this.matrix = new ArrayList<>();
        for (int y = 0; y < background.getHeight(); y++) {
            ArrayList<Float> buf = new ArrayList<>();
            for (int x = 0; x < background.getWidth(); x++) {
                buf.add(background.getMatrix().get(y).get(x));
            }
            matrix.add(buf);
        }

        this.freq = background.getFreq();
    }
    public Background() {
    }
    public int getHeight() {
        return matrix.size();
    }

    public int getWidth() {
        return matrix.get(0).size();
    }

    public ArrayList<ArrayList<Float>> getMatrix() {
        return matrix;
    }

    public void setMatrix(ArrayList<ArrayList<Float>> matrix) {
        this.matrix = matrix;
    }

    public Float getFreq() {
        return freq;
    }

    public void setFreq(Float freq) {
        this.freq = freq;
    }

    public void setBackground(Background background) {
        this.matrix = background.getMatrix();
        this.freq = background.getFreq();
    }
}
