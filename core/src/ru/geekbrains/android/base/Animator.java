package ru.geekbrains.android.base;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Animator implements ApplicationListener {

    private static final int FRAME_COLS = 6, FRAME_ROWS = 5;

    int cols, rows, frames;
//    TextureAtlas atlasName;
    String nameOfSpriteSheet;
    Vector2 pos;

    Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
    Texture walkSheet;
    SpriteBatch spriteBatch;// конструктор

    float stateTime;

    public Animator(int cols, int rows, int frames, String nameOfSpriteSheet, Vector2 pos, SpriteBatch spriteBatch) {
        this.cols = cols;
        this.rows = rows;
        this.frames = frames;
        this.nameOfSpriteSheet = nameOfSpriteSheet;
        this.pos = pos;
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void create() {

        // Load the sprite sheet as a Texture
        walkSheet = new Texture(nameOfSpriteSheet);

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, cols, rows
                /*walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS*/);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[frames/*FRAME_COLS * FRAME_ROWS*/];
        int index = 0;
        for (int i = 0; i < rows/*FRAME_ROWS*/; i++) {
            for (int j = 0; j < cols/*FRAME_COLS*/; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        walkAnimation = new Animation<TextureRegion>(0.025f, walkFrames);

        // Instantiate a SpriteBatch for drawing and reset the elapsed animation
        // time to 0
//        spriteBatch = new SpriteBatch();
        stateTime = 0f;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, pos.x, pos.y); // Draw current frame at (50, 50)
        spriteBatch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() { // SpriteBatches and Textures must always be disposed
        spriteBatch.dispose();
        walkSheet.dispose();
    }
}
