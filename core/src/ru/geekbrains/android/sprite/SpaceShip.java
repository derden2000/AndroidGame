package ru.geekbrains.android.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.pool.BulletPool;

public class SpaceShip extends Sprite {

    private static final int INVALID_POINTER = -1;

    private boolean pressedLeft;
    private boolean pressedRight;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private BulletPool bulletPool;
    private LifeLevelPic lifeLevelPic;

    private Vector2 speed;
    private Vector2 moveSpeed = new Vector2(0.5f, 0f);;
    private Vector2 touch;
    private Rect worldBounds;
    private TextureAtlas atlas;
    private Sound shoot, explosion, laser;
    private int points;

    public SpaceShip(TextureAtlas atlas, BulletPool bulletPool, LifeLevelPic lifeLevelPic) {
        super(atlas.findRegion("ship"), 1, 2, 2);
        setHeightProportion(0.1f);
        this.atlas = atlas;
        this.bulletPool = bulletPool;
        this.lifeLevelPic = lifeLevelPic;
        speed = new Vector2();
        touch = new Vector2();
        shoot = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        laser = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
    }

    public CharSequence getPoints() {
        return Integer.toString(points);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + lifeLevelPic.getHeight()+ 0.01f);
    }


    @Override
    public void update(Float delta) {
        //super.update(delta);
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
    public boolean touchUp(Vector2 touch, int pointer) {
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
                shoot();
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

    public void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, atlas.findRegion("ownBullet"), pos, new Vector2(0, 0.5f), 0.01f, worldBounds, 1);
        laser.play();
        points++;
    }

    private void moveRight() {
        speed.set(moveSpeed);
    }

    private void moveLeft() {
        speed.set(moveSpeed).rotate(180);
    }

    private void stop() {
        speed.setZero();
    }

    public void checkShoot(Bullet bullet) {
        if (!bullet.getOwner().equals(this)) {
            explosion.play(0.2f);
            bullet.destroy();
            lifeLevelPic.strength --;
        }
    }
}
