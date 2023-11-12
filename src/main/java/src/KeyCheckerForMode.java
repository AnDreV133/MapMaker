package src;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyCheckerForMode implements KeyListener {
    public static boolean isAltPressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isAltDown()) {
            isAltPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
            isAltPressed = false;
    }
}
