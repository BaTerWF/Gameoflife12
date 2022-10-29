package com.example.gameoflife2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private  GameOfLifeView gameOfLifeView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameOfLifeView = (GameOfLifeView) findViewById(R.id.gameOfLife);
    }

    @Override
    protected void onResume(){
        super.onResume();
        gameOfLifeView.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        gameOfLifeView.stop();
    }
}