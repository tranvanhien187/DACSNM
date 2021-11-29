package com.example.dacsnm.observe;

public interface Observable{
    void registerObserver(Observer observer);
    void unregisterObserver(Observer observer);
    void onGameStart(int turn,String player1,String player2);
    void onMove(int indexFrom,int indexTo);
    void onGameWinOrLose(boolean isRed);
    void onSendActionPlayAgain(int turn);
    void onAcceptActionPlayAgain();
    void onDeclineActionPlayAgain();
    void onPlayAgain();
    void onQuit();
}
