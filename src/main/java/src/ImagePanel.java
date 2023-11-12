package src;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private final ImageIcon imageIcon = new ImageIcon();
    private final MouseChecker mouseChecker = new MouseChecker(this);

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public ImagePanel() {
//        MainFrame.getSettingsPanel().empty();

        addMouseListener(mouseChecker); // добавляем слушателей событий мыши
        addMouseMotionListener(mouseChecker);
        addMouseWheelListener(mouseChecker);
        addKeyListener(new KeyCheckerForMode());

        mouseChecker.getImagePlace().setXY(
            (MainFrame.WIDTH_IMAGE_SPACE - imageIcon.getIconWidth()) / 2,
            (MainFrame.HEIGHT_SPACE - imageIcon.getIconHeight()) / 2
        );
    }

    @Override
    public void paintComponent(Graphics g) { // переопределяем метод отрисовки компонента
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; // используем 2D графику
        g2d.scale(mouseChecker.getScale(), mouseChecker.getScale()); // масштабируем изображение
        g2d.drawImage(
            imageIcon.getImage(),
            (int) (mouseChecker.getImagePlace().getX() / mouseChecker.getScale()),
            (int) (mouseChecker.getImagePlace().getY() / mouseChecker.getScale()),
            this
        );
    }

    public void updateImageFrame(BufferedImage image) {
        imageIcon.setImage(image);
        updateImageFrame();
    }

    public void updateImageFrame() {
        repaint();
    }
}
