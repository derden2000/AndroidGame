package ru.geekbrains.android.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.Rect;

public class SpaceShip extends Sprite {

    public SpaceShip(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight()*0.1f);
        pos.set(0, -0.5f+halfHeight);
    }
}
