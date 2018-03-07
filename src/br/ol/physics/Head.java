package br.ol.physics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author leo
 */
public class Head {

    private BufferedImage image;
    private Particle w1;
    private Particle w2;
    private final Vec2 position = new Vec2();
    private double angle;
    private double width;
    private double height;
    
    public Head(Particle w1, Particle w2) {
        this.w1 = w1;
        this.w2 = w2;
        try {
            InputStream is = getClass().getResourceAsStream("/res/head.png");
            image = ImageIO.read(is);
        } catch (IOException ex) {
            Logger.getLogger(Wheel.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }
    
    public void update() {
        position.set(w2.getPosition());
        position.sub(w1.getPosition());
        width = 100;
        height = 100;
        
        angle = position.getAngle() + Math.toRadians(-90);
        
        position.scale(0.5);
        position.add(w1.getPosition());
    }
    
    public void draw(Graphics2D g){
        AffineTransform transform = g.getTransform();
        g.translate((int) position.getX(), (int) position.getY());
        g.rotate(angle);
        g.drawImage(image, (int) -width / 2 - 17, (int) -height + 8, (int) width, (int) height, null);
        g.setTransform(transform);
    }
    
}
