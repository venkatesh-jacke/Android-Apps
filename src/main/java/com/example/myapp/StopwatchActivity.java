package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity {
    private int seconds=0;
    private boolean isRunning=false;
    private boolean wasRunning=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if(savedInstanceState!=null){
            seconds=savedInstanceState.getInt("seconds");
            isRunning=savedInstanceState.getBoolean("isRunning");
            wasRunning=savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    public void onClickStart(View view) {
        isRunning=true;
    }

    public void onClickStop(View view) {
        isRunning=false;
    }

    public void onClickReset(View view) {
        isRunning=false;
        seconds=0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.d("sw","onStop");
        wasRunning=isRunning;  //Record the state of the stopwatch
        isRunning=false;  //

    }

    @Override
    protected void onResume() {
        super.onResume();
        L.d("sw", "onStart Called");
        if (wasRunning) {
            isRunning = true;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        L.d("sw","onSaveInstanceState Called");
        outState.putInt("seconds", seconds);
        outState.putBoolean("isRunning", isRunning);
        outState.putBoolean("wasRunning", wasRunning);
    }

    public void runTimer(){
        final TextView timeView=(TextView) findViewById(R.id.tvTimer);
        final Handler handler=new Handler();
        handler.post(new Runnable(){
            @Override
            public void run() {
                int hours=seconds/3600;
                int minutes=(seconds%3600)/60;
                int secs=seconds%60;
                String time=String.format("%d:%02d:%02d",hours,minutes,secs);
                timeView.setText(time);
                if(isRunning) seconds++;
                handler.postDelayed(this,1000);
            }
        });
    }
}