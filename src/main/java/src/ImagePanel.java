package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import static java.awt.event.KeyEvent.*;
import static src.MainFrame.*;

public class ImagePanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    //    private final ImageIcon imageIcon = new ImageIcon();
    private final Point imagePlace = new Point();
    private final Point mousePlace = new Point();
    private BufferedImage image;

    private final MainPainter mainPainter = new MainPainter();
    private double scale = 1.0;

    private final JPanel imagePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) { // переопределяем метод отрисовки компонента
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g; // используем 2D графику
            g2d.scale(scale, scale); // масштабируем изображение
            g2d.drawImage(
                image,
                (int) (imagePlace.getX() / scale),
                (int) (imagePlace.getY() / scale),
                this
            );
        }
    };

    public void initImagePanel() {


//        settingsPanel.empty();

        imagePanel.addMouseListener(this);
        imagePanel.addMouseMotionListener(this);
        imagePanel.addMouseWheelListener(this);

        imagePanel.addKeyListener(this);
        imagePanel.addKeyListener(this);

//        imagePlace.setXY(
//            (MainFrame.WIDTH_IMAGE_SPACE - imageIcon.getIconWidth()) / 2,
//            (MainFrame.HEIGHT_SPACE - imageIcon.getIconHeight()) / 2
//        );
    }

    private final HashMap<IdObject, String> dictNameObjects = new HashMap<>() {{
        put(IdObject.CELL, "cell");
        put(IdObject.STONE, "stone");
        put(IdObject.BLOCK, "block");
        put(IdObject.FENCE, "fence");
        put(IdObject.TOWER, "tower");
        put(IdObject.HOUSE, "house");
        put(IdObject.WATER, "water");
    }};

    private IdObject currentObjectIndex = IdObject.CELL;

    public HashMap<IdObject, String> getDictNameObjects() {
        return dictNameObjects;
    }

    public IdObject getCurrentObjectIndex() {
        return currentObjectIndex;
    }

    private final JSpinner widthLandscape = new JSpinner();
    private final JSpinner heightLandscape = new JSpinner();
    private final JSlider blockFreq = new JSlider(200, 800, 500);
    private final JLabel objectName = new JLabel();

    JPanel settingsPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            objectName.setText(dictNameObjects.get(currentObjectIndex));
        }
    };

    public void initSettingsPanel() {
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setSize(MainFrame.WIDTH_SETTINGS_SPACE, MainFrame.HEIGHT_SPACE);

        // Задание размеров поля
        settingsPanel.add(new JLabel("Sizes"));
        widthLandscape.setModel(new SpinnerNumberModel(DEFAULT_SIZE_MAP, MIN_SIZE_MAP, MAX_SIZE_MAP, STEP_SIZE_MAP));
        settingsPanel.add(widthLandscape);

        heightLandscape.setModel(new SpinnerNumberModel(DEFAULT_SIZE_MAP, MIN_SIZE_MAP, MAX_SIZE_MAP, STEP_SIZE_MAP));
        settingsPanel.add(heightLandscape);

        JButton btnResize = new JButton();
        btnResize.addActionListener((l) -> {
            mainPainter.getResizeImage(
                (Integer) widthLandscape.getValue(),
                (Integer) heightLandscape.getValue(),
                blockFreq.getValue() / 1000f
            );
            imagePanel.repaint();
        });
        btnResize.setText("Resize");
        settingsPanel.add(btnResize);

        settingsPanel.add(new JLabel("Block frequency"));
        blockFreq.addChangeListener((l) -> {
            mainPainter.getImageFromMap(blockFreq.getValue() / 1000f);
            imagePanel.repaint();
        });
        settingsPanel.add(blockFreq);

        JButton btnRandomDots = new JButton();
        btnRandomDots.addActionListener((l) -> {
            mainPainter.getRandomDotsImage(blockFreq.getValue() / 1000f);
            imagePanel.repaint();
        });
        btnRandomDots.setText("RandomDots");
        settingsPanel.add(btnRandomDots);

        JButton btnInterpolate = new JButton();
        btnInterpolate.addActionListener((l) -> {
            mainPainter.getInterpolatedImage(blockFreq.getValue() / 1000f);
            imagePanel.repaint();
        });
        btnInterpolate.setText("Interpolate");
        settingsPanel.add(btnInterpolate);

        JButton btnEmpty = new JButton();
        btnEmpty.addActionListener((l) -> {
            mainPainter.makeEmptyMap();
            imagePanel.repaint();
        });
        btnEmpty.setText("Empty");
        settingsPanel.add(btnEmpty);

        settingsPanel.add(objectName);
    }

    public static boolean isAltPressed;

    public void setCurrentIdObject(IdObject currentObjectIndex) {
        this.currentObjectIndex = currentObjectIndex;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) { // todo call generator, move dict to painter
            case VK_0 -> setCurrentIdObject(IdObject.CELL);
            case VK_1 -> setCurrentIdObject(IdObject.STONE);
            case VK_2 -> setCurrentIdObject(IdObject.BLOCK);
            case VK_3 -> setCurrentIdObject(IdObject.FENCE);
            case VK_4 -> setCurrentIdObject(IdObject.TOWER);
            case VK_5 -> setCurrentIdObject(IdObject.HOUSE);
            case VK_6 -> setCurrentIdObject(IdObject.WATER);
        }

        settingsPanel.repaint();
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

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        System.out.println(e.getX() + " " + e.getY());
        mousePlace.setXY(e.getX(), e.getY());
    }

    private Point translateMouseCordToIndexOfMatrix(Point mouseCord) {
        return new Point(
            (int) ((mouseCord.getX() - imagePlace.getX()) / (SIZE_CELL * scale)),
            (int) ((mouseCord.getY() - imagePlace.getY()) / (SIZE_CELL * scale))
        );
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isAltPressed) {
            MainFrame.getObjectsPainter().drawObjectsByEdge(
                translateMouseCordToIndexOfMatrix(mousePlace),
                translateMouseCordToIndexOfMatrix(new Point(e.getX(), e.getY())),
                getCurrentObjectIndex()
            );
        } // для добавления объектов
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        imagePanel.requestFocusInWindow();
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!isAltPressed) {
            imagePlace.setXY(
                imagePlace.getX() + e.getX() - mousePlace.getX(),
                imagePlace.getY() + e.getY() - mousePlace.getY()
            );

            imagePanel.repaint();

            mousePlace.setXY(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (!isAltPressed) {
            int notches = e.getWheelRotation();
            if (notches < 0) {
                scale *= 1.1;
            } else if (scale >= 0.1) {
                scale /= 1.1;
            }

            imagePanel.repaint();
        }
    }
}
