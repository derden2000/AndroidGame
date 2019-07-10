package ru.geekbrains.android.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.pool.BulletPool;
import ru.geekbrains.android.pool.ExplosionPool;

public abstract class ShipTemplate extends Sprite {


    protected TextureRegion bulletRegion;
    protected Vector2 speed;
    protected Vector2 moveSpeed;
    protected int hp;
    protected int pointsForKill;
    protected float bulletHeight;
    protected Sound shootSound;
    protected Sound blowSound;
    protected Vector2 bulletSpeed;
//    protected int bulletDamage;
    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected int damage;
    private float damageAnimateInterval = 0.2f;
    private float damageAnimateTimer = damageAnimateInterval;
    protected float timeCount;
    protected float reloadInterval = 1.5f;



    public ShipTemplate(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public ShipTemplate() {

    }

        public int getHp() {
        return hp;
    }

    public int getPointsForKill() {
        return pointsForKill;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(Float delta) {
        super.update(delta);
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= damageAnimateInterval) {
            frame = 0;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        blow();
    }

    public void shootBullet() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletSpeed, bulletHeight, worldBounds, damage);
        shootSound.play(0.2f);
    }

    protected void blow() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    public void damage(int damagePoints) {
        damageAnimateTimer = 0f;
        frame = 1;
        hp -= damagePoints;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
    }
}
