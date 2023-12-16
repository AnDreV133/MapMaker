package src;

import java.util.ArrayList;
import java.util.LinkedList;

public class UndoRedo {
    private static final int CAPACITY = 30;
    private ArrayList<ArrayList<Float>> lastBackground;
    private ArrayList<ArrayList<ShapeId>> lastForeground;
    private LinkedList<PairGrounds> stackDo;
    private LinkedList<PairGrounds> subStackDo;

    public UndoRedo(ArrayList<ArrayList<Float>> lastBackground, ArrayList<ArrayList<ShapeId>> lastForeground) {
        this.lastBackground = lastBackground;
        this.lastForeground = lastForeground;
    }

    private void add(PairGrounds pairGrounds) {
        if (stackDo.size() == CAPACITY) {
            var temp = stackDo.getFirst();
            if (temp.background() != null) lastBackground = temp.background();
            else if (temp.foreground() != null) lastForeground = temp.foreground();

            stackDo.removeFirst();
        }

        stackDo.add(pairGrounds);
    }

    public void addBackground(ArrayList<ArrayList<Float>> background) {
        stackDo.add(new PairGrounds(new ArrayList<>(background), null));
    }

    public void addForeground(ArrayList<ArrayList<ShapeId>> foreground) {
        stackDo.add(new PairGrounds(null, new ArrayList<>(foreground)));
    }

    public PairGrounds getPairGrounds() {
        var res = stackDo.getLast();
        var tempForeground = res.foreground();
        var tempBackground = res.background();

        int i = stackDo.size() - 2;
        while (tempBackground == null || tempForeground == null) {
            if (i < 0) {
                if (tempBackground == null) tempBackground = lastBackground;
                else tempForeground = lastForeground;
                break;
            }

            var iterPairGrounds = stackDo.get(i);
            if (res.background() == null && tempBackground != null)
                tempBackground = iterPairGrounds.background();
            else if (res.foreground() == null && tempForeground != null)
                tempForeground = iterPairGrounds.foreground();

            i--;
        }

        return new PairGrounds(tempBackground, tempForeground);
    }

    public PairGrounds redo() {

    } // ctrl + z

    public PairGrounds undo() {

    }  // ctrl + shift + z
}
