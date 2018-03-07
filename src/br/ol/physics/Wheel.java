package br.ol.physics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import view.Keyboard;
import view.Mouse;

/**
 * Particle class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Wheel extends Circle {
    
    private BufferedImage image;
    
    private double angle;
    
    private final Vec2 vec2Tmp = new Vec2();
    private final Vec2 vec2Tmp2 = new Vec2();
    
    private boolean accelerating;
    private double desiredSpeed = 10;
    
    private boolean skidding;
    
    public Wheel(World world, double x, double y, double radius) {
        super(world, x, y, radius);
        
        try {
            InputStream is = getClass().getResourceAsStream("/res/wheel.png");
            image = ImageIO.read(is);
        } catch (IOException ex) {
            Logger.getLogger(Wheel.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }

    @Override
    public void update() {
        if (pinned) {
            return;
        }
        
        accelerating = Keyboard.keyDown[KeyEvent.VK_RIGHT];
        
        velocity.set(getPosition());
        velocity.sub(previousPosition);
        velocity.add(world.GRAVITY);
        
        vec2Tmp.set(Mouse.x, Mouse.y);
        vec2Tmp.sub(getPosition());
        if (vec2Tmp.getLength() < 10) {
            vec2Tmp.set(20, 0);
            velocity.add(vec2Tmp);
        }
        
        previousPosition.set(getPosition());
        getPosition().add(velocity);
        
        updateConstraints();
    }
    
    private void updateConstraints() {
        boolean collided = false;
        
        for (Capsule capsule : world.getCapsules()) {
            if (capsule.collides(this, vec2Tmp2)) {
                
                vec2Tmp2.scale(0.5);
                
                if (!capsule.getC1().isPinned()) {
                    capsule.getC1().getPosition().sub(vec2Tmp2);
                }
                if (!capsule.getC2().isPinned()) {
                    capsule.getC2().getPosition().sub(vec2Tmp2);
                }
                
                getPosition().add(vec2Tmp2);
                double nx = vec2Tmp2.getX();
                double ny = vec2Tmp2.getY();
                vec2Tmp2.set(-ny, nx);
                vec2Tmp2.normalize();
                
                //velocity.set(getPosition());
                //velocity.sub(previousPosition);
                velocity.sub(World.GRAVITY);
                
                double v = velocity.dot(vec2Tmp2);
                
                if (accelerating) {
                    if (Math.abs(v - desiredSpeed) > 1) {
                        skidding = true;
                        world.spawnPuff((int) getPosition().getX(), (int) (getPosition().getY() + getRadius()), velocity);
                    }
                    v = desiredSpeed;
                }
                else {
                    skidding = false;
                }
                
                angle += v * 0.02;
                collided = true;
            }
        }

        if (!collided) {
            double v = 0;
            if (accelerating) {
                v = desiredSpeed;
            }
            angle += v * 0.02;
        }
        
        // floor temporary
//        if (getPosition().getY() + getRadius() >= 550) {
//            getPosition().setY(550 - getRadius());
//            previousPosition.setY(550 - getRadius() + restitution * velocity.getY());
//            angle += velocity.getX() * 0.03;
//        }
//        if (getPosition().getX() - getRadius() <= 50) {
//            getPosition().setX(50 + getRadius());
//            previousPosition.setX(50 + getRadius() + restitution * velocity.getX());
//        }
//        else if (getPosition().getX() + getRadius() >= 750) {
//            getPosition().setX(750 - getRadius());
//            previousPosition.setX(750 - getRadius() + restitution * velocity.getX());
//        }
        
        //System.out.println("skidding: " + skidding);
    }
    
    @Override
    public void drawDebug(Graphics2D g) {
        g.setColor(Color.RED);
        AffineTransform transform = g.getTransform();
        g.translate((int) getPosition().getX(), (int) getPosition().getY());
        g.rotate(angle);
        
        g.drawLine(0, 0, (int) getRadius(), 0);
        g.drawOval((int) -getRadius(), (int) -getRadius(), (int) (2 * getRadius()), (int) (2 * getRadius()));
        g.fillOval(-2, -2, 4, 4);

        //g.drawOval((int) (position.getX() - radius), (int) (position.getY() - radius), (int) (2 * radius), (int) (2 * radius));
        //g.fillOval((int) (position.getX() - 2), (int) (position.getY() - 2), 4, 4);
        g.setTransform(transform);
    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform transform = g.getTransform();
        g.translate((int) getPosition().getX(), (int) getPosition().getY());
        g.rotate(angle);
        g.drawImage(image, (int) -getRadius(), (int) -getRadius(), (int) (2 * getRadius()), (int) (2 * getRadius()), null);
        g.setTransform(transform);
    }
    
}
