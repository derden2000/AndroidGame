package ru.geekbrains.android.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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

public class GameScreen extends BaseScreen {

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
    private BitmapFont points = new BitmapFont();

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
        lifeLevelPic = new LifeLevelPic(atlas, game, 80);
        ship = new SpaceShip(atlas, bulletPool, explosionPool, lifeLevelPic, shoot);
        for (int i = 0; i < 10; i++) {
            stars.add(new Star(atlas));
        }
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/classic.mp3"));
        music.play();
        music.setPosition(Rnd.nextFloat(20,800));
        music.setLooping(true);
        enemyGenerator = new EnemyGenerator(atlas, enemyShipPool, getWorldBounds(), bulletPool, ship);
        createEnemyShip();
        Gdx.input.setCatchBackKey(true);
    }

    private void createEnemyShip() {
        enemyGenerator.generate(0.2f);
//        EnemyShip enemyShip = enemyShipPool.obtain();
//        enemyShip.set(atlas, getWorldBounds(), bulletPool, ship);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        ship.resize(worldBounds);
        lifeLevelPic.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    public void update(Float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        ship.update(delta);
        lifeLevelPic.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyShipPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
    }

    public void draw() {
        batch.begin();
        background.draw(batch);
        lifeLevelPic.draw(batch);
        ship.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        bulletPool.drawActiveSprites(batch);
        enemyShipPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        if (enemyShipPool.getActiveObjects().size() < 3) {
            createEnemyShip();
        }
        batch.end();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
        ship.checkCross(bulletPool, enemyShipPool); //можно 2 метода вынести в родительский класс.
        enemyShipPool.checkCross(bulletPool);      // не успеваю вынести
        freeAllDestroyedSprites();
    }

    public void freeAllDestroyedSprites() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyShipPool.freeAllDestroyedActiveSprites();
        if (enemyShipPool.getActiveObjects().isEmpty()) {
            createEnemyShip();
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
//        ship.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            game.setScreen(new MenuScreen(this.game));
        }
        ship.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        ship.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        ship.touchDown(setTouch(), pointer);
        System.out.println("ship setTouch" + setTouch().toString());
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX, screenY,pointer,button);
        ship.touchUp(setTouch(), pointer);
        ship.shoot();
        return false;
    }
}
