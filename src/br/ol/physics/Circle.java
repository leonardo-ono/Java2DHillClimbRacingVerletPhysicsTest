package br.ol.physics;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author leonardo
 */
public class Circle extends Particle {

    private Vec2 position = new Vec2();
    private double radius = 20;
    private final Vec2 vec2Tmp = new Vec2();

    public Circle(World world, double x, double y, double radius) {
        super(world, x, y);
        this.radius = radius;
    }
    
    public double getRadius() {
        return radius;
    }
    
    public boolean collides(Circle other) {
        vec2Tmp.set(other.getPosition());
        vec2Tmp.sub(position);
        double length = vec2Tmp.getLength();
        return length <= radius + other.getRadius();
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.ORANGE);
        g.drawOval((int) (position.getX() - radius), (int) (position.getY() - radius), (int) (2 * radius), (int) (2 * radius));
    }
    
}
