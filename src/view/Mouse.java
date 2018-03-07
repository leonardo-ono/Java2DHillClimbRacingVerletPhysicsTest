package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Mouse class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Mouse extends MouseAdapter {
    
    public static int x;
    public static int y;
    public static boolean pressed;

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
    
}
