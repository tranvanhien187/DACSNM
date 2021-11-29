package com.example.dacsnm.observe;

import java.util.ArrayList;
import java.util.List;

public class DataStation implements Observable{
    private static List<Observer> listObserver ;
    private static DataStation dataStation = null;

    private DataStation(){
        listObserver = new ArrayList<>();
    }

    public static DataStation newInstance(){
        if(dataStation==null){
            dataStation = new DataStation();
        }
        return dataStation;
    }

    public static List<Observer> getListObserver() {
        return listObserver;
    }

    public static void setListObserver(List<Observer> listObserver) {
        DataStation.listObserver = listObserver;
    }

    @Override
    public void registerObserver(Observer observer) {
        listObserver.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        listObserver.remove(observer);
    }

    @Override
    public void onGameStart(int turn,String player1,String player2) {
        for (int i =0;i<listObserver.size();i++){
            listObserver.get(i).onGamePlay(turn,player1,player2);
        }
    }

    @Override
    public void onMove(int indexFrom, int indexTo) {
        for (int i =0;i<listObserver.size();i++){
            listObserver.get(i).onMove(indexFrom,indexTo);
        }
    }

    @Override
    public void onGameWinOrLose(boolean isWin) {
        for (int i =0;i<listObserver.size();i++){
            listObserver.get(i).onGameWinOrLose(isWin);
        }
    }

    @Override
    public void onSendActionPlayAgain(int turn) {
        for (int i =0;i<listObserver.size();i++){
            listObserver.get(i).onSendActionPlayAgain(turn);
        }
    }

    @Override
    public void onAcceptActionPlayAgain() {
        for (int i =0;i<listObserver.size();i++){
            listObserver.get(i).onAcceptActionPlayAgain();
        }
    }

    @Override
    public void onDeclineActionPlayAgain() {
        for (int i =0;i<listObserver.size();i++){
            listObserver.get(i).onDeclineActionPlayAgain();
        }
    }

    @Override
    public void onPlayAgain() {
        for (int i =0;i<listObserver.size();i++){
            listObserver.get(i).onPlayAgain();
        }
    }

    @Override
    public void onQuit() {
        for (int i =0;i<listObserver.size();i++){
            listObserver.get(i).onQuit();
        }
    }

}
