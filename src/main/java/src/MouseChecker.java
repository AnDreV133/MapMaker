package src;

import javax.swing.*;
import java.awt.event.*;

public class MouseChecker implements MouseListener, MouseMotionListener, MouseWheelListener {
    private final Point imagePlace = new Point();
    private final Point mousePlace = new Point();
    private double scale = 1.0;
    private final JPanel frame;

    public MouseChecker(JPanel frame) {
        this.frame = frame;
    }

    public Point getImagePlace() {
        return imagePlace;
    }

    public Point getMousePlace() {
        return mousePlace;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        requestFocusInWindow();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePlace.setXY(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (KeyCheckerForMode.isAltPressed) {

        } // для добавления объектов
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        imagePlace.setXY(
            imagePlace.getX() + e.getX() - mousePlace.getX(),
            imagePlace.getY() + e.getY() - mousePlace.getY()
        );

        frame.repaint();

        mousePlace.setXY(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        if (notches < 0) {
            scale *= 1.1; // увеличиваем масштаб на 10%
        } else if (scale >= 0.1) {
            scale /= 1.1; // уменьшаем масштаб на 10%
        }

        frame.repaint(); // перерисовываем компонент
    }
}
