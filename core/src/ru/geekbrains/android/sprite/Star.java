package ru.geekbrains.android.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.math.Rnd;

public class Star extends Sprite {

    private Vector2 speed = new Vector2();
    private Rect worldBounds;


    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        speed.set(Rnd.nextFloat(-0.01f, 0.01f), Rnd.nextFloat(-0.3f, -0.1f));
        setHeightProportion(0.01f);

    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
    }

    @Override
    public void update(Float delta) {
        super.update(delta);
        pos.mulAdd(speed, delta);
        if (getRight() < worldBounds.getLeft()) {setLeft(worldBounds.getRight());}
        if (getLeft() > worldBounds.getRight()) {setRight(worldBounds.getLeft());}
        if (getTop() < worldBounds.getBottom()) {setBottom(worldBounds.getTop());}
        if (getBottom() > worldBounds.getTop()) {setTop(worldBounds.getBottom());}
    }
}
