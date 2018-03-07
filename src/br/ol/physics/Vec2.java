package br.ol.physics;

/**
 * Vec2 class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Vec2 {
    
    private double x;
    private double y;

    public Vec2() {
    }

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2(Vec2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vec2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    public void add(Vec2 v) {
        this.x += v.x;
        this.y += v.y;
    }

    public void sub(Vec2 v) {
        this.x -= v.x;
        this.y -= v.y;
    }
    
    public void scale(double s) {
        scale(s, s);
    }

    public void scale(double sx, double sy) {
        this.x *= sx;
        this.y *= sy;
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }
    
    public void normalize() {
        double length = getLength();
        if (length != 0) {
            this.x /= length;
            this.y /= length;
        }
    }

    public double getAngle() {
        return Math.atan2(y, x);
    }

    public double dot(Vec2 v) {
        return x * v.x + y * v.y;
    }
    
    public void rotate(double angle) {
        double s = Math.sin(angle);
        double c = Math.sin(angle);
        double nx = x * c - y * s;
        double ny = x * s + y * c;
        this.x = nx;
        this.y = ny;
    }
    
    @Override
    public String toString() {
        return "Vec2{" + "x=" + x + ", y=" + y + '}';
    }

    
}
