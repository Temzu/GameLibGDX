package com.education.libgdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.education.libgdx.game.base.BaseScreen;
import com.education.libgdx.game.math.Rect;
import com.education.libgdx.game.pool.BulletPool;
import com.education.libgdx.game.pool.EnemyPool;
import com.education.libgdx.game.pool.ExplosionPool;
import com.education.libgdx.game.sprite.Background;
import com.education.libgdx.game.sprite.Bullet;
import com.education.libgdx.game.sprite.ButtonNewGame;
import com.education.libgdx.game.sprite.Enemy;
import com.education.libgdx.game.sprite.GameOver;
import com.education.libgdx.game.sprite.MainShip;
import com.education.libgdx.game.sprite.Star;
import com.education.libgdx.game.sprite.TrackingStar;
import com.education.libgdx.game.utils.EnemyEmitter;
import com.education.libgdx.game.utils.Font;

import java.util.List;

public class GameScreen extends BaseScreen {

    public static final float MARGIN = 0.01f;
    private static final int STAR_COUNT = 128;
    private static final String FRAGS = "Frags: ";
    public static final String HP = "HP: ";
    public static final String LEVEL = "Level: ";

    private enum State {PLAYING, GAME_OVER}

    private Texture bg;
    private Background background;

    private TextureAtlas atlas;
    private TrackingStar[] stars;

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;

    private EnemyEmitter enemyEmitter;

    private MainShip mainShip;

    private Music music;
    private Sound bulletSound;
    private Sound explosionSound;

    private State state;

    private GameOver gameOver;
    private ButtonNewGame btnNewGame;

    private int frags;

    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;


    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas("textures/mainAtlas.tpack");

        bulletPool = new BulletPool();
        btnNewGame = new ButtonNewGame(atlas, this);
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        enemyEmitter = new EnemyEmitter(atlas, worldBounds, bulletSound, enemyPool);
        stars = new TrackingStar[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new TrackingStar(atlas, mainShip.getV());
        }
        gameOver = new GameOver(atlas);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        state = State.PLAYING;
        frags = 0;
        font = new Font("font\\font.fnt", "font\\font.png");
        font.setSize(0.02f);
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        btnNewGame.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        music.dispose();
        bulletSound.dispose();
        explosionSound.dispose();
        mainShip.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        }  else if (state == State.GAME_OVER) {
            btnNewGame.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            btnNewGame.touchUp(touch, pointer, button);
        }
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveObjects(delta);
        if (state == State.PLAYING) {
            bulletPool.updateActiveObjects(delta);
            enemyPool.updateActiveObjects(delta);
            mainShip.update(delta);
            enemyEmitter.generate(delta, frags);
        }
    }

    public void startNewGame() {
        frags = 0;
        mainShip.startNewGame();
        enemyPool.freeAll();
        bulletPool.freeAll();
        explosionPool.freeAll();
        state = State.PLAYING;
    }

    private void checkCollision() {
        if (state == State.GAME_OVER) {
            return;
        }
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(enemy.pos) < minDist) {
                enemy.destroy();
                mainShip.damage(enemy.getDamage());
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() == mainShip && enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemy.isDestroyed()) {
                        frags++;
                    }
                }
            }
        }
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() != mainShip && mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }
        if (mainShip.isDestroyed()) {
            state = State.GAME_OVER;
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.55f, 0.23f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (state == State.PLAYING) {
            bulletPool.drawActiveObjects(batch);
            enemyPool.drawActiveObjects(batch);
            mainShip.draw(batch);
        } else if (state == State.GAME_OVER) {
            gameOver.draw(batch);
            btnNewGame.draw(batch);
        }
        explosionPool.drawActiveObjects(batch);
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + MARGIN, worldBounds.getTop() - MARGIN);
        sbHp.setLength(0);
        font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - MARGIN, Align.center);
        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel() ), worldBounds.getRight() - MARGIN, worldBounds.getTop() - MARGIN, Align.right);
    }


}
