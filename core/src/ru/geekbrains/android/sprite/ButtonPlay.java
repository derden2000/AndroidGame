package ru.geekbrains.android.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.android.base.ScaledTouchUpButton;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.screen.GameScreen;

public class ButtonPlay extends ScaledTouchUpButton {

    private Game game;
    private GameScreen gameScreen;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btnPlay"));
        this.game = game;
        this.gameScreen = new GameScreen(game);
    }

    @Override
    protected void action() {
        game.setScreen(gameScreen);
        gameScreen.resetGameSettings();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.1f);
        setRight(worldBounds.getRight() - 0.005f);
        setBottom(worldBounds.getBottom() + 0.005f);
    }

    public void render(float delta, SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }

//    @Override
//    public void dispose() {
//
//    }
}
