package ru.geekbrains.android.pool;

import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.android.base.SpritesPool;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.sprite.Bullet;
import ru.geekbrains.android.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Rect worldBounds;
    private Sound shootSound;

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, explosionPool, worldBounds, shootSound);
    }

    public EnemyShipPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound shootSound) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.shootSound = shootSound;
    }

    public void checkCross(BulletPool bulletPool) {
        for (Bullet bullet : bulletPool.getActiveObjects()) {
            for (EnemyShip enemyShip : this.getActiveObjects()) {
                if (!bullet.isOutside(enemyShip)) {
                    enemyShip.checkShoot(bullet);
                }
            }
        }
    }
}
