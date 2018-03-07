package br.ol.physics;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Stick class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Stick {

    private final Particle pa;
    private final Particle pb;
    private double distance;
    private final Vec2 direction = new Vec2();
    private double stiffness = 1;
    
    public Stick(Particle pa, Particle pb) {
        this.pa = pa;
        this.pb = pb;
        direction.set(pb.getPosition());
        direction.sub(pa.getPosition());
        this.distance = direction.getLength();
    }

    public Particle getPa() {
        return pa;
    }

    public Particle getPb() {
        return pb;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getStiffness() {
        return stiffness;
    }

    public void setStiffness(double stiffness) {
        this.stiffness = stiffness;
    }
    
    public void update() {
        direction.set(pb.getPosition());
        direction.sub(pa.getPosition());
        double dif = direction.getLength() - distance;
        direction.normalize();
        direction.scale(dif * 0.5 * stiffness);
        if (!pa.isPinned()) {
            pa.getPosition().add(direction);
        }
        if (!pb.isPinned()) {
            pb.getPosition().sub(direction);
        }
    }
    
    public void drawDebug(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.drawLine((int) pa.getPosition().getX(), (int) pa.getPosition().getY(), (int) pb.getPosition().getX(), (int) pb.getPosition().getY());
    }
    
}
