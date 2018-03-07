package br.ol.physics;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import view.Camera;
import view.Keyboard;

/**
 * World class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class World {
    
    public static final Vec2 GRAVITY = new Vec2(0, 0.5);
    
    private Camera camera;
    private final List<Particle> particles = new ArrayList<Particle>();
    private final List<Stick> sticks = new ArrayList<Stick>();
    private final List<Capsule> capsules = new ArrayList<Capsule>();
    private final List<Puff> puffs = new ArrayList<Puff>();
    private final List<Puff> puffsRemove = new ArrayList<Puff>();
    
    private Body body;
    private Head head;
    
    private Wheel pk, pl;

    public List<Capsule> getCapsules() {
        return capsules;
    }
    
    public void start() {
        Particle pa, pb, pg, ph, pi, pj;
        particles.add(pa = new Particle(this, 145, 60));
        particles.add(pb = new Particle(this, 145, 100));
        
        particles.add(pg = new Particle(this, 100, 100));
        particles.add(ph = new Particle(this, 225, 100));
        particles.add(pi = new Particle(this, 225, 150));
        particles.add(pj = new Particle(this, 100, 150));
        
        particles.add(pk = new Wheel(this, 100, 180, 30));
        particles.add(pl = new Wheel(this, 225, 180, 30));
        body = new Body(pj, pi);
        camera = new Camera(body);
        head = new Head(pa, pb);
        
        Stick se, sf, sg, sh, si, sj;
        sticks.add(new Stick(pg, pb));
        sticks.add(new Stick(pb, ph));
        sticks.add(se = new Stick(pa, pg));
        sticks.add(sf = new Stick(pa, pb));
        sticks.add(sg = new Stick(pb, pj));
        sticks.add(sh = new Stick(pa, ph));
        sticks.add(si = new Stick(pa, pj));
        sticks.add(sj = new Stick(pa, pi));
        se.setStiffness(0.01);
        sg.setStiffness(1);
        sh.setStiffness(0.01);
        si.setStiffness(0.025);
        sj.setStiffness(0.025);
        
        sticks.add(new Stick(pg, ph));
        sticks.add(new Stick(ph, pi));
        sticks.add(new Stick(pi, pj));
        sticks.add(new Stick(pj, pg));
        sticks.add(new Stick(pg, pi));
        sticks.add(new Stick(ph, pj));
        
        Stick sa, sb, sc, sd;
        sticks.add(sa = new Stick(pj, pk));
        sticks.add(new Stick(pk, pl));
        sticks.add(sb = new Stick(pl, pi));
        sticks.add(new Stick(pj, pl));
        sticks.add(new Stick(pk, pi));
        
        sticks.add(sc = new Stick(pg, pl));
        sticks.add(sd = new Stick(ph, pk));
        
        sa.setStiffness(0.01);
        sb.setStiffness(0.01);
        sc.setStiffness(0.5);
        sd.setStiffness(0.5);
        
        createBridge();
        createTerrain();
    }
    
    private void createBridge() {
        double radius = 10;
        Circle c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11;
        particles.add(c1 = new Circle(this, -600, 550, radius));
        particles.add(c2 = new Circle(this, -500, 550, radius));
        particles.add(c3 = new Circle(this, -450, 550, radius));
        particles.add(c4 = new Circle(this, -400, 550, radius));
        particles.add(c5 = new Circle(this, -350, 550, radius));
        particles.add(c6 = new Circle(this, -300, 550, radius));
        particles.add(c7 = new Circle(this, -250, 550, radius));
        particles.add(c8 = new Circle(this, -200, 550, radius));
        particles.add(c9 = new Circle(this, -150, 550, radius));
        particles.add(c10 = new Circle(this, -100, 550, radius));
        particles.add(c11 = new Circle(this, 0, 550, radius));
        c1.setPinned(true);
        c11.setPinned(true);
        sticks.add(new Stick(c1, c2));
        sticks.add(new Stick(c2, c3));
        sticks.add(new Stick(c3, c4));
        sticks.add(new Stick(c4, c5));
        sticks.add(new Stick(c5, c6));
        sticks.add(new Stick(c6, c7));
        sticks.add(new Stick(c7, c8));
        sticks.add(new Stick(c8, c9));
        sticks.add(new Stick(c9, c10));
        sticks.add(new Stick(c10, c11));
        capsules.add(new Capsule(this, c1, c2));
        capsules.add(new Capsule(this, c2, c3));
        capsules.add(new Capsule(this, c3, c4));
        capsules.add(new Capsule(this, c4, c5));
        capsules.add(new Capsule(this, c5, c6));
        capsules.add(new Capsule(this, c6, c7));
        capsules.add(new Capsule(this, c7, c8));
        capsules.add(new Capsule(this, c8, c9));
        capsules.add(new Capsule(this, c9, c10));
        capsules.add(new Capsule(this, c10, c11));
    }
    
    public void createTerrain() {
        double radius = 10;
        
        Circle c7, c8;
        particles.add(c7 = new Circle(this, -2000, 550, radius));
        particles.add(c8 = new Circle(this, -600, 550, radius));
        c7.setPinned(true);
        c8.setPinned(true);
        capsules.add(new Capsule(this, c7, c8));
        
        Circle c1, c2;
        particles.add(c1 = new Circle(this, 0, 550, radius));
        particles.add(c2 = new Circle(this, 3800, 350, radius));
        c1.setPinned(true);
        c2.setPinned(true);
        capsules.add(new Capsule(this, c1, c2));

        // rampa
        Circle c3, c4;
        particles.add(c3 = new Circle(this, 2500, 550, radius));
        particles.add(c4 = new Circle(this, 2800, 450, radius));
        c3.setPinned(true);
        c4.setPinned(true);
        capsules.add(new Capsule(this, c3, c4));

        Circle c5, c6;
        particles.add(c5 = new Circle(this, 3000, 450, radius));
        particles.add(c6 = new Circle(this, 3300, 550, radius));
        c5.setPinned(true);
        c6.setPinned(true);
        capsules.add(new Capsule(this, c5, c6));

        radius = 30;
        
        double k = 0;
        Circle co, cp;
        particles.add(cp = new Circle(this, 3800, 350 + 0, radius));
        for (int x = 3860; x < 9000; x+=60) {
            double y = 200 * Math.sin(k);
            k += 0.2;
            particles.add(co = new Circle(this, x, 350 + y, radius));
            capsules.add(new Capsule(this, cp, co));
            co.setPinned(true);
            cp.setPinned(true);
            cp = co;
            radius = 10 + 20 * Math.random();
        }

        
    }
    
    
    public void start2() {
        Particle pa, pb, pc, pd, pe, pf;
        particles.add(pa = new Particle(this, 200, 200));
        particles.add(pb = new Particle(this, 220, 200));
        particles.add(pc = new Particle(this, 240, 200));
        particles.add(pd = new Particle(this, 260, 200));
        particles.add(pe = new Particle(this, 280, 200));
        particles.add(pf = new Particle(this, 300, 200));
        
        //pa.setPinned(true);
        
        Stick stick1 = new Stick(pa, pb);
        Stick stick2 = new Stick(pb, pc);
        Stick stick3 = new Stick(pc, pd);
        Stick stick4 = new Stick(pd, pe);
        Stick stick5 = new Stick(pe, pf);
        sticks.add(stick1);
        sticks.add(stick2);
        sticks.add(stick3);
        sticks.add(stick4);
        sticks.add(stick5);

        Particle pg, ph, pi, pj;
        particles.add(pg = new Particle(this, 325, 175));
        particles.add(ph = new Particle(this, 375, 175));
        particles.add(pi = new Particle(this, 375, 225));
        particles.add(pj = new Particle(this, 325, 225));
        sticks.add(new Stick(pg, ph));
        sticks.add(new Stick(ph, pi));
        sticks.add(new Stick(pi, pj));
        sticks.add(new Stick(pj, pg));
        sticks.add(new Stick(pg, pi));
        sticks.add(new Stick(ph, pj));
        
        sticks.add(new Stick(pf, pg));
        sticks.add(new Stick(pf, pj));
    }
    
    public void update() {
        if (Keyboard.keyDown[KeyEvent.VK_LEFT]) {
            pk.getPosition().setX(pk.getPosition().getX() - 1);
        }
        else if (Keyboard.keyDown[KeyEvent.VK_RIGHT]) {
            pk.getPosition().setX(pk.getPosition().getX() + 1);
        }

        if (Keyboard.keyDown[KeyEvent.VK_UP]) {
            pk.getPosition().setY(pk.getPosition().getY() - 5);
            pl.getPosition().setY(pk.getPosition().getY() - 5);
        }
        else if (Keyboard.keyDown[KeyEvent.VK_DOWN]) {
            pk.getPosition().setY(pk.getPosition().getY() + 10);
            pl.getPosition().setY(pk.getPosition().getY() + 10);
        }
        
        for (Particle particle : particles) {
            particle.update();
        }
        for (int i = 0; i < 7; i++) {
            for (Stick stick : sticks) {
                stick.update();
            }
        }
        body.update();
        head.update();
        
        for (Capsule capsule : capsules) {
            capsule.update();
        }
        
        
        for (Puff puff : puffs) {
            puff.update();
        }
        
        camera.update();
    }
    
    public void drawDebug(Graphics2D g) {
//        for (Particle particle : particles) {
//            particle.drawDebug(g);
//        }
//        for (Stick stick : sticks) {
//            stick.drawDebug(g);
//        }
        for (Capsule capsule : capsules) {
            capsule.drawDebug(g);
        }
    }

    public void draw(Graphics2D g) {
        g.translate(-camera.getPosition().getX() + 400, -camera.getPosition().getY() + 300);
        body.draw(g);
        head.draw(g);
        for (Particle particle : particles) {
            particle.draw(g);
        }
        for (Puff puff : puffs) {
            puff.draw(g);
            if (puff.isDestroyed()) {
                puffsRemove.add(puff);
            }
        }
        if (!puffsRemove.isEmpty()) {
            puffs.removeAll(puffsRemove);
            puffsRemove.clear();
        }
    }
    
    private long lastSpawnPuffTime;
    
    public void spawnPuff(int x, int y, Vec2 velocity) {
        if (System.currentTimeMillis() - lastSpawnPuffTime < 50) {
            return;
        }
        puffs.add(new Puff(this, x, y, velocity));
        lastSpawnPuffTime = System.currentTimeMillis();
    }
    
}
