package ru.geekbrains.android.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.math.Rnd;
import ru.geekbrains.android.pool.BulletPool;
import ru.geekbrains.android.pool.EnemyShipPool;
import ru.geekbrains.android.sprite.EnemyShip;
import ru.geekbrains.android.sprite.SpaceShip;

public class EnemyGenerator {

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_SMALL_HP = 1;

    private static final float ENEMY_MIDDLE_HEIGHT = 0.1f;
    private static final float ENEMY_MIDDLE_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_MIDDLE_BULLET_VY = -0.25f;
    private static final int ENEMY_MIDDLE_DAMAGE = 5;
    private static final float ENEMY_MIDDLE_RELOAD_INTERVAL = 4f;
    private static final int ENEMY_MIDDLE_HP = 2;

    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final float ENEMY_BIG_BULLET_VY = -0.3f;
    private static final int ENEMY_BIG_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 1f;
    private static final int ENEMY_BIG_HP = 3;

    private TextureRegion[] enemySmallRegion;
    private TextureRegion[] enemyMiddleRegion;
    private TextureRegion[] enemyBigRegion;

    private Vector2 enemySmallV;
    private Vector2 enemyMiddleV;
    private Vector2 enemyBigV;

    private TextureRegion bulletRegion;
    private EnemyShipPool enemyShipPool;
    private SpaceShip hero;
    private BulletPool bulletPool;
    TextureAtlas atlas;


    private float generateInterval = 4f;
    private float generateTimer;

    private Rect worldBounds;

    public EnemyGenerator(TextureAtlas atlas, EnemyShipPool enemyShipPool, Rect worldBounds, BulletPool bulletPool, SpaceShip hero) {
        this.atlas = atlas;
        this.enemyShipPool = enemyShipPool;
        this.bulletPool = bulletPool;
        this.hero = hero;
        this.worldBounds = worldBounds;
        this.enemySmallV = new Vector2(0f, -0.2f);
        this.enemyMiddleV = new Vector2(0f, -0.1f); //03
        this.enemyBigV = new Vector2(0f, -0.05f);//005
        TextureRegion region0 = atlas.findRegion("enemySmall");
        enemySmallRegion = Regions.split(region0, 1, 2, 2);
        TextureRegion region1 = atlas.findRegion("enemyMiddle");
        enemyMiddleRegion = Regions.split(region1, 1, 2, 2);
        TextureRegion region2= atlas.findRegion("enemyBig");
        enemyBigRegion = Regions.split(region2, 1, 2, 2);
        bulletRegion = this.atlas.findRegion("enemyBullet");
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            EnemyShip enemyShip = enemyShipPool.obtain();
//            enemyShip.set(atlas, worldBounds, bulletPool, hero);
            float type = (float)Math.random();
            if (type < 0.5f) {
                enemyShip.set(
                        enemySmallRegion,
                        enemySmallV,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VY,
                        ENEMY_SMALL_DAMAGE,
//                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HP,
                        ENEMY_SMALL_HEIGHT,
                        bulletPool,
                        hero
                );
            } else if (type < 0.8f){
                enemyShip.set(
                        enemyMiddleRegion,
                        enemyMiddleV,
                        bulletRegion,
                        ENEMY_MIDDLE_BULLET_HEIGHT,
                        ENEMY_MIDDLE_BULLET_VY,
                        ENEMY_MIDDLE_DAMAGE,
//                        ENEMY_MIDDLE_RELOAD_INTERVAL,
                        ENEMY_MIDDLE_HP,
                        ENEMY_MIDDLE_HEIGHT,
                        bulletPool,
                        hero
                );
            } else {
                enemyShip.set(
                        enemyBigRegion,
                        enemyBigV,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VY,
                        ENEMY_BIG_DAMAGE,
//                        ENEMY_BIG_RELOAD_INTERVAL,
                        ENEMY_BIG_HP,
                        ENEMY_BIG_HEIGHT,
                        bulletPool,
                        hero
                );
            }
            enemyShip.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemyShip.getHalfWidth(), worldBounds.getRight() - enemyShip.getHalfWidth());
            enemyShip.setBottom(worldBounds.getTop() - 0.01f);
        }
    }
}
