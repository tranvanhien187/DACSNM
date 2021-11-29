package com.example.dacsnm;

import android.util.Log;

import com.example.dacsnm.observe.DataStation;
import com.example.dacsnm.observe.Observer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player implements Runnable {
    public static final int KEY_START = 100;
    public static final int KEY_MOVE = 101;
    public static final int KEY_QUIT = 110;
    public static final int KEY_RED_WIN = 103;
    public static final int KEY_BLACK_WIN = 104;
    public static final int KEY_SEND_REQUEST_PLAY_AGAIN = 106;
    public static final int KEY_ACCEPT_REQUEST_PLAY_AGAIN = 107;
    public static final int KEY_DECLINE_REQUEST_PLAY_AGAIN = 108;
    public static final int KEY__PLAY_AGAIN = 109;
    private DataOutputStream dos;
    private DataInputStream dis;
    private int turn ;
    private boolean isWaiting =true;
    private DataStation dataStation = DataStation.newInstance();
    private String namePlayer;

    private static Player player;


    public static Player getInstance(String ip,String name){
        if(player==null){
            player = new Player(ip,name);
        }
        return player;
    }

    private Player(String ip,String name) {
        this.namePlayer = name;
        new Thread(() -> {
            try {
                Socket soc = new Socket(ip, 8888);
                dos = new DataOutputStream(soc.getOutputStream());
                dis = new DataInputStream(soc.getInputStream());
                dos.writeUTF(namePlayer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            isWaiting=false;
        }).start();
    }
    public void clear(){
        player = null;
    }
    @Override
    public void run() {
        try {
            while(true) {
                if(!isWaiting){
                    int action = Integer.parseInt(dis.readUTF());
                    isWaiting = true;
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
                        case KEY_SEND_REQUEST_PLAY_AGAIN:{
                            handleActionSendRequestPlayAgain();
                            break;
                        }
                        case KEY_ACCEPT_REQUEST_PLAY_AGAIN:{
                            handleActionAcceptPlayAgain();
                            break;
                        }
                        case KEY_DECLINE_REQUEST_PLAY_AGAIN:{
                            handleActionDeclinePlayAgain();
                            break;
                        }
                        case KEY__PLAY_AGAIN:{
                            handleActionPlayAgain();
                            break;
                        }
                        case KEY_QUIT:{
                            handleActionQuit();
                            break;
                        }
                    }
                    isWaiting = false;
                    if(dis==null || dos == null) break;
                }

            }
        }catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void handleActionQuit() {
        dataStation.onQuit();
        clear();
    }

    private void handleActionStart() throws IOException {
        String player1 = dis.readUTF();
        String player2 = dis.readUTF();
        int turn = Integer.parseInt(dis.readUTF());
        this.turn = turn;
        dataStation.onGameStart(turn,player1,player2);
    }

    private void handleActionMove() throws IOException {
        int indexFrom = Integer.parseInt(dis.readUTF());
        int indexTo = Integer.parseInt(dis.readUTF());
        dataStation.onMove(indexFrom,indexTo);
    }

    private void handleActionSendRequestPlayAgain() throws IOException {
        dataStation.onSendActionPlayAgain(turn);
    }
    private void handleActionAcceptPlayAgain() throws IOException {
        dataStation.onAcceptActionPlayAgain();
    }
    private void handleActionPlayAgain() throws IOException {
        dataStation.onPlayAgain();
    }
    private void handleActionDeclinePlayAgain() throws IOException {
        dataStation.onDeclineActionPlayAgain();
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


    public void registerObserver(Observer observer) {
        dataStation.registerObserver(observer);
    }
    public void unregisterObserver(Observer observer) {
        dataStation.unregisterObserver(observer);
    }


    public void pushActionSendRequestPlayAgain() {
        new Thread(() -> {
            try {
                dos.writeUTF(KEY_SEND_REQUEST_PLAY_AGAIN+"");
                dos.writeUTF(turn+"");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void pushAcceptPlayAgain() {
        new Thread(() -> {
            try {
                dos.writeUTF(KEY_ACCEPT_REQUEST_PLAY_AGAIN+"");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void pushDeclinePlayAgain() {
        new Thread(() -> {
            try {
                dos.writeUTF(KEY_DECLINE_REQUEST_PLAY_AGAIN+"");
                clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void pushActionQuit() {
        new Thread(() -> {
            try {
                dos.writeUTF(KEY_QUIT+"");
                clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
