package ru.geekbrains.android.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.animation.Tiger;
import ru.geekbrains.android.animation.TigerNew;
import ru.geekbrains.android.base.AnimatedSprite;
import ru.geekbrains.android.base.BaseScreen;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.sprite.Background;
import ru.geekbrains.android.sprite.ButtonExit;
import ru.geekbrains.android.sprite.ButtonPlay;

public class MenuScreen extends BaseScreen {
    private Texture bground;
    private Background background;

    private Game game;

    private TextureAtlas atlas;
    private TextureAtlas atlast;
    private ButtonExit btnExit;
    private ButtonPlay btnPlay;

    private AnimatedSprite animation;
    private TigerNew video;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bground = new Texture("space.jpg");
        background = new Background(new TextureRegion(bground));
        atlas = new TextureAtlas("textures/game_btn.pack");
//        atlast = new TextureAtlas("textures/tiger.pack");
        btnExit = new ButtonExit(atlas);
        btnPlay = new ButtonPlay(atlas, game);
        animation = new Tiger(new TextureRegion(new Texture("tiger.png")), 3, 3, 9,
                new Vector2(150,250), 0.5f);
//        animation = new Tiger(atlast, "tiger", 3,3,9, new Vector2(0,0), batch);
//        video = new TigerNew(3,3,9, "tiger.png", new Vector2(0,0), batch);
//        video.create();

    }

    @Override
    public void resize(Rect worldBounds) {
//        super.resize(worldBounds);
        background.resize(worldBounds);
        btnPlay.resize(worldBounds);
        btnExit.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
//        super.render(delta);
        draw();
        animation.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        bground.dispose();
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
        background.draw(batch);
        btnExit.draw(batch);
        btnPlay.draw(batch);
        batch.end();
    }
}
