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
        super(atlas.findRegion("LifeLevel"),10, 1,10);
        this.game = game;
        this.strength = strength;
    }

    public void decreaseStrength(int points) {
        strength -= points;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setWidthProportion(worldBounds.getWidth());
        setBottom(worldBounds.getBottom());
    }

    @Override
    public void update(Float delta) {
        switch (strength) {
            case 100:
                frame=0;
                break;
            case 89: case 90: case 91:
                frame=1;
                break;
            case 79: case 80: case 81:
                frame=2;
                break;
            case 69: case 70: case 71:
                frame=3;
                break;
            case 59: case 60: case 61:
                frame=4;
                break;
            case 49: case 50: case 51:
                frame=5;
                break;
            case 39: case 40: case 41:
                frame=6;
                break;
            case 29: case 30: case 31:
                frame=7;
                break;
            case 19: case 20: case 21:
                frame=8;
                break;
            case 9: case 10: case 11:
                frame=9;
                break;
//            case 6: case 7: case 8:
//                frame=9;
//                break;
//            case 0: case -1: case -2:
//                game.setScreen(new ChoiceScreen(game));
        }
    }
}
