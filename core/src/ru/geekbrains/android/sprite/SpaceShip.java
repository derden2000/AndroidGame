package ru.geekbrains.android.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.Rect;

public class SpaceShip extends Sprite {

    private Vector2 speed;
    private Vector2 touch;
    private Rect worldBounds;

    public SpaceShip(TextureAtlas atlas) {
        super(atlas.findRegion("ship"));
        setHeightProportion(0.1f);
        pos.set(0, -0.5f+halfHeight);
        speed = new Vector2();
        touch = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }


    @Override
    public void update(Float delta) {
        super.update(delta);
        pos.mulAdd(speed, delta);
        if (getRight()-getWidth() < worldBounds.getLeft()) {speed.setZero();}
        if (getLeft()+getWidth() > worldBounds.getRight()) {speed.setZero();}
        if (getTop()-getHeight() < worldBounds.getBottom()) {speed.setZero();}
        if (getBottom()+getHeight() > worldBounds.getTop()) {speed.setZero();}
        if (this.isMe(touch)) {speed.setZero();}
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        speed = touch.cpy().sub(pos).scl(0.3f);
        this.touch = touch;
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println(keycode);
        if (keycode == 19) {
            speed.set(0, 0.5f);
        }
        if (keycode == 20) {
            speed.set(0, -0.5f);
        }
        if (keycode == 21) {
            speed.set(-0.5f, 0);
        }
        if (keycode == 22) {
            speed.set(0.5f, 0);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == 19 || keycode == 20 || keycode == 21 || keycode == 22) {
            speed.set(0, 0);
        }
        return false;
    }
}
