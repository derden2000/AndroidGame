package ru.geekbrains.android.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class ScaledTouchUpButton extends Sprite {

    private static final float ZOOM = 0.9f;
    private int pointer;
    private boolean isPressed;

    public ScaledTouchUpButton(TextureRegion region) {
        super(region);
    }



    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (!isPressed || this.pointer != pointer) {
            return false;
        }
        if (isMe(touch)) {
            action();
        }
        isPressed = false;
        scale = 1.0f;
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (isPressed || !isMe(touch)) {
            return false;
        }
        this.pointer = pointer;
        this.scale = ZOOM;
        isPressed = true;
        return false;
    }

    protected abstract void action();
}
