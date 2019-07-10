package ru.geekbrains.android.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.Rect;


public class Bullet extends Sprite {

    private Rect worldBounds;
    private Vector2 bulletSpeed = new Vector2();
    private int damage;
    private Object owner;

    public Bullet() {
        regions = new TextureRegion[1];
    }

    public void set(
            Object owner,
            TextureRegion region,
            Vector2 pos0,
            Vector2 v0,
            float height,
            Rect worldBounds,
            int damage
    ) {
        this.owner = owner;
        this.regions[0] = region;
        this.pos.set(pos0);
        this.bulletSpeed.set(v0);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
        System.out.println("bulletDamage: " + damage);
    }

    @Override
    public void update(Float delta) {
        pos.mulAdd(bulletSpeed, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }

//    @Override
//    public void dispose() {
//
//    }

    public int getDamage() {
        return damage;
    }

    public Object getOwner() {
        return owner;
    }
}
