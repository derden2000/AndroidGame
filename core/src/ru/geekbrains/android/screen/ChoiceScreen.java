package ru.geekbrains.android.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.base.BaseScreen;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.sprite.Background;
import ru.geekbrains.android.sprite.ButtonFinish;
import ru.geekbrains.android.sprite.ButtonMainMenu;

public class ChoiceScreen extends BaseScreen {

    private Texture bground;
    private Background background;

    private Game game;

    private TextureAtlas atlas;
    private ButtonMainMenu buttonMainMenu;
    private ButtonFinish buttonFinish;

    public ChoiceScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bground = new Texture("space.jpg");
        background = new Background(new TextureRegion(bground));
        atlas = new TextureAtlas("textures/game_btn.pack");
        buttonMainMenu = new ButtonMainMenu(atlas,game);
        buttonFinish = new ButtonFinish(atlas);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        draw();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        buttonMainMenu.draw(batch);
        buttonFinish.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        buttonMainMenu.resize(worldBounds);
        buttonFinish.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bground.dispose();
        atlas.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        buttonMainMenu.touchDown(touch,pointer);
        buttonFinish.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        buttonMainMenu.touchUp(touch, pointer);
        buttonFinish.touchUp(touch, pointer);
        return false;
    }
}


