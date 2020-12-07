package com.education.libgdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.education.libgdx.game.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture img;


    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.54f, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean keyDown(int keycode) {
        
        return super.keyDown(keycode);
    }
}
