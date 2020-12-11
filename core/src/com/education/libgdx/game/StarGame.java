package com.education.libgdx.game;

import com.badlogic.gdx.Game;
import com.education.libgdx.game.screen.MenuScreen;

public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
