package ru.geekbrains.android.utils;

import com.badlogic.gdx.files.FileHandle;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class NetworkFileSaver {

    private static final String FTPADDRESS = "ftp://antonshoo.keenetic.pro/"; //Адрес локальной сети. Поменять на белый IP

    private  static FTPClient client = new FTPClient();
    private  static int port = 21;
    private static String user = "App";
    private static String pass = "12345";

    public static String getFTPData(FileHandle fileName) throws IOException {
        File file = new File(fileName.path());
//        System.out.println("INfileName.path(): " + fileName.path());
        client.connect("antonshoo.keenetic.pro", port);
        client.enterLocalPassiveMode();
        client.login("anonymous", "*********************");
        String[] replies = client.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
//        client.changeWorkingDirectory("/data");

//        BufferedReader in = new BufferedReader(new InputStream();
//        FileOutputStream fileOutputStream = new FileOutputStream("");
        client.retrieveFile(file.getName()/*"records.dat"*/, new FileOutputStream(file.getName()+"2"/*"data/read.txt"*/));
        FTPFile[] answer = client.listFiles();
        for (FTPFile ftpFile : answer) {
            System.out.println("Dir: " + ftpFile.getName());
        }
        System.out.println("file.getPath(): " + file.getPath());
        FileInputStream inputStream = new FileInputStream(file.getPath());
        client.storeFile("sample.txt", inputStream);
        System.out.println("client.storeFile(sample.txt, inputStream);)");
        return "Connect Status: " + client.isConnected();
    }


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
