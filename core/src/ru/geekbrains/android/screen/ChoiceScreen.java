package ru.geekbrains.android.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ru.geekbrains.android.base.BaseScreen;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.results.Records;
import ru.geekbrains.android.sprite.Background;
import ru.geekbrains.android.sprite.ButtonFinish;
import ru.geekbrains.android.sprite.ButtonMainMenu;
import ru.geekbrains.android.sprite.ButtonResults;
import ru.geekbrains.android.utils.Font;
import ru.geekbrains.android.utils.NameInput;

public class ChoiceScreen extends BaseScreen {

    private final int points;
    private Texture bground;
    private Background background;

    private Game game;

    private TextureAtlas atlas;
    private ButtonMainMenu buttonMainMenu;
    private ButtonFinish buttonFinish;
    private ButtonResults buttonResults;

    private GsonBuilder builder;
    public Gson gson;
    private Records records;

    private FileHandle fileOfRecords;
    private NameInput dialog;

    private StringBuilder resultsString;
    private Font font;
    private boolean showPermited;

    public ChoiceScreen(Game game, int points) {
        this.points = points;
        this.game = game;
    }

    public Records getRecords() {
        return records;
    }

    public int getPoints() {
        return points;
    }

    public FileHandle getFileOfRecords() {
        return fileOfRecords;
    }

    public void setShowPermited(boolean showPermited) {
        this.showPermited = showPermited;
    }

    @Override
    public void show() {
        super.show();
        bground = new Texture("space.jpg");
        background = new Background(new TextureRegion(bground));
        atlas = new TextureAtlas("textures/game_btn.pack");
        buttonMainMenu = new ButtonMainMenu(atlas,game);
        buttonFinish = new ButtonFinish(atlas);
        buttonResults = new ButtonResults(atlas, this);
        fileOfRecords = Gdx.files.local("data/records.dat");
        builder = new GsonBuilder();
        gson = builder.create();
        String text = fileOfRecords.readString();
        System.out.println("Прочитано из файла :" + text);
        records = gson.fromJson(text, Records.class);
        System.out.println("рекорды(records)" + records.getAllResults());
        checkRecordsTable(points);
        if (checkRecordsTable(points)) {
            dialog = new NameInput(this);
            Gdx.input.getTextInput(dialog, "Вы попали в список рекордов", null, "Напишите Ваше Имя");
        }
        font = new Font("font/sample2.fnt", "font/sample2.png");
        font.setSize(0.05f);
        resultsString = new StringBuilder();
    }

    public boolean checkRecordsTable(int points) {
        if (points > records.getMinPoint()) {
            return true;
        } else {
            return false;
        }
    }

    public void showRecordsTable() {
        if (showPermited) {
            resultsString.setLength(0);
            for (String str : records.getAllStringResults()) {
                resultsString.append(str).append("\n");
            }
            font.draw(batch, resultsString.toString(), 0, 0);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        draw();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        buttonMainMenu.draw(batch);
        buttonFinish.draw(batch);
        buttonResults.draw(batch);
        showRecordsTable();
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        buttonMainMenu.resize(worldBounds);
        buttonFinish.resize(worldBounds);
        buttonResults.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bground.dispose();
        atlas.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        buttonMainMenu.touchDown(touch,pointer);
        buttonFinish.touchDown(touch, pointer);
        buttonResults.touchDown(touch, pointer);
        showPermited = false;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        buttonMainMenu.touchUp(touch, pointer);
        buttonFinish.touchUp(touch, pointer);
        buttonResults.touchUp(touch, pointer);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK) {
            showPermited = false;
        }
        if (keycode == Input.Keys.ESCAPE) {
            showPermited = false;
        }
        return false;
    }
}


