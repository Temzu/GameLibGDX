package com.education.libgdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.education.libgdx.game.base.BaseButton;
import com.education.libgdx.game.math.Rect;
import com.education.libgdx.game.screen.GameScreen;

public class ButtonNewGame extends BaseButton {
    private GameScreen gameScreen;

    public ButtonNewGame(TextureAtlas atlas, GameScreen screen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = screen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.06f);
        setBottom(0.09f);
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
