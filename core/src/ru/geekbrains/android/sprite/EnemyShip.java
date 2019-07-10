package ru.geekbrains.android.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.pool.BulletPool;
import ru.geekbrains.android.pool.ExplosionPool;

public class EnemyShip extends ShipTemplate/*Sprite*/ {

//    private Vector2 speed = new Vector2(); //111
//    private Vector2 bulletSpeed; //= new Vector2();
//    private Rect worldBounds;
//    private TextureAtlas atlas;
//    private float timeCount;
//    private Sound shoot, /*explosion,*/ laser;
    private enum State { APPROACH, FIGHT }

    private State state;

//    private Vector2 approachSpeed = new Vector2(0, -0.15f);
    private SpaceShip hero;
//    TextureAtlas atlas = new TextureAtlas("textures/game_btn.pack");
//    private int bulletDamage;


//    private BulletPool bulletPool;

//    public void set(
//            TextureAtlas atlas,
//            Rect worldBounds,
//            BulletPool bulletPool,
//            SpaceShip ship
//    ) {
//        this.atlas = atlas;
//        this.regions = Regions.split(atlas.findRegion("enemyBig"), 1 , 2, 2)/*atlas.findRegion("enemyBig")*/;
//        this.bulletPool = bulletPool;
//        this.hero = ship;
//        speed.set(Rnd.nextFloat(-0.01f, 0.01f), Rnd.nextFloat(-0.3f, -0.2f));
//        setHeightProportion(0.2f);
//        this.worldBounds = worldBounds;
//        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
//        float posY = worldBounds.getTop() - 0.01f;
//        pos.set(posX, posY);
//        shoot = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
//        explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
//        laser = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
//    } //111

    @Override
    public void update(Float delta) {
        super.update(delta);
        switch (state) {
            case APPROACH:
//                timeCount+=delta;
                if (getTop() > worldBounds.getTop()) {
                    pos.mulAdd(speed,delta*4);
//                    state=State.FIGHT;
                } else {
                    state = State.FIGHT;
                    break;
                }
//                break;
            case FIGHT:
                timeCount += delta;
                if (getBottom() < worldBounds.getBottom()) {
                    destroy();
                }
//                speed.set(moveSpeed);
                pos.mulAdd(speed,delta);
                if (timeCount > reloadInterval ) {
                    shootBullet();
                    timeCount=0;
                }
                break;
        }

//        timeCount += delta;
//        if (this.getBottom() + getHeight() > worldBounds.getTop()) {
//            pos.mulAdd(speed, delta*4);
//        }
//        pos.mulAdd(speed, delta);
//        if (timeCount > reloadInterval ) {
//            shootBullet();
//            timeCount=0;
//        }
//        if (isOutside(worldBounds)) {
//            destroy();
//        }
    }

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound laser) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
        this.blowSound = explosionPool.getSound();
        this.shootSound = laser;
        regions = new TextureRegion[2];
        this.speed = new Vector2();
        this.moveSpeed = new Vector2();
        this.bulletSpeed = new Vector2();
//        this.damage = 1;
        this.state = State.APPROACH;

    }

//    public void shoot() {
//        Bullet bullet = bulletPool.obtain();
//        bullet.set(this, bulletRegion/*atlas.findRegion("enemyBullet")*/, pos, (new Vector2(0, -0.6f)), 0.01f, worldBounds, 1);
//        laser.play();
//    }

//    protected void shootBullet() {
//        Bullet bullet = bulletPool.obtain();
//        bullet.set(this, bulletRegion, pos, bulletSpeed, bulletHeight, worldBounds, damage);
//    }

    public void checkShoot(Bullet bullet) {
//        int points = getPointsForKill();
        if (!bullet.getOwner().getClass().equals(this.getClass())) {
//            blowSound.play(0.1f);
            bullet.destroy();
            damage(bullet.getDamage());
            if (this.isDestroyed) {
                hero.addPoints(getPointsForKill());
                hero.addFrags(1);
            }
        }
    }

    public void set(TextureRegion[] regions,
                    Vector2 enemySpeed,
                    TextureRegion bulletRegion,
                    float bulletHeight,
                    float bulletSpeed,
                    int damage,
//                    float enemySmallReloadInterval,
                    int enemyHp,
                    float enemyHeight,
                    BulletPool bulletPool,
                    SpaceShip ship)
    {
        this.regions = regions;
        this.moveSpeed.set(enemySpeed);
        this.bulletPool = bulletPool;
        this.hero = ship;
        this.speed.set(enemySpeed);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletSpeed.set(0, bulletSpeed);
//        this.bulletDamage = damage;
        this.damage = damage;
//        this.reloadInterval = reloadInterval;
        this.hp = enemyHp;
        this.pointsForKill = enemyHp;
        setHeightProportion(enemyHeight);
        moveSpeed.set(enemySpeed);
        this.state = State.APPROACH;
    }
}
