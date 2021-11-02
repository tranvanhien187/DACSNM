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

    @Override
    public void registerObserver(Observer observer) {
        listObserver.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        listObserver.add(observer);
    }

    @Override
    public void onGamePlay(int turn) {
        for (int i =0;i<listObserver.size();i++){
            listObserver.get(i).onGamePlay(turn);
        }
    }

    @Override
    public void onMove(int indexFrom, int indexTo) {
        for (int i =0;i<listObserver.size();i++){
            listObserver.get(i).onMove(indexFrom,indexTo);
        }
    }

    @Override
    public void onGameOver(boolean isWin) {
        for (int i =0;i<listObserver.size();i++){
            listObserver.get(i).onGameOver(isWin);
        }
    }

}
