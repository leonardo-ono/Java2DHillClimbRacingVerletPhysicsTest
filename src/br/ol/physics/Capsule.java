package br.ol.physics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 *
 * @author leonardo
 */
public class Capsule {

    private Circle c1;
    private Circle c2;
    private double radius;
    
    private final Vec2 vec2Tmp = new Vec2();
    private final Vec2 vec2Tmp2 = new Vec2();
    private final Circle cc;
    
    public Capsule(World world, double x1, double y1, double x2, double y2, double radius) {
        c1 = new Circle(world, x1, y1, radius);
        c2 = new Circle(world, x2, y2, radius);
        cc = new Circle(world, x1, y1, radius);
        this.radius = radius;
    }

    public Capsule(World world, Circle c1, Circle c2) {
        this.c1 = c1;
        this.c2 = c2;
        this.radius = c1.getRadius();
        cc = new Circle(world, c1.getPosition().getX(), c1.getPosition().getY(), c1.getRadius());
    }

    public Circle getC1() {
        return c1;
    }

    public Circle getC2() {
        return c2;
    }
    
    public void update() {
    }
    
    public boolean collides(Circle other, Vec2 collisionNormal) {
        vec2Tmp2.set(c2.getPosition());
        vec2Tmp2.sub(c1.getPosition());
        double maxLength = vec2Tmp2.getLength();
        vec2Tmp2.normalize();

        vec2Tmp.set(other.getPosition());
        vec2Tmp.sub(c1.getPosition());
        
        double length = vec2Tmp.dot(vec2Tmp2);
        length = length < 0 ? 0 : length > maxLength ? maxLength : length;
        
        vec2Tmp2.scale(length);
        vec2Tmp2.add(c1.getPosition());
        
        cc.getPosition().set(vec2Tmp2);
        
        vec2Tmp.set(other.getPosition());
        vec2Tmp.sub(cc.getPosition());
        double penetration = radius + other.getRadius() - vec2Tmp.getLength();
        
        collisionNormal.set(other.getPosition());
        collisionNormal.sub(cc.getPosition());
        collisionNormal.normalize();
        collisionNormal.scale(penetration);
        
        return penetration >= 0;
    }
    
    public void drawDebug(Graphics2D g) {
        g.setColor(Color.BLACK);
        
        vec2Tmp.set(c2.getPosition());
        vec2Tmp.sub(c1.getPosition());
        double length = vec2Tmp.getLength();
        AffineTransform at = g.getTransform();
        g.translate(c1.getPosition().getX(), c1.getPosition().getY());
        g.rotate(vec2Tmp.getAngle());
        g.drawOval((int) -radius, (int) -radius, (int) (2 * radius), (int) (2 * radius));
        g.drawOval((int) (length - radius), (int) -radius, (int) (2 * radius), (int) (2 * radius));
        g.drawLine(0, (int) -radius, (int) length, (int) -radius);
        g.drawLine(0, (int) radius, (int) length, (int) radius);
        g.setTransform(at);
        
        g.fillOval((int) (cc.getPosition().getX() - radius), (int) (cc.getPosition().getY() - radius), (int) (2 * radius), (int) (2 * radius));
    }
    
}
