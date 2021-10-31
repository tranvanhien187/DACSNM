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
    private DataOutputStream dos;
    private DataInputStream dis;
    private int turn ;
    private  boolean isWaiting =true;
    private boolean isMyturn = false;
    private DataStation dataStation = DataStation.newInstance();

    public Player() {
        new Thread(() -> {
            try {
                Socket soc = new Socket("192.168.1.9", 8888);
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
            while (true){
                if(!isWaiting){
                    int turn = Integer.parseInt(dis.readUTF());
                    this.turn = turn;
                    Log.d("AAA","turn    "+turn);
                    dataStation.onGamePlay(turn);
                    break;
                }
            }
            while(true) {
                int ix = Integer.parseInt(dis.readUTF());
                int iy = Integer.parseInt(dis.readUTF());
            }
        }catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void pushMoveToServer(int from,int to){
        try {
            dos.writeUTF(String.valueOf(from));
            dos.writeUTF(String.valueOf(to));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void registerObserver(Observer observer) {
        dataStation.registerObserver(observer);
    }
    public void unregisterObserver(Observer observer) {
        dataStation.unregisterObserver(observer);
    }
}
