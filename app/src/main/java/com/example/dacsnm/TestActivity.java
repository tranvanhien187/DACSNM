package com.example.dacsnm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TestActivity extends AppCompatActivity {
    private static final int PORT=8888;
    private EditText edtIp;
    private Button btn;
    private Socket socket;
    private DataInputStream din;
    private DataOutputStream dout;
    private boolean isWaiting=true;
    private Player player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        edtIp=findViewById(R.id.edt_ip);
        btn=findViewById(R.id.button);
        btn.setOnClickListener(view->{
            player = new Player("");
            new Thread(player).start();
        });
//        btn.setOnClickListener(v -> {
//            String ip=edtIp.getText().toString();
//            if(!TextUtils.isEmpty(ip)){
//                try {
//                    connectToServer(ip);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        new Thread(() -> {
//            while(true){
//                if(!isWaiting){
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        dout.writeUTF("hello from client");
//                        String msg=din.readUTF();
//                        Log.e("AAA", "server ---> "+msg );
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        break;
//                    }
//                }
//            }
//        }).start();
    }

    private void connectToServer(String ip) throws IOException {
        new Thread(() -> {
            try {
                socket=new Socket(ip,PORT);
                din=new DataInputStream(socket.getInputStream());
                dout=new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            isWaiting=false;
        }).start();
    }
}