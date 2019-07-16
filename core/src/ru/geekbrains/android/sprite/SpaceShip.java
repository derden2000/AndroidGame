package ru.geekbrains.android.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.pool.BulletPool;
import ru.geekbrains.android.pool.EnemyShipPool;
import ru.geekbrains.android.pool.ExplosionPool;

public class SpaceShip extends ShipTemplate/*Sprite*/ {

    private static final int INVALID_POINTER = -1;
    private static final int HP_LEVEL = 100;

    private boolean pressedLeft;
    private boolean pressedRight;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

//    private BulletPool bulletPool;
    private LifeLevelPic lifeLevelPic;

    private Vector2 speed;
    private Vector2 moveSpeed = new Vector2(0.5f, 0f);;
//    private Vector2 touch;
//    private Rect worldBounds;
//    private TextureAtlas atlas;
//    private Sound shoot, explosion, laser;
    private int points;
    private int frags;

    public SpaceShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, LifeLevelPic lifeLevelPic, Sound shoot) {
        super(atlas.findRegion("ship"), 1, 2, 2);
        setHeightProportion(0.1f);
//        this.atlas = atlas;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.blowSound = explosionPool.getSound();
        this.shootSound = shoot;
        this.lifeLevelPic = lifeLevelPic;
        this.bulletRegion = atlas.findRegion("ownBullet");
        this.bulletSpeed = new Vector2(0, 0.5f);
        this.bulletHeight = 0.01f;
        this.hp = HP_LEVEL;
        this.damage = 1;
        speed = new Vector2();
//        touch = new Vector2();
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addFrags(int points) {
        this.frags += points;
    }

    public int getFrags() {
        return frags;
    }

    public void setFrags(int frags) {
        this.frags = frags;
    }

    @Override
    public void damage(int damagePoints) {
        super.damage(damagePoints);
        lifeLevelPic.strength -= damagePoints;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + lifeLevelPic.getHeight()+ 0.01f);
    }


    @Override
    public void update(Float delta) {
        super.update(delta);
        pos.mulAdd(speed, delta);
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        stop();
        pressedLeft = false;
        pressedRight = false;
        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        shootBullet();
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
                shootBullet();
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                }
                break;
            case Input.Keys.UP:
                break;
        }
        if (!pressedLeft && !pressedRight) {
            stop();
        }
        return false;
    }

    private void moveRight() {
        speed.set(moveSpeed);
    }

   private void moveLeft() {
        speed.set(moveSpeed).rotate(180);
    }

    public void stop() {
        speed.setZero();
    }

//    public void checkShoot(Bullet bullet) {
//        if (!bullet.getOwner().equals(this)) {
//            explosion.play(0.2f);
//            Gdx.input.vibrate(200);
//            bullet.destroy();
//            lifeLevelPic.strength --;
//        }
//    }

//    public void dispose() {
//        shoot.dispose();
//        explosion.dispose();
//        laser.dispose();
//    }

//    public void checkCross(BulletPool bulletPool, EnemyShipPool enemyShipPool) {
//        for (Bullet bullet : bulletPool.getActiveObjects()) {
//            if (!bullet.isOutside(this)) {
//                if (!bullet.getOwner().getClass().equals(this.getClass())) {
//                    blowSound.play(0.1f);
//                    this.damage(bullet.getDamage());
//                    Gdx.input.vibrate(200);
//                    bullet.destroy();
//                    lifeLevelPic.strength -= bullet.getDamage();
//                }
//            }
//        }
//        for (EnemyShip enemyShip : enemyShipPool.getActiveObjects()) {
//            float blowDist = this.getHalfHeight() + enemyShip.getHalfHeight();
//            if (this.pos.dst(enemyShip.pos) < blowDist/*!enemyShip.isOutside(this)*/) {
////                blowSound.play(0.2f);
////                enemyShip.blow();
//                Gdx.input.vibrate(200);
//                enemyShip.destroy();
//                lifeLevelPic.strength -=enemyShip.getHp();
//            }
//        }
//    }

    public void setToNewGame(Rect worldBounds) {
        flushDestroy();
        hp=HP_LEVEL;
        points = 0;
        frags = 0;
        this.pos.x = worldBounds.pos.x;
    }
}
