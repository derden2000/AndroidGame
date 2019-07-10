package ru.geekbrains.android.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import ru.geekbrains.android.animation.Tiger;
import ru.geekbrains.android.animation.TigerNew;
import ru.geekbrains.android.base.AnimatedSprite;
import ru.geekbrains.android.base.BaseScreen;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.sprite.Background;
import ru.geekbrains.android.sprite.ButtonExit;
import ru.geekbrains.android.sprite.ButtonPlay;
import ru.geekbrains.android.sprite.Star;

public class MainScreen extends BaseScreen {

    private Game game;

    private TextureAtlas atlas;
    private ButtonExit btnExit;
    private ButtonPlay btnPlay;

    private ArrayList<Star> stars = new ArrayList<Star>();

    private AnimatedSprite animation;

    public MainScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/game_btn.pack");
        btnExit = new ButtonExit(atlas);
        btnPlay = new ButtonPlay(atlas, game);
//        for (int i = 0; i < 25; i++) {
//            stars.add(new Star(atlas));
//        }
        animation = new Tiger(new TextureRegion(new Texture("anim2.png")), 25, 5, 125,
                new Vector2(0,0), 0.04f);


    }

    @Override
    public void resize(Rect worldBounds) {
        btnPlay.resize(worldBounds);
        btnExit.resize(worldBounds);
//        for (Star star : stars) {
//            star.resize(worldBounds);
//        }
    }

    @Override
    public void render(float delta) {
//        for (Star star : stars) {
//            star.update(delta);
//        }
        animation.render();
        draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        atlas.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        btnExit.touchDown(touch, pointer);
        btnPlay.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        btnExit.touchUp(touch, pointer);
        btnPlay.touchUp(touch, pointer);
        return false;
    }

    public void draw() {
        batch.begin();
        btnPlay.draw(batch);
        btnExit.draw(batch);
        btnPlay.draw(batch);
//        for (Star star : stars) {
//            star.draw(batch);
//        }
        batch.end();
    }
}
