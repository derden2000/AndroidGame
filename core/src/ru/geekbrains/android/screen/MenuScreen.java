package ru.geekbrains.android.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture img;
    private Texture background;
    private TextureRegion region;
    private Vector2 touch;
    private Vector2 position;
    private Vector2 speed;

    @Override
    public void show() {
        super.show();
        img = new Texture("spaceship2.gif");
        background = new Texture("StarWars.jpg");
        region = new TextureRegion(background, 262,0, 752, 1358);
        touch = new Vector2();
        position = new Vector2();
        speed = new Vector2();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(region, 0, 0, 1080, 2340);//здесь разрешение моего телефона
        batch.draw(img, position.x, position.y);
        if ((Gdx.graphics.getWidth() -  (position.x + img.getWidth())) > 0
                && (Gdx.graphics.getWidth() - position.x) <= (Gdx.graphics.getWidth())
                && ((Gdx.graphics.getHeight() - position.y) > img.getHeight())
                && (Gdx.graphics.getHeight() - position.y) <= (Gdx.graphics.getHeight()))
        {
            if (Math.abs(touch.x - position.x) < 0.1 && Math.abs(touch.y - position.y) < 0.1) {
                speed.set(0, 0);
            } else {
                position.add(speed);
            }
        }
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        background.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println(keycode);
        if (keycode==19) {
            speed.set(0, 5.0f);
            position.add(speed);
        }
        if (keycode==20) {
            speed.set(0, -5.0f);
            position.add(speed);
        }
        if (keycode==21) {
            speed.set(-5.0f, 0);
            position.add(speed);
        }
        if (keycode==22) {
            speed.set(5.0f, 0);
            position.add(speed);
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
        System.out.println("mouse.x - " + screenX + " mouse.y - " + (Gdx.graphics.getHeight() - screenY));
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        speed.set((screenX-position.x)/100, ((Gdx.graphics.getHeight() - screenY) - position.y)/100);
        return false;
    }
}
