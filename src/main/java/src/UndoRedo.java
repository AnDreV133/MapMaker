package src;

import src.Background.Background;
import src.Foreground.Foreground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class UndoRedo {
    private static final int CAPACITY = 50;
    private final LinkedList<PairGrounds> stackDo = new LinkedList<>();
    private final LinkedList<PairGrounds> subStackDo = new LinkedList<>();
    private Background lastBackground;
    private Foreground lastForeground;

    public UndoRedo(Background lastBackground, Foreground lastForeground) {
        this.lastBackground = new Background(lastBackground);
        this.lastForeground = new Foreground(lastForeground);
    }

    private void add(PairGrounds pairGrounds) {
        subStackDo.clear();

        if (stackDo.size() == CAPACITY) {
            var temp = stackDo.getFirst();
            if (temp.background() != null)
                lastBackground = temp.background();
            else if (temp.foreground() != null)
                lastForeground = temp.foreground();

            stackDo.removeFirst();
        }

        stackDo.add(pairGrounds);
    }

    public void add(Background background, Foreground foreground) {
        add(new PairGrounds(new Background(background), new Foreground(foreground)));
    }

    public void add(Background background) {
        add(new PairGrounds(new Background(background), null));
    }

    public void add(Foreground foreground) {
        add(new PairGrounds(null, new Foreground(foreground)));
    }

    public PairGrounds getPairGrounds() {
        PairGrounds lastPair = new PairGrounds(null, null);
        Foreground resForeground = null;
        Background resBackground = null;
        if (!stackDo.isEmpty()) {
            lastPair = stackDo.getLast();
            resForeground = lastPair.foreground();
            resBackground = lastPair.background();
        }

        int i = stackDo.size() - 2;
        while (resBackground == null || resForeground == null) {
            if (i < 0) {
                if (resBackground == null) resBackground = lastBackground;
                if (resForeground == null) resForeground = lastForeground;
                break;
            }

            var iterPairGrounds = stackDo.get(i);
            if (lastPair.background() == null && iterPairGrounds.background() != null)
                resBackground = iterPairGrounds.background();
            else if (lastPair.foreground() == null && iterPairGrounds.foreground() != null)
                resForeground = iterPairGrounds.foreground();

            i--;
        }

        return new PairGrounds(new Background(resBackground), new Foreground(resForeground));
    }

    public PairGrounds undo() {
        if (!stackDo.isEmpty()) {
            subStackDo.add(stackDo.getLast());
            stackDo.removeLast();
        }

        return getPairGrounds();
    } // ctrl + z

    public PairGrounds redo() {
        if (!subStackDo.isEmpty()) {
            stackDo.add(subStackDo.getLast());
            subStackDo.removeLast();
        }

        return getPairGrounds();
    }  // ctrl + shift + z
}
