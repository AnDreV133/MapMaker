package src;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame {
    static public final int WIDTH_SETTINGS_SPACE = 300;
    static public final int WIDTH_IMAGE_SPACE = 600;
    static public final int HEIGHT_SPACE = 500;
    static public final int SIZE_CELL = 12;

    public static final int MIN_SIZE_MAP = 10;
    public static final int MAX_SIZE_MAP = 200;
    public static final int DEFAULT_SIZE_MAP = 50;
    public static final int STEP_SIZE_MAP = 5;
    
    static private final SettingsPanel settingsPanel;
    static private final ImagePanel imagePanel;
    static private final LandscapePainter landscapePainter;
    static private final ObjectsPainter objectsPainter;

    static {
        settingsPanel = new SettingsPanel();
        imagePanel = new ImagePanel();
        landscapePainter = new LandscapePainter();
        objectsPainter = new ObjectsPainter();
    }

    static public SettingsPanel getSettingsPanel() {
        return settingsPanel;
    }

    static public ImagePanel getImagePanel() {
        return imagePanel;
    }

    static public LandscapePainter getLandscapePainter() {
        return landscapePainter;
    }

    static public ObjectsPainter getObjectsPainter() {
        return objectsPainter;
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
