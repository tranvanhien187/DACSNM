package com.example.dacsnm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;

public class SplashActivity extends AppCompatActivity {
    private static final String ipDell = "192.168.1.3";

    private Player player ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        findViewById(R.id.btn_pvp).setOnClickListener(view->{
            startActivity(new Intent(this,MainActivity.class));
        });

        findViewById(R.id.btn_pvf).setOnClickListener(view->{
            startActivity(new Intent(this,TwoPlayersActivity.class));
        });
    }

    @SuppressLint("InflateParams")
    private void showDialogPvP(){
        LayoutInflater inflater = getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nhập nickname")
                .setView(inflater.inflate(R.layout.custom_dialog_pvp, null));
        AlertDialog dialog = builder.create();
        dialog.show();
        EditText edt = dialog.findViewById(R.id.edt_player);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        btnOk.setOnClickListener(view -> {
            player = Player.getInstance(ipDell,edt.getText().toString());
            dialog.dismiss();
        });
        btnCancel.setOnClickListener(view-> dialog.dismiss()
        );
    }
}