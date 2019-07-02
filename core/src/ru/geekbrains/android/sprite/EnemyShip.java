package ru.geekbrains.android.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.math.Rnd;
import ru.geekbrains.android.pool.BulletPool;

public class EnemyShip extends Sprite {

    private Vector2 speed = new Vector2();
    private Rect worldBounds;
    private TextureAtlas atlas;
    private float timeCount;
    private Sound shoot, explosion, laser;


    private BulletPool bulletPool;

    public void set(
            TextureAtlas atlas,
            Rect worldBounds,
            BulletPool bulletPool
    ) {
        this.atlas = atlas;
        this.regions[0] = atlas.findRegion("enemyship");
        this.bulletPool = bulletPool;
        speed.set(Rnd.nextFloat(-0.01f, 0.01f), Rnd.nextFloat(-0.3f, -0.2f));
        setHeightProportion(0.1f);
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = worldBounds.getTop() - 0.01f;
        pos.set(posX, posY);
        shoot = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        laser = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
    }

    @Override
    public void update(Float delta) {
        timeCount += delta;
        pos.mulAdd(speed, delta);
        if (timeCount > 1.5f ) {
            shoot();
            timeCount=0;
        }
        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    public EnemyShip() {
        regions = new TextureRegion[1];
    }

    public void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, atlas.findRegion("enemyBullet"), pos, (new Vector2(0, -0.6f)), 0.01f, worldBounds, 1);
        laser.play();
    }

    public void checkShoot(Bullet bullet) {
        if (!bullet.getOwner().getClass().equals(this.getClass())) {
            explosion.play(0.1f);
            destroy();
            bullet.destroy();
        }
    }
}
