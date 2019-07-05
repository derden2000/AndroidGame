package ru.geekbrains.android.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.android.base.SpritesPool;
import ru.geekbrains.android.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    private TextureRegion explosionRegion;
    private Sound sound;

    public ExplosionPool(TextureAtlas atlas, Sound sound) {
        explosionRegion = atlas.findRegion("explosion");
        this.sound = sound;
    }

    public Sound getSound() {
        return sound;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(explosionRegion, 5, 8, 38, sound);
    }
}
