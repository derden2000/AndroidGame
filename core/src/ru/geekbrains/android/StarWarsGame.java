package ru.geekbrains.android;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StarWarsGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    TextureRegion region;

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Texture("StarWars.jpg");
        region = new TextureRegion(img, 262,0, 752, 1358); //немного картинки не хватило до 768x1366
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(region, 0, 0, 1080, 2340);//здесь разрешение моего телефона
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
    }
}
