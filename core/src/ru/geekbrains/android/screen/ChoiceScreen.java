package ru.geekbrains.android.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ru.geekbrains.android.base.BaseScreen;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.results.Records;
import ru.geekbrains.android.sprite.Background;
import ru.geekbrains.android.sprite.ButtonFinish;
import ru.geekbrains.android.sprite.ButtonMainMenu;
import ru.geekbrains.android.sprite.ButtonResults;
import ru.geekbrains.android.utils.Font;
import ru.geekbrains.android.utils.NameInputDialog;
import ru.geekbrains.android.utils.NetworkFileSaver;

public class ChoiceScreen extends BaseScreen {

    private static final String FILENAMEOFRECORDS = "data/records.dat";
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
    private FileHandle bufFile;
    private NameInputDialog dialog;

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

    public String getFileNameOfRecords() { return FILENAMEOFRECORDS; }

    public int getPoints() {
        return points;
    }

    public FileHandle getFileOfRecords() {
        return fileOfRecords;
    }

    public void setShowPermited(boolean showPermited) {
        this.showPermited = showPermited;
    }

    public void syncLocalRecords() {
        bufFile.writeString(fileOfRecords.readString(), false);
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
        bufFile = Gdx.files.internal(FILENAMEOFRECORDS);
        if (Gdx.files.isLocalStorageAvailable()) {
            String locRoot = Gdx.files.getLocalStoragePath();
            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                fileOfRecords = Gdx.files.local(locRoot + FILENAMEOFRECORDS);
            } else {
                fileOfRecords = Gdx.files.local(FILENAMEOFRECORDS);
            }
//            fileOfRecords.writeString(bufFile.readString(), false);
        }
//        Следующий код работает только на моем домашнем ftp-сервере в локальной сети
//        Расскомментировать при выкладке файла рекордов на сервер с белым IP-адресом.
        //Для FTPS
//        try {
//            NetworkFileSaver.getFTPData(fileOfRecords);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //Для обычного FTP
//        try {
//            fileOfRecords.writeString(NetworkFileSaver.getData(FILENAMEOFRECORDS), false);
//            System.out.println("!!!!!!!!!NetworkFileSaver.getData(FILENAMEOFRECORDS)" + NetworkFileSaver.getData(FILENAMEOFRECORDS));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        builder = new GsonBuilder();
        gson = builder.create();
        String text = fileOfRecords.readString();
        System.out.println("Прочитано из файла :" + text);
        records = gson.fromJson(text, Records.class);
        System.out.println("рекорды(records)" + records.getAllResults());
        checkRecordsTable(points);
        if (checkRecordsTable(points)) {
            dialog = new NameInputDialog(this);
            Gdx.input.getTextInput(dialog, "Вы попали в список рекордов", null, "Напишите Ваше Имя");
        }
        font = new Font("font/1f.fnt", "font/1f.png");
        font.setSize(0.025f);
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
            TreeMap<Integer, String> fin = new TreeMap<Integer, String>(Collections.reverseOrder());
            fin.putAll(records.getAllResults());
            resultsString.setLength(0);
            for (Map.Entry<Integer, String> entry : fin.entrySet()) {
                resultsString.append(entry.getValue()).append(" - ").append(entry.getKey()).append("\n");
            }
            font.draw(batch, resultsString.toString(), -0.25f, 0);
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


