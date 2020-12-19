package com.education.libgdx.game.pool;

import com.education.libgdx.game.base.SpritesPool;
import com.education.libgdx.game.math.Rect;
import com.education.libgdx.game.sprite.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {
    private final BulletPool bulletPool;
    private final Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
    }

    @Override
    public Enemy newObject() {
        return new Enemy(bulletPool, worldBounds);
    }
}
