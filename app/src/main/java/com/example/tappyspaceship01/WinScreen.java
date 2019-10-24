package com.example.tappyspaceship01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WinScreen extends AppCompatActivity {
    Button restartgame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);
        restartgame = (Button)findViewById(R.id.restartgame);

    }

    public void restart(View view) {
            Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

