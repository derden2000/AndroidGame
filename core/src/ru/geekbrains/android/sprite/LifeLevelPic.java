package ru.geekbrains.android.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.screen.ChoiceScreen;

public class LifeLevelPic extends Sprite {

    private Rect worldBounds;
    int strength = 80;
    private Game game;

    public LifeLevelPic(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("Level"),8, 1,8);
        this.game = game;
        setHeightProportion(0.08f);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setRight(worldBounds.getRight() - 0.01f);
        setBottom(worldBounds.getBottom() + 0.01f);
    }

    @Override
    public void update(Float delta) {
        switch (this.strength) {
            case 80:
                frame=0;
                break;
            case 70:
                frame=1;
                break;
            case 60:
                frame=2;
                break;
            case 50:
                frame=3;
                break;
            case 40:
                frame=4;
                break;
            case 30:
                frame=5;
                break;
            case 20:
                frame=6;
                break;
            case 10:
                frame=7;
                break;
            case 0:
                game.setScreen(new ChoiceScreen(game));
        }
    }
}
