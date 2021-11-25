package com.example.dacsnm;

import android.util.Log;

import com.example.dacsnm.observe.DataStation;
import com.example.dacsnm.observe.Observable;
import com.example.dacsnm.observe.Observer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player implements Runnable {
    public static final int KEY_START = 100;
    public static final int KEY_MOVE = 101;
    public static final int KEY_RED_WIN = 103;
    public static final int KEY_BLACK_WIN = 104;
    private DataOutputStream dos;
    private DataInputStream dis;
    private int turn ;
    private  boolean isWaiting =true;
    private boolean isMyturn = false;
    private DataStation dataStation = DataStation.newInstance();
    private String name;

    private static Player player;


    public static Player getInstance(String ip,String name){
        if(player==null){
            player = new Player(ip,name);
        }
        return player;
    }

    private Player(String ip,String name) {
        this.name = name;
        new Thread(() -> {
            try {
                Socket soc = new Socket(ip, 8888);
                dos = new DataOutputStream(soc.getOutputStream());
                dis = new DataInputStream(soc.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            isWaiting=false;
        }).start();
    }
    @Override
    public void run() {
        try {
            while(true) {
                if(!isWaiting){
                    int action = Integer.parseInt(dis.readUTF());
                    switch (action){
                        case KEY_START:{
                            handleActionStart();
                            break;
                        }
                        case KEY_MOVE:{
                            handleActionMove();
                            break;
                        }
                        case KEY_RED_WIN:{
                            dataStation.onGameWinOrLose(true);
                            break;
                        }
                        case KEY_BLACK_WIN:{
                            dataStation.onGameWinOrLose(false);
                            break;
                        }
                    }
                }

            }
        }catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void handleActionStart() throws IOException {
        int turn = Integer.parseInt(dis.readUTF());
        this.turn = turn;
        Log.d("AAA","turn  "+turn);
        dataStation.onGamePlay(turn);
    }

    private void handleActionMove() throws IOException {
        int indexFrom = Integer.parseInt(dis.readUTF());
        int indexTo = Integer.parseInt(dis.readUTF());
        dataStation.onMove(indexFrom,indexTo);
    }

    public void pushMoveToServer(int from,int to){
        new Thread(() -> {
            try {
                dos.writeUTF(String.valueOf(Player.KEY_MOVE));
                dos.writeUTF(String.valueOf(from));
                dos.writeUTF(String.valueOf(to));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void pushActionWin(int action){
        new Thread(() -> {
            try {
                dos.writeUTF(String.valueOf(action));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void pushActionStart(String name){
        new Thread(() -> {
            try {
                dos.writeUTF(name);
                dos.writeUTF(String.valueOf(KEY_START));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }



    public void registerObserver(Observer observer) {
        dataStation.registerObserver(observer);
    }
    public void unregisterObserver(Observer observer) {
        dataStation.unregisterObserver(observer);
    }
}
