package com.example.dacsnm.observe;

public interface Observer {
    void onGamePlay(int turn);
    void onGameWinOrLose(boolean isRed);
    void onMove(int indexFrom,int indexTo);

}

