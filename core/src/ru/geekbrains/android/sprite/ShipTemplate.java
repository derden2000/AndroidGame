package ru.geekbrains.android.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.pool.BulletPool;

public abstract class ShipTemplate extends Sprite {


    protected TextureRegion bulletRegion;
    protected Vector2 speed;
    protected Vector2 moveSpeed;
//    protected Vector2 v0;
    protected int hp;
    protected float bulletHeight;
    protected Sound shootSound;
    protected Vector2 bulletSpeed;
    protected int bulletDamage;
    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected int damage;


    public ShipTemplate(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public ShipTemplate() {

    }

    public ShipTemplate(BulletPool bulletPool, Rect worldBounds, Sound laser) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.shootSound = laser;
        regions = new TextureRegion[2];
        this.speed = new Vector2();
        this.moveSpeed = new Vector2(); // V0??
        this.bulletSpeed = new Vector2();
    }

    public int getHp() {
        return hp;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(Float delta) {
        super.update(delta);
//        pos.mulAdd(speed, delta);
    }

    protected void shootBullet() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletSpeed, bulletHeight, worldBounds, damage);
        shootSound.play();
    }
}
