package ru.geekbrains.android.base;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.utils.Regions;

public abstract class AnimatedSprite extends Sprite {

    Animation<TextureRegion> animatedObject;
//    Texture dataSheet;
    SpriteBatch spriteBatch;
    TextureRegion[] framesArray;    // нужно в конструкторе
    float stateTime; //отслеживание времени анимации
    TextureAtlas atlasName3;     // нужно в конструкторе
//    String nameOfSpriteSheet;
    Vector2 pos;
    float animationSpeed;


//    public AnimatedSprite(TextureRegion region, int rows, int cols, int frames, Vector2 position, SpriteBatch batch) {
//        super(region, rows, cols, frames);
//        this.spriteBatch = batch;
//        this.animatedObject = new Animation<TextureRegion>(0.033f, regions);
//        stateTime=0f;
//        this.pos = position;
//    }
//
//    @Override
//    public void draw(SpriteBatch batch) {
//        stateTime += Gdx.graphics.getDeltaTime();
//        TextureRegion currentFrame = animatedObject.getKeyFrame(stateTime, true);
//        batch.draw(currentFrame, pos.x , pos.y);
//    }
//
//    @Override
//    public void update(Float delta) {
//        super.update(delta);
//        frame++;
//    }

    public AnimatedSprite(TextureAtlas atlasName, String nameOfSpriteSheet, int rows, int cols, int frames, Vector2 position, float animationSpeed) {
        this.animationSpeed = animationSpeed;
//        System.out.println(String.format("%s.findRegion(%s)", atlasNameString, nameOfSpriteSheet));
//        this.dataSheet = texture;
        framesArray = Regions.split(atlasName.findRegion(nameOfSpriteSheet), rows, cols, frames);
//        this.regions = framesArray;
        this.animatedObject = new Animation<TextureRegion>(animationSpeed/*0.12f*/, framesArray); //передать в конструктор
        this.spriteBatch = new SpriteBatch();
        stateTime=0f;
        this.pos = position;
//        String.format("%s.findRegion(\"%s\"), %d, %d, %d", atlasName, nameOfSpriteSheet, rows,cols,frames);
//        atlasName3.findRegion("tiger"), rows, cols, frames
    }

    public AnimatedSprite(TextureRegion textureRegion, int rows, int cols, int frames, Vector2 position, float animationSpeed) {
        this.animationSpeed = animationSpeed;
        framesArray = Regions.split(textureRegion, rows, cols, frames);
//        this.regions = framesArray;
        this.animatedObject = new Animation<TextureRegion>(animationSpeed/*0.12f*/, framesArray); //передать в конструктор
        this.spriteBatch = new SpriteBatch();
        stateTime=0f;
        this.pos = position;

    }

    //    @Override
//    public void create() {
//        dataSheet = new Texture(String.format("%s.findRegion(%s))", atlasName, nameOfSpriteSheet)); // нужно в конструкторе
//        animatedObject = new Animation<TextureRegion>(0.033f, framesArray); //передать в конструктор
//        spriteBatch = new SpriteBatch();
//        stateTime=0f;
//    }

//    @Override
    public void resize(Rect worldBounds) {
        pos.set(worldBounds.getTop(), worldBounds.getRight());
//        for (TextureRegion frame : framesArray) {
//            frame.setRegionWidth(100);
//            frame.setRegionHeight(100);
//        }
//        setHeightProportion(0.15f);
    }

//    @Override
    public void update(Float delta) {
        stateTime += delta;
        TextureRegion currentFrame = animatedObject.getKeyFrame(stateTime, true);
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, pos.x, pos.y); // Draw current frame at (0, 0)
        spriteBatch.end();
    }

//    @Override
    public void drawPic(SpriteBatch batch) {
//        this.spriteBatch = batch;
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animatedObject.getKeyFrame(stateTime, true);
//        batch.begin();
        batch.draw(currentFrame, pos.x , pos.y); // Draw current frame at (0, 0)
//        batch.end();
    }

    //    @Override
    public void render() {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animatedObject.getKeyFrame(stateTime, true);
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, pos.x, pos.y); // Draw current frame at (0, 0)
        spriteBatch.end();
    }

//    @Override
    public void pause() {
    }

//    @Override
    public void resume() {

    }

//    @Override
//    public void dispose() {
////        spriteBatch.dispose();
////        dataSheet.dispose();
//    }
}
