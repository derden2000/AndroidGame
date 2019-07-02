package ru.geekbrains.android.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.android.base.ScaledTouchUpButton;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.screen.GameScreen;

public class ButtonPlay extends ScaledTouchUpButton {

    private Game game;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btnPlay"));
        this.game = game;
    }

    @Override
    protected void action() {
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.1f);
        setRight(worldBounds.getRight() - 0.005f);
        setBottom(worldBounds.getBottom() + 0.005f);
    }
}
