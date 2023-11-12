package src;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static src.MainFrame.*;

public class SettingsPanel extends JPanel {
    private final HashMap<Integer, String> dictNameObjects = new HashMap<>() {{
        put(0, "cell");
        put(1, "stone");
        put(2, "fence");
        put(3, "tower");
        put(4, "house");
        put(5, "water");
    }};

    private int currentObjectIndex = 0;

    public HashMap<Integer, String> getDictNameObjects() {
        return dictNameObjects;
    }

    public int getCurrentObjectIndex() {
        return currentObjectIndex;
    }

    public void setCurrentObjectIndex(int currentObjectIndex) {
        this.currentObjectIndex = currentObjectIndex;
    }

    private final JSpinner widthLandscape = new JSpinner();
    private final JSpinner heightLandscape = new JSpinner();
    private final JSlider blockFreq = new JSlider(200, 800, 500);
    private final JLabel objectName = new JLabel();


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

        add(objectName);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        objectName.setText(dictNameObjects.get(currentObjectIndex));
    }

    public void empty() {
        MainFrame.getImagePanel().updateImageFrame(MainFrame.getLandscapePainter().getEmptyImage());
    }

    public void randomiseDots() {
        MainFrame.getImagePanel().updateImageFrame(
            MainFrame.getLandscapePainter().getRandomDotsImage(blockFreq.getValue() / 1000f)
        );
    }

    public void resize() {
        MainFrame.getImagePanel().updateImageFrame(MainFrame.getLandscapePainter().getResizeImage(
            (Integer) widthLandscape.getValue(),
            (Integer) heightLandscape.getValue(),
            blockFreq.getValue() / 1000f
        ));
    }

    public void interpolate() {
        MainFrame.getImagePanel().updateImageFrame(
            MainFrame.getLandscapePainter().getInterpolatedImage(blockFreq.getValue() / 1000f)
        );
    }

    public void draw() {
        MainFrame.getImagePanel().updateImageFrame(
            MainFrame.getLandscapePainter().getImageFromMap(blockFreq.getValue() / 1000f)
        );
    }
}
