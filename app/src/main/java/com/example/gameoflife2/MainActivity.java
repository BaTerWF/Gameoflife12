package com.example.gameoflife2;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private  GameOfLifeView gameOfLifeView;
    Button stopBut;
    Button startBut;
    Button resetBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameOfLifeView = (GameOfLifeView) findViewById(R.id.gameOfLife);
        stopBut = (Button) findViewById(R.id.stopB);
        startBut = (Button) findViewById(R.id.startB);
        resetBut = (Button)  findViewById(R.id.restBut);
    }

    public void clickPause(View view){
        gameOfLifeView.stop();
    }
    public void clickStart(View view){
        gameOfLifeView.start();
    }
    public void clickReset(View view){super.recreate();}

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