package ru.geekbrains.android.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.android.base.ScaledTouchUpButton;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.screen.MenuScreen;

public class ButtonMainMenu extends ScaledTouchUpButton {
    private Game game;

    public ButtonMainMenu(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btnStart"));
        this.game = game;

    }

    @Override
    protected void action() {
        game.setScreen(new MenuScreen(this.game));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.1f);
        pos.set(0, -0.25f);
    }
}
