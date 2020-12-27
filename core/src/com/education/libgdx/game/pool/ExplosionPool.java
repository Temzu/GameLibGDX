package com.education.libgdx.game.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.education.libgdx.game.base.SpritesPool;
import com.education.libgdx.game.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    private final TextureAtlas atlas;
    private final Sound sound;

    public ExplosionPool(TextureAtlas atlas, Sound sound) {
        this.atlas = atlas;
        this.sound = sound;
    }

    @Override
    public Explosion newObject() {
        return new Explosion(atlas.findRegion("explosion"), 9, 9, 74, sound);
    }
}