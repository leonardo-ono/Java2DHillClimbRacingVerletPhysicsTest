package br.ol.physics;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Particle class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Puff extends Particle {

    private BufferedImage image;
    private double angle = 0;
    private double scale = 1;
    private double alpha = 0.7;
    private boolean destroyed = false;
    
    public Puff(World world, double x, double y, Vec2 velocity) {
        super(world, x, y);
        this.velocity.set(velocity);
        this.velocity.setX(-1 - 4 * Math.random());
        this.velocity.setY(-1 - 4 * Math.random());

        try {
            InputStream is = getClass().getResourceAsStream("/res/puff.png");
            image = ImageIO.read(is);
        } catch (IOException ex) {
            Logger.getLogger(Wheel.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void update() {
        angle += 0.1;
        scale += 0.1;
        alpha -= 0.02;
        alpha = alpha < 0 ? 0 : alpha;
        if (alpha == 0) {
            destroyed = true;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform transform = g.getTransform();
        g.translate((int) getPosition().getX(), (int) getPosition().getY());
        g.rotate(angle);
        g.scale(scale, scale);
        g.translate(-image.getWidth() / 2, -image.getHeight() / 2);
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha);
        Composite originalComposite = g.getComposite();
        g.setComposite(alphaComposite);
        g.drawImage(image, 0, 0, null);
        g.setComposite(originalComposite);
        g.setTransform(transform);   
    }
    
}
