package ru.geekbrains.android.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.results.Records;
import ru.geekbrains.android.utils.Font;

public class ResultsWindow extends Sprite {

    private StringBuilder resultsString;
    private Font font;

    public ResultsWindow(TextureAtlas atlas, Records records) {
        super(atlas.findRegion("resWindow"));
        font = new Font("font/1f.fnt", "font/1f.png");
        resultsString = new StringBuilder();
        TreeMap<Integer, String> fin = new TreeMap<Integer, String>(Collections.reverseOrder());
        fin.putAll(records.getAllResults());
        resultsString.setLength(0);
        for (Map.Entry<Integer, String> entry : fin.entrySet()) {
            resultsString.append(entry.getValue()).append(" - ").append(entry.getKey()).append("\n");
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        setWidthProportion(0.5f);
        setBottom(-halfHeight);
        font.setSize(this.getHeight() * 0.04f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        font.draw(batch, resultsString.toString(), -halfWidth*0.5f, halfHeight*0.5f);
    }
}
