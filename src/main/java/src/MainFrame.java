package src;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame {
    public static final int WIDTH_SETTINGS_SPACE = 300;
    public static final int WIDTH_IMAGE_SPACE = 600;
    public static final int HEIGHT_SPACE = 500;

    static private final ImagePanel imagePanel;
    static private final SettingsPanel settingsPanel ;

    static {
        settingsPanel = new SettingsPanel();
        imagePanel = new ImagePanel();
    }

    static public SettingsPanel getSettingsPanel() {
        return settingsPanel;
    }

    static public ImagePanel getImagePanel() {
        return imagePanel;
    }

    public MainFrame() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imagePanel, settingsPanel);
        splitPane.setDividerLocation(WIDTH_IMAGE_SPACE);

        add(splitPane);

        setSize(WIDTH_IMAGE_SPACE + WIDTH_SETTINGS_SPACE, HEIGHT_SPACE); // задаем размеры окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // закрываем окно при нажатии на крестик
        setVisible(true); // делаем окно видимым
    }


}
