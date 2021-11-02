package com.example.dacsnm.observe;

public interface Observer {
    void onGamePlay(int turn);
    void onGameOver(boolean isWin);
    void onMove(int indexFrom,int indexTo);

}

