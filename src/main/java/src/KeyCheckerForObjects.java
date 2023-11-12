package src;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

public class KeyCheckerForObjects implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) { // todo call generator, move dict to painter
            case VK_0 -> MainFrame.getSettingsPanel().setCurrentObjectIndex(0);
            case VK_1 -> MainFrame.getSettingsPanel().setCurrentObjectIndex(1);
            case VK_2 -> MainFrame.getSettingsPanel().setCurrentObjectIndex(2);
            case VK_3 -> MainFrame.getSettingsPanel().setCurrentObjectIndex(3);
            case VK_4 -> MainFrame.getSettingsPanel().setCurrentObjectIndex(4);
            case VK_5 -> MainFrame.getSettingsPanel().setCurrentObjectIndex(5);
        }

        MainFrame.getSettingsPanel().repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
