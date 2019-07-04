package ru.geekbrains.android.animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.base.AnimatedSprite;
import ru.geekbrains.android.math.Rect;

public class Tiger extends AnimatedSprite {


    public Tiger(TextureAtlas atlasName, String nameOfSpriteSheet, int rows, int cols, int frames, Vector2 position, float animationSpeed) {
        super(atlasName, nameOfSpriteSheet, rows, cols, frames, position, animationSpeed);
    }

    public Tiger(TextureRegion textureRegion, int rows, int cols, int frames, Vector2 position, float animationSpeed) {
        super(textureRegion, rows, cols, frames, position, animationSpeed);
    }

//    @Override
//    public void dispose() {
//
//    }
}
