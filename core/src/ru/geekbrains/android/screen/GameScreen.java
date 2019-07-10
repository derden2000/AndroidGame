package ru.geekbrains.android.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import ru.geekbrains.android.base.BaseScreen;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.math.Rnd;
import ru.geekbrains.android.pool.BulletPool;
import ru.geekbrains.android.pool.EnemyShipPool;
import ru.geekbrains.android.pool.ExplosionPool;
import ru.geekbrains.android.sprite.Background;
import ru.geekbrains.android.sprite.Bullet;
import ru.geekbrains.android.sprite.EnemyShip;
import ru.geekbrains.android.sprite.LifeLevelPic;
import ru.geekbrains.android.sprite.SpaceShip;
import ru.geekbrains.android.sprite.Star;
import ru.geekbrains.android.utils.EnemyGenerator;
import ru.geekbrains.android.utils.Font;

public class GameScreen extends BaseScreen {

    private static final int STARS_NUM = 10;
    private static final String POINTS = "Points: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";
    private StringBuilder sbPoints;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;

    private enum State {PLAY, PAUSE, GAME_OVER};
    public State state;
    public State oldState;

    private Texture bground;
    private Background background;
    private SpaceShip ship;

    private TextureAtlas atlas;
    private ArrayList<Star> stars = new ArrayList<Star>();
    private Game game;

    private BulletPool bulletPool;
    private EnemyShipPool enemyShipPool;
    private ExplosionPool explosionPool;
    private LifeLevelPic lifeLevelPic;
    private Music music;
    private Sound shoot, explosion;
    private Font font;

    private EnemyGenerator enemyGenerator;


    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bground = new Texture("space.jpg");
        background = new Background(new TextureRegion(bground));
        atlas = new TextureAtlas("textures/game_btn.pack");
        bulletPool = new BulletPool();
        shoot = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas, explosion);
        enemyShipPool = new EnemyShipPool(bulletPool, explosionPool, getWorldBounds(), shoot);
        lifeLevelPic = new LifeLevelPic(atlas, game, 100);
        ship = new SpaceShip(atlas, bulletPool, explosionPool, lifeLevelPic, shoot);
        for (int i = 0; i < STARS_NUM; i++) {
            stars.add(new Star(atlas));
        }
        font = new Font("font/sample.fnt", "font/sample.png");
        font.setSize(0.05f);
        sbPoints = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/classic.mp3"));
        music.play();
        music.setPosition(Rnd.nextFloat(20,800));
        music.setLooping(true);
        enemyGenerator = new EnemyGenerator(atlas, enemyShipPool, getWorldBounds(), bulletPool, ship);
        state = State.PLAY;
        enemyGenerator.generate(0.2f, ship.getFrags());
        Gdx.input.setCatchBackKey(true);
    }
    @Override
    public void pause() {
        super.pause();
        oldState = state;
        state = State.PAUSE;
        music.pause();
    }

    @Override
    public void resume() {
        super.resume();
        state = oldState;
        music.play();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        lifeLevelPic.resize(worldBounds);
        ship.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    public void update(Float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        if (state==State.PLAY) {
            ship.update(delta);
            lifeLevelPic.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyShipPool.updateActiveSprites(delta);
        }
        explosionPool.updateActiveSprites(delta);
    }

    public void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (state == State.PLAY) {
            lifeLevelPic.draw(batch);
            ship.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyShipPool.drawActiveSprites(batch);
            if (enemyShipPool.getActiveObjects().size() < 3) {
                enemyGenerator.generate(0.2f, ship.getFrags());
            }
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
        if (state == State.PLAY) {
            checkCross();
        } else if (state == State.GAME_OVER) {
            game.setScreen(new ChoiceScreen(game, ship.getPoints()));
        }
        freeAllDestroyedSprites();
    }

    public void freeAllDestroyedSprites() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyShipPool.freeAllDestroyedActiveSprites();
        if (enemyShipPool.getActiveObjects().isEmpty()) {
            enemyGenerator.generate(0.2f, ship.getFrags());
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        atlas.dispose();
        bground.dispose();
        bulletPool.dispose();
        enemyShipPool.dispose();
        music.dispose();
        font.dispose();
    }

    public void checkCross() {
        if (state != State.PLAY) {
            return;
        }
        enemyShipPool.checkCross(bulletPool);
        if (bulletPool.getActiveObjects()!=null) {
            for (Bullet bullet : bulletPool.getActiveObjects()) {
                if (!bullet.isOutside(ship)) {
                    if (!bullet.getOwner().getClass().equals(ship.getClass())) {
                        explosion.play(0.1f);
                        ship.damage(bullet.getDamage());
                        Gdx.input.vibrate(200);
                        bullet.destroy();
                        if (ship.isDestroyed()) {
                            state = State.GAME_OVER;
                        }
                    }
                }
            }
        }
        for (EnemyShip enemyShip : enemyShipPool.getActiveObjects()) {
            float blowDist = ship.getHalfHeight() + enemyShip.getHalfHeight();
            if (ship.pos.dst(enemyShip.pos) < blowDist) {
                ship.destroy();
                Gdx.input.vibrate(200);
                enemyShip.destroy();
                if (ship.isDestroyed()) {
                    state = State.GAME_OVER;
                }
            }
        }
    }

    public void resetGameSettings() {
        if (!bulletPool.equals(null) && !enemyShipPool.equals(null) && !explosionPool.equals(null)) {
            bulletPool.freeAllActiveSprites();
            enemyShipPool.freeAllActiveSprites();
            explosionPool.freeAllActiveSprites();
        }

        ship.setToNewGame(getWorldBounds());
        state = State.PLAY;
        enemyGenerator.setLevel(1);

    }

    private void printInfo() {
        sbPoints.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbPoints.append(POINTS).append(ship.getPoints()), getWorldBounds().getLeft(), getWorldBounds().getTop());
        font.draw(batch, sbHp.append(HP).append(ship.getHp()), getWorldBounds().pos.x, getWorldBounds().getTop(), Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyGenerator.getLevel()), getWorldBounds().getRight(), getWorldBounds().getTop(), Align.right);
    }

    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            game.setScreen(new MenuScreen(this.game));
        }
        if (state == State.PLAY) {
            ship.keyDown(keycode);
        } else {
            ship.stop();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAY) {
            ship.keyUp(keycode);
        } else {
            ship.stop();
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (state == State.PLAY) {
            super.touchDown(screenX, screenY, pointer, button);
            ship.touchDown(setTouch(), pointer);
            System.out.println("ship setTouch" + setTouch().toString());
        } else {
            ship.stop();
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (state == State.PLAY) {
            super.touchUp(screenX, screenY, pointer, button);
            ship.touchUp(setTouch(), pointer);
        } else {
            ship.stop();
        }
        return false;
    }
}
