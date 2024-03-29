package ru.geekbrains.android;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.android.screen.MainScreen;
import ru.geekbrains.android.screen.MenuScreen;

public class StarWarsGame extends Game {


    @Override
    public void create () {
        setScreen(new MenuScreen(this));
    }
}
