package com.education.libgdx.game.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.education.libgdx.game.base.Sprite;
import com.education.libgdx.game.math.Rect;

public class Logo extends Sprite {
    private Vector2 vTouch;
    private Vector2 buf;
    private Vector2 v;
    private float V_LEN = 0.005f;

    public Logo(Texture region) {
        super(new TextureRegion(region));
        vTouch = new Vector2();
        buf = new Vector2();
        v = new Vector2();
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        buf.set(vTouch);
        if (buf.sub(pos).len() > V_LEN) {
            pos.add(v);
        } else {
            pos.set(vTouch);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        vTouch.set(touch);
        v.set(vTouch.cpy().sub(pos)).setLength(0.005f);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        vTouch.set(touch);
        v.set(vTouch.cpy().sub(pos)).setLength(V_LEN);
        return super.touchDragged(touch, pointer);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.3f);
        this.pos.set(-0.1f, -0.1f);
    }
}
