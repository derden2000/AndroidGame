package ru.geekbrains.android.utils;

import com.badlogic.gdx.Input;

import java.io.IOException;

import ru.geekbrains.android.screen.ChoiceScreen;

public class NameInputDialog implements Input.TextInputListener {

    private ChoiceScreen owner;

    public NameInputDialog(ChoiceScreen owner) {
        this.owner = owner;
    }

    @Override
    public void input(String text) {
        owner.getRecords().addNewUser(text, owner.getPoints());
        System.out.println("owner.getRecords().addNewUser(text, owner.getPoints());");
        System.out.println("После добавления нового рекорда: " + owner.getRecords().getAllResults());
        String txt = owner.gson.toJson(owner.getRecords());
        owner.getFileOfRecords().writeString(txt, false);
//
//        Следующий код работает только на моем домашнем ftp-сервере в локальной сети
//        Расскомментировать при выкладке файла рекордов на сервер с белым IP-адресом.
//
//        try {
//            NetworkFileSaver.sendData(owner.getFileNameOfRecords());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void canceled() {

    }
}
