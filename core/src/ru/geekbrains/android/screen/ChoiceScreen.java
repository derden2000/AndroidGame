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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import ru.geekbrains.android.base.BaseScreen;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.results.Records;
import ru.geekbrains.android.sprite.Background;
import ru.geekbrains.android.sprite.ButtonFinish;
import ru.geekbrains.android.sprite.ButtonMainMenu;
import ru.geekbrains.android.sprite.ButtonResults;
import ru.geekbrains.android.sprite.ResultsWindow;
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

    private boolean showResultsPermited;

    private ResultsWindow resultsWindow;

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

    public void setShowResultsPermited(boolean showResultsPermited) {
        this.showResultsPermited = showResultsPermited;
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
        resultsWindow = new ResultsWindow(atlas, records);
    }

    public boolean checkRecordsTable(int points) {
        if (points > records.getMinPoint()) {
            return true;
        } else {
            return false;
        }
    }

//    public void showRecordsTable() {
//        if (showResultsPermited) {
//            resultsWindow.draw(batch);
//        }
//    }

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
        if (showResultsPermited) {
            resultsWindow.draw(batch);
        }
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        buttonMainMenu.resize(worldBounds);
        buttonFinish.resize(worldBounds);
        buttonResults.resize(worldBounds);
        resultsWindow.resize(worldBounds);
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
        showResultsPermited = false;
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
            showResultsPermited = false;
        }
        if (keycode == Input.Keys.ESCAPE) {
            showResultsPermited = false;
        }
        return false;
    }
}


