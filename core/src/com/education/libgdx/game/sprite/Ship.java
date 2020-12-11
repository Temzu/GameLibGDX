package com.education.libgdx.game.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.education.libgdx.game.base.Sprite;
import com.education.libgdx.game.math.Rect;

public class Ship extends Sprite {
    private static final float HEIGHT = 0.25f;
    private static final float SPEED = 0.05f;
    private static boolean isHit = false;
    private static final float MARGIN = 0.05f;

    private final Vector2 v;
    private Rect worldBounds;

    public Ship(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"), 0);
        v = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    @Override
    public void update(float delta) {
        if (!isHit) {

        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        if (isMe(touch)) {
            pos.set(touch.x, pos.y);
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.RIGHT:
                if (!(getRight() > worldBounds.getRight())) {
                    v.set(SPEED, 0);
                    pos.add(v);
                }
                break;
            case Input.Keys.LEFT:
                if (!(getLeft() < worldBounds.getLeft())) {
                    v.set(-SPEED, 0);
                    pos.add(v);
                }
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }
}
