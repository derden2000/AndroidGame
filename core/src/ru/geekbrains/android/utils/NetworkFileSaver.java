package ru.geekbrains.android.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class NetworkFileSaver {

    private static final String FTPADDRESS = "ftp://192.168.1.1/"; //Адрес локальной сети. Поменять на белый IP

    public static String getData(String fileName) throws IOException {
        URL yahoo = new URL(FTPADDRESS + fileName);
        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine, res = null;

        while ((inputLine = in.readLine()) != null)
            res = inputLine;
        in.close();
        return res;
    }

    public static void sendData(String fileName) throws IOException {
        File out = new File(fileName);
        URL ur = new URL(FTPADDRESS + fileName);
        URLConnection urlc = ur.openConnection();

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(out));
        BufferedOutputStream z = new BufferedOutputStream(urlc.getOutputStream());
        int by;
        while ((by = in.read()) != -1) {
            z.write(by);
        }
        in.close();
        z.close();
    }
}
