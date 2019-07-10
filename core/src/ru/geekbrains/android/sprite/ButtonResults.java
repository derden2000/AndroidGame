package ru.geekbrains.android.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.android.base.ScaledTouchUpButton;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.screen.ChoiceScreen;

public class ButtonResults extends ScaledTouchUpButton {

    private ChoiceScreen choiceScreen;

    public ButtonResults(TextureAtlas atlas, ChoiceScreen choiceScreen) {
        super(atlas.findRegion("btnRes"));
        this.choiceScreen = choiceScreen;
    }

    @Override
    protected void action() {
        choiceScreen.setShowPermited(true);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.1f);
        pos.set(0, 0);
    }
}
