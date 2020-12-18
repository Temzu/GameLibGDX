package com.education.libgdx.game.pool;

import com.education.libgdx.game.base.SpritesPool;
import com.education.libgdx.game.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    public Bullet newObject() {
        return new Bullet();
    }
}
