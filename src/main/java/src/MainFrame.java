package src;

import javax.swing.*;

public class MainFrame extends JFrame {
    static public final int WIDTH_SETTINGS_SPACE = 300;
    static public final int WIDTH_IMAGE_SPACE = 600;
    static public final int HEIGHT_SPACE = 500;
    static private final ImagePanel imagePanel;
    static private final MainPainter MAIN_PAINTER;

    static {
        imagePanel = new ImagePanel();
        MAIN_PAINTER = new MainPainter();
    }


    static public ImagePanel getImagePanel() {
        return imagePanel;
    }


    static public MainPainter getObjectsPainter() {
        return MAIN_PAINTER;
    }

    public MainFrame() {
//        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imagePanel, settingsPanel);
//        splitPane.setDividerLocation(WIDTH_IMAGE_SPACE);
//
//        add(splitPane);
//
//        setSize(WIDTH_IMAGE_SPACE + WIDTH_SETTINGS_SPACE, HEIGHT_SPACE); // задаем размеры окна
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // закрываем окно при нажатии на крестик
//        setVisible(true); // делаем окно видимым
    }


}
