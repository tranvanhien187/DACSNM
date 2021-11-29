package com.example.dacsnm.observe;

public interface Observer {
    void onGamePlay(int turn,String player1,String player2);
    void onGameWinOrLose(boolean isRed);
    void onMove(int indexFrom,int indexTo);
    void onSendActionPlayAgain(int turn);
    void onAcceptActionPlayAgain();
    void onDeclineActionPlayAgain();
    void onPlayAgain();
    void onQuit();

}

