package ru.geekbrains.android.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.android.base.ScaledTouchUpButton;
import ru.geekbrains.android.math.Rect;

public class ButtonFinish extends ScaledTouchUpButton {
    public ButtonFinish(TextureAtlas atlas) {
        super(atlas.findRegion("btnFinish"));
    }

    @Override
    protected void action() {
        Gdx.app.exit();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.1f);
        pos.set(0, 0.25f);
    }
}
