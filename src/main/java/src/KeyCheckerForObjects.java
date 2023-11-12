package src;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_0;

public class KeyCheckerForObjects implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyCode()) {
            case VK_0 -> {

            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
