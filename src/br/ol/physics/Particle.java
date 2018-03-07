package br.ol.physics;

import java.awt.Color;
import java.awt.Graphics2D;
import view.Mouse;

/**
 * Particle class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Particle {

    protected World world;
    protected boolean pinned;
    protected final Vec2 position = new Vec2();
    protected final Vec2 previousPosition = new Vec2();
    protected final Vec2 velocity = new Vec2();
    protected double restitution = 0.95;
    
    protected final Vec2 vec2Tmp = new Vec2();
    
    public Particle(World world, double x, double y) {
        this.world = world;
        this.position.set(x, y);
        this.previousPosition.set(x, y);
    }

    public World getWorld() {
        return world;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public Vec2 getPosition() {
        return position;
    }

    public void update() {
        if (pinned) {
            return;
        }
        
        velocity.set(position);
        velocity.sub(previousPosition);
        velocity.add(world.GRAVITY);
        
        vec2Tmp.set(Mouse.x, Mouse.y);
        vec2Tmp.sub(position);
        if (vec2Tmp.getLength() < 10) {
            vec2Tmp.set(20, 0);
            velocity.add(vec2Tmp);
        }
        
        previousPosition.set(position);
        position.add(velocity);
        
        updateConstraints();
    }
    
    private void updateConstraints() {
//        if (position.getY() >= 550) {
//            position.setY(550);
//            previousPosition.setY(550 + restitution * velocity.getY());
//        }
//        if (position.getX() <= 50) {
//            position.setX(50);
//            previousPosition.setX(50 + restitution * velocity.getX());
//        }
//        else if (position.getX() >= 750) {
//            position.setX(750);
//            previousPosition.setX(750 + restitution * velocity.getX());
//        }
    }
    
    public void drawDebug(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillOval((int) (position.getX() - 2), (int) (position.getY() - 2), 4, 4);
    }

    public void draw(Graphics2D g) {
    }
    
}
