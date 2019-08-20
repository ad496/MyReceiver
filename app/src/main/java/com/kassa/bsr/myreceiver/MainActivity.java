package com.kassa.bsr.myreceiver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyReceiver.add(this, "MYFILTER", new IAction<String>() {
            @Override
            public void action(String s) {
                Toast.makeText(MainActivity.this,""+s,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyReceiver.deatach(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyReceiver.sendMessage(this,"MYFILTER","test");
    }
}
