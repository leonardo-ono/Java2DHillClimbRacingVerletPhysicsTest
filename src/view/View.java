package view;

import br.ol.physics.World;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author leonardo
 */
public class View extends JPanel implements KeyListener {
    
    private final World world = new World();
    private final SlashTrail slashTrail = new SlashTrail(20);
    
    public View() {
        world.start();
        
        Mouse mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        
        addKeyListener(new Keyboard());
        
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                update();
                repaint();
            }
        }, 100, 1000 / 55);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // g2d.scale(0.5, 0.5);
        draw(g2d);
    }
    
    public void update() {
        world.update();
        
        //if (Mouse.pressed) {
        //    slashTrail.addTrail(Mouse.x, Mouse.y);
        //}
        //else {
        //    slashTrail.standBy();
        //}
    }
    
    public void draw(Graphics2D g) {
        world.draw(g);
        world.drawDebug(g);
        
        if (Mouse.pressed) {
            g.setColor(Color.ORANGE);
            g.fillOval(Mouse.x - 2, Mouse.y - 2, 4, 4);
        }
        
        slashTrail.drawDebug(g);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View view = new View();
                JFrame frame = new JFrame();
                frame.setTitle("Verlet");
                frame.getContentPane().add(view);
                frame.setSize(800, 600);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                frame.setVisible(true);
                view.requestFocus();
            }
        });
    }    

    // KeyListener implementation 
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
