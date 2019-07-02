package ru.geekbrains.android.animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.base.Animator;

public class TigerNew extends Animator {
    public TigerNew(int cols, int rows, int frames, String nameOfSpriteSheet, Vector2 pos, SpriteBatch spriteBatch) {
        super(cols, rows, frames, nameOfSpriteSheet, pos, spriteBatch);
    }
}
