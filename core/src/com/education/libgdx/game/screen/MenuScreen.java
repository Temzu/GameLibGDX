package com.education.libgdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.education.libgdx.game.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture img;
    private Vector2 posObj;
    private Vector2 posTouch;
    private Vector2 vVector;
    private Vector2 buf;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        posObj = new Vector2();
        posTouch = new Vector2();
        vVector = new Vector2();
        buf = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.54f, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        buf.set(posTouch);
        if (buf.sub(posObj).len() > 10f) {
            posObj.add(vVector);
        }

        batch.begin();
        batch.draw(img, posObj.x, posObj.y);
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        posTouch.set(screenX, Gdx.graphics.getHeight() - screenY);
        vVector.set(posTouch.cpy().sub(posObj)).setLength(10f);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        posTouch.set(screenX, Gdx.graphics.getHeight() - screenY);
        vVector.set(posTouch.cpy().sub(posObj)).setLength(10f);
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean keyDown(int keycode) {

        return super.keyDown(keycode);
    }
}
