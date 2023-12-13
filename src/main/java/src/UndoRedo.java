package src;

import java.util.ArrayList;
import java.util.LinkedList;

public class UndoRedo {
    private ArrayList<ArrayList<Float>> lastBackground;
    private ArrayList<ArrayList<ShapeId>> lastForeground;
    private LinkedList<PairGrounds> sequenceStates;

    private void add(PairGrounds pairGrounds) {
        sequenceStates.add(pairGrounds);
    }

    public void addBackground(ArrayList<ArrayList<Float>> background) {
        sequenceStates.add(new PairGrounds(new ArrayList<>(background), null));
    }

    public void addForeground(ArrayList<ArrayList<ShapeId>> foreground) {
        sequenceStates.add(new PairGrounds(null, new ArrayList<>(foreground)));
    }

    public PairGrounds getPairGrounds() {
        var res = sequenceStates.getLast();
        var tempForeground = res.foreground();
        var tempBackground = res.background();

        int i = sequenceStates.size() - 2;
        while (tempBackground == null || tempForeground == null) {
            if (i < 0) {
                if (tempBackground == null) tempBackground = lastBackground;
                else if (tempForeground == null) tempForeground = lastForeground;
                break;
            }

            var iterPairGrounds = sequenceStates.get(i);
            if (res.background() == null && tempBackground != null)
                tempBackground = iterPairGrounds.background();
            else if (res.foreground() == null && tempForeground != null)
                tempForeground = iterPairGrounds.foreground();

            i--;
        }

        return new PairGrounds(tempBackground, tempForeground);
    }


}
