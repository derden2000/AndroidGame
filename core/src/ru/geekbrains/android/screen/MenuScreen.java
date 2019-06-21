package ru.geekbrains.android.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.base.BaseScreen;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.sprite.Background;
import ru.geekbrains.android.sprite.SpaceShip;

public class MenuScreen extends BaseScreen {
    private Texture img;
    private Texture bground;
    private Vector2 touch;
    private Vector2 position;
    private Vector2 speed;
    private Background background;
    private SpaceShip ship;

    @Override
    public void show() {
        super.show();
        img = new Texture("spaceship2.gif");
        bground = new Texture("space.jpg");
        background = new Background(new TextureRegion(bground));
        ship = new SpaceShip(new TextureRegion(img));
        touch = new Vector2();
        position = new Vector2();
        speed = new Vector2();

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        ship.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        ship.draw(batch);
        moveShip();
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        bground.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println(keycode);
        if (keycode==19) {
            speed.set(0, 0.05f);
            moveShip();
        }
        if (keycode==20) {
            speed.set(0, -0.05f);
            moveShip();
        }
        if (keycode==21) {
            speed.set(-0.05f, 0);
            moveShip();
        }
        if (keycode==22) {
            speed.set(0.05f, 0);
            moveShip();
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode==19) {
            speed.set(0, 0);
        }
        if (keycode==20) {
            speed.set(0, 0);
        }
        if (keycode==21) {
            speed.set(0, 0);
        }
        if (keycode==22) {
            speed.set(0, 0);
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX,screenY,pointer,button);
        touch=setTouch();
        speed=setSpeed(ship.pos);
        return false;
    }

    private void moveShip() {
        if (!ship.isOutside(background)) {
            if (ship.isMe(touch)) {
                speed.set(0, 0);
            } else {
                ship.pos.add(speed);
            }
        } else {
            speed.set(0, 0);
            if (ship.pos.x >= 0) {
                ship.pos.x = background.getRight() - ship.getHalfWidth();
            }
            if (ship.pos.x <= 0) {
                ship.pos.x = background.getLeft() + ship.getHalfWidth();
            }
            if (ship.pos.y >= 0) {
                ship.pos.y = background.getTop() - ship.getHalfHeight();
            }
            if (ship.pos.y <= 0) {
                ship.pos.y = background.getBottom() + ship.getHalfHeight();
            }
        }
    }
}
