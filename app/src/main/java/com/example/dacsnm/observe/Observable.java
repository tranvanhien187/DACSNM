package com.example.dacsnm.observe;

public interface Observable{
    void registerObserver(Observer observer);
    void unregisterObserver(Observer observer);
    void onGamePlay(int turn);
    void onMove(int indexFrom,int indexTo);
    void onGameWinOrLose(boolean isRed);
}
