package ru.geekbrains.android.utils;

import com.badlogic.gdx.Input;

import ru.geekbrains.android.screen.ChoiceScreen;

public class NameInput implements Input.TextInputListener {

    private ChoiceScreen owner;

    public NameInput(ChoiceScreen owner) {
        this.owner = owner;
    }

    @Override
    public void input(String text) {
        owner.getRecords().addNewUser(text, owner.getPoints());
        System.out.println("owner.getRecords().addNewUser(text, owner.getPoints());");
        System.out.println("После добавления нового рекорда: " + owner.getRecords().getAllResults());
        String txt = owner.gson.toJson(owner.getRecords());
        owner.getFileOfRecords().writeString(txt, false);
    }

    @Override
    public void canceled() {

    }
}
