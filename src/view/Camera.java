package view;

import br.ol.physics.Body;
import br.ol.physics.Vec2;

/**
 *
 * @author leo
 */
public class Camera {
    private Body body;
    private Vec2 position = new Vec2();
    private Vec2 vec2Tmp = new Vec2();

    public Camera(Body body) {
        this.body = body;
    }

    public Vec2 getPosition() {
        return position;
    }

    public void update() {
        vec2Tmp.set(body.getPosition());
        vec2Tmp.sub(position);
        vec2Tmp.scale(0.2);
        position.add(vec2Tmp);
    }
    
}
