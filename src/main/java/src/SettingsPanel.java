package src;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class SettingsPanel extends JPanel{
    public static final int MIN_SIZE_MAP = 10;
    public static final int MAX_SIZE_MAP = 200;
    public static final int DEFAULT_SIZE_MAP = 50;
    public static final int STEP_SIZE_MAP = 5;

    private final JSpinner widthLandscape = new JSpinner();
    private final JSpinner heightLandscape = new JSpinner();
    private final JSlider blockFreq = new JSlider(200, 800, 500);
    
    private final LandscapePainter landscapePainter = new LandscapePainter();
    
    private final Map<Integer, String> dictNameObjects = new HashMap<>() {{
        put(0, "cell");
        put(1, "stone");
        put(2, "fence");
        put(3, "tower");
        put(4, "house");
        put(5, "water");
    }};

    static private int currentObjectIndex = 0;

    public int getCurrentObjectIndex() {
        return currentObjectIndex;
    }

    public void setCurrentObjectIndex(int currentObjectIndex) {
        SettingsPanel.currentObjectIndex = currentObjectIndex;
    }

    public SettingsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setSize(MainFrame.WIDTH_SETTINGS_SPACE, MainFrame.HEIGHT_SPACE);

        // Задание размеров поля
        add(new JLabel("Sizes"));
        widthLandscape.setModel(new SpinnerNumberModel(DEFAULT_SIZE_MAP, MIN_SIZE_MAP, MAX_SIZE_MAP, STEP_SIZE_MAP));
        add(widthLandscape);

        heightLandscape.setModel(new SpinnerNumberModel(DEFAULT_SIZE_MAP, MIN_SIZE_MAP, MAX_SIZE_MAP, STEP_SIZE_MAP));
        add(heightLandscape);

        JButton btnResize = new JButton();
        btnResize.addActionListener((l) -> resize());
        btnResize.setText("Resize");
        add(btnResize);

        add(new JLabel("Block frequency"));
        blockFreq.addChangeListener((l) -> draw());
        add(blockFreq);

        JButton btnRandomDots = new JButton();
        btnRandomDots.addActionListener((l) -> randomiseDots());
        btnRandomDots.setText("RandomDots");
        add(btnRandomDots);

        JButton btnInterpolate = new JButton();
        btnInterpolate.addActionListener((l) -> interpolate());
        btnInterpolate.setText("Interpolate");
        add(btnInterpolate);

        JButton btnEmpty = new JButton();
        btnEmpty.addActionListener((l) -> empty());
        btnEmpty.setText("Empty");
        add(btnEmpty);

        JLabel objectName = new JLabel(dictNameObjects.get(currentObjectIndex));
        add(objectName);
    }

    public void empty() {
        MainFrame.getImagePanel().updateImageFrame(landscapePainter.getEmptyImage());
    }

    public void randomiseDots() {
        MainFrame.getImagePanel().updateImageFrame(landscapePainter.getRandomDotsImage(blockFreq.getValue() / 1000f));
    }

    public void resize() {
        MainFrame.getImagePanel().updateImageFrame(landscapePainter.getResizeImage(
            (Integer) widthLandscape.getValue(),
            (Integer) heightLandscape.getValue(),
            blockFreq.getValue() / 1000f
        ));
    }

    public void interpolate() {
        MainFrame.getImagePanel().updateImageFrame(landscapePainter.getInterpolatedImage(blockFreq.getValue() / 1000f));
    }

    public void draw() {
        MainFrame.getImagePanel().updateImageFrame(landscapePainter.getImageFromMap(blockFreq.getValue() / 1000f));
    }
}
