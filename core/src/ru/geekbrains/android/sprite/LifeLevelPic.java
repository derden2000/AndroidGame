package ru.geekbrains.android.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.screen.ChoiceScreen;

public class LifeLevelPic extends Sprite {

    private Rect worldBounds;
    int strength;
    private Game game;

    public LifeLevelPic(TextureAtlas atlas, Game game, int strength) {
        super(atlas.findRegion("Level"),8, 1,8);
        this.game = game;
        this.strength = strength;
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
            case 69: case 70: case 71:
                frame=1;
                break;
            case 59: case 60: case 61:
                frame=2;
                break;
            case 49: case 50: case 51:
                frame=3;
                break;
            case 39: case 40: case 41:
                frame=4;
                break;
            case 29: case 30: case 31:
                frame=5;
                break;
            case 19: case 20: case 21:
                frame=6;
                break;
            case 9: case 10: case 11:
                frame=7;
                break;
            case 0: case -1: case -2:
                game.setScreen(new ChoiceScreen(game));
        }
    }

//    @Override
//    public void dispose() {
//
//    }
}
