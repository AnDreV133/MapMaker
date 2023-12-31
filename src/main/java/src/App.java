package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import static java.awt.event.KeyEvent.*;

public class App extends JFrame implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private static final int MIN_SIZE_MAP = 10;
    private static final int MAX_SIZE_MAP = 200;
    private static final int DEFAULT_SIZE_MAP = 50;
    private static final int STEP_SIZE_MAP = 5;
    private static final int SIZE_CELL = 12;
    private static final int WIDTH_WINDOW = 900;
    private static final int HEIGHT_WINDOW = 600;
    private final Point imagePlace = new Point();
    private final Point mousePlace = new Point();
    private final Painter painter = new Painter(DEFAULT_SIZE_MAP, DEFAULT_SIZE_MAP, SIZE_CELL);
    private final HashMap<ShapeId, String> dictNameObjects = new HashMap<>() {{ // todo rewrite to switch
        put(ShapeId.CELL, "cell");
        put(ShapeId.STONE, "stone");
        put(ShapeId.BLOCK, "block");
        put(ShapeId.FENCE, "fence");
        put(ShapeId.TOWER, "tower");
        put(ShapeId.HOUSE, "house");
        put(ShapeId.ROAD, "road");
        put(ShapeId.WATER, "water");
    }};
    private final JSpinner widthLandscape = new JSpinner();
    private final JSpinner heightLandscape = new JSpinner();
    private final JSlider blockFreq = new JSlider(200, 800, 500);
    private final JLabel shapeName = new JLabel();
    private double scale = 1.0;
    private final JPanel imagePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.scale(scale, scale);
            g2d.drawImage(painter.getImage(), (int) (imagePlace.getX() / scale), (int) (imagePlace.getY() / scale), this);
        }
    };
    private ShapeId currentShapeId = ShapeId.CELL;
    private final JPanel settingsPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            shapeName.setText(dictNameObjects.get(currentShapeId));
        }
    };

    public App() {
        initImagePanel();
        initSettingsPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imagePanel, settingsPanel);
        splitPane.setDividerLocation(WIDTH_WINDOW / 3 * 2);

        add(splitPane);

        setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void initImagePanel() {
        imagePanel.addMouseListener(this);
        imagePanel.addMouseMotionListener(this);
        imagePanel.addMouseWheelListener(this);

        imagePanel.addKeyListener(this);
    }

    public HashMap<ShapeId, String> getDictNameObjects() {
        return dictNameObjects;
    }

    public ShapeId getCurrentShapeId() {
        return currentShapeId;
    }

    public void setCurrentShapeId(ShapeId currentShapeId) {
        if (currentShapeId == null) return;

        this.currentShapeId = currentShapeId;
    }

    public void initSettingsPanel() {
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        settingsPanel.add(new JLabel("Sizes"));
        widthLandscape.setModel(new SpinnerNumberModel(DEFAULT_SIZE_MAP, MIN_SIZE_MAP, MAX_SIZE_MAP, STEP_SIZE_MAP));
        settingsPanel.add(widthLandscape);

        heightLandscape.setModel(new SpinnerNumberModel(DEFAULT_SIZE_MAP, MIN_SIZE_MAP, MAX_SIZE_MAP, STEP_SIZE_MAP));
        settingsPanel.add(heightLandscape);

        JButton btnResize = new JButton();
        btnResize.addActionListener((l) -> {
            painter.resizeMap((Integer) widthLandscape.getValue(), (Integer) heightLandscape.getValue());
            imagePanel.repaint();
        });
        btnResize.setText("Resize");
        settingsPanel.add(btnResize);

        settingsPanel.add(new JLabel("Block frequency"));
        blockFreq.addChangeListener((l) -> {
            painter.setFreq(blockFreq.getValue() / 1000f);
            imagePanel.repaint();
        });
        settingsPanel.add(blockFreq);

        JButton btnRandomDots = new JButton();
        btnRandomDots.addActionListener((l) -> {
            painter.makeRandomMap();
            imagePanel.repaint();
        });
        btnRandomDots.setText("RandomDots");
        settingsPanel.add(btnRandomDots);

        JButton btnInterpolate = new JButton();
        btnInterpolate.addActionListener((l) -> {
            painter.interpolateMap();
            imagePanel.repaint();
        });
        btnInterpolate.setText("Interpolate");
        settingsPanel.add(btnInterpolate);

        JButton btnEmpty = new JButton();
        btnEmpty.addActionListener((l) -> {
            painter.makeEmptyMap();
            imagePanel.repaint();
        });
        btnEmpty.setText("Empty");
        settingsPanel.add(btnEmpty);

        settingsPanel.add(shapeName);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        setCurrentShapeId(switch (e.getKeyChar()) {
            case VK_0 -> ShapeId.CELL;
            case VK_1 -> ShapeId.STONE;
            case VK_2 -> ShapeId.BLOCK;
            case VK_3 -> ShapeId.FENCE;
            case VK_4 -> ShapeId.TOWER;
            case VK_5 -> ShapeId.HOUSE;
            case VK_6 -> ShapeId.ROAD;
            case VK_7 -> ShapeId.WATER;
            default -> null;
        });

        settingsPanel.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == VK_Z) {
            painter.redo();
        } else if (e.isControlDown() && e.getKeyCode() == VK_Z) {
            painter.undo();
        }

        imagePanel.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePlace.setXY(e.getX(), e.getY());
    }

    private Point translateMouseCordToIndexOfMatrix(Point mouseCord) {
        return new Point((int) ((mouseCord.getX() - imagePlace.getX()) / scale / SIZE_CELL), (int) ((mouseCord.getY() - imagePlace.getY()) / scale / SIZE_CELL));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isAltDown() && e.isControlDown()) {
            painter.setCurrentShapeId(currentShapeId);
            painter.drawShapesForce(translateMouseCordToIndexOfMatrix(mousePlace),
                    translateMouseCordToIndexOfMatrix(new Point(e.getX(), e.getY())));
        } else if (e.isAltDown() && !e.isControlDown()) {
            painter.setCurrentShapeId(currentShapeId);
            painter.drawShapes(translateMouseCordToIndexOfMatrix(mousePlace),
                    translateMouseCordToIndexOfMatrix(new Point(e.getX(), e.getY())));
        }

        imagePanel.repaint();
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
        if (!e.isAltDown()) {
            imagePlace.setXY(imagePlace.getX() + e.getX() - mousePlace.getX(), imagePlace.getY() + e.getY() - mousePlace.getY());

            imagePanel.repaint();

            mousePlace.setXY(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        if (notches < 0) {
            scale *= 1.1;
        } else if (scale >= 0.1) {
            scale /= 1.1;
        }

        imagePanel.repaint();
    }
}
