package com.example.chirag.red;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Reflex30 extends AppCompatActivity {

    private TextView timer;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    @BindView(R.id.colorText)
    TextView colorText;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.rlred)
    Button red;
    @BindView(R.id.rlyell)
    Button yellow;
    @BindView(R.id.rlblu)
    Button blue;
    @BindView(R.id.rlgree)
    Button green;
    @BindView(R.id.rel)
    RelativeLayout rel;
    SharedPreferences sharedPref;
    private ArrayList<String> colorNames = new ArrayList<>();
    private int colors[] = new int[4];
    private int sc;
    private long speed;
    private int valuecolor;
    private int valuetext;
    private MediaPlayer player;
    private Boolean audio;
    private Random rand = new Random();
    private int init;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflex30);
        ButterKnife.bind(this);
        init = 0;
        timer = (TextView) findViewById(R.id.timer);
        player = MediaPlayer.create(this, R.raw.ding);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(Reflex30.this);
        audio = sharedPref.getBoolean("audio", true);
        colorNames.add("RED");
        colorNames.add("BLUE");
        colorNames.add("GREEN");
        colorNames.add("YELLOW");
        colors[0] = Color.RED;
        colors[1] = Color.BLUE;
        colors[2] = Color.GREEN;
        colors[3] = Color.YELLOW;
        score.setText("30");
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timer.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };

    @OnClick({R.id.rlred, R.id.rlyell, R.id.rlblu, R.id.rlgree})
    public void onViewClicked(View view) {
        if(init==0){
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);init++;}
        switch (view.getId()) {
            case R.id.rlred:
                checkcolor(((ColorDrawable) red.getBackground()).getColor());
                break;
            case R.id.rlyell:
                checkcolor(((ColorDrawable) yellow.getBackground()).getColor());
                break;
            case R.id.rlblu:
                checkcolor(((ColorDrawable) blue.getBackground()).getColor());
                break;
            case R.id.rlgree:
                checkcolor(((ColorDrawable) green.getBackground()).getColor());
                break;
        }
    }
    void checkcolor(int color) {
        if (color == colors[valuecolor]) {
            sc = Integer.parseInt(score.getText().toString());
            sc--;
            score.setText(String.valueOf(sc));
            if(speed>400)
                speed = speed - 8;
            change();
        } else {
            timeSwapBuff += timeInMilliseconds;
            customHandler.removeCallbacks(updateTimerThread);
            if (audio) player.stop();
            Intent intent = new Intent(Reflex30.this, PlayAgain.class);
            intent.putExtra("Time", timer.getText().toString());
            intent.putExtra("Mode", "reflex");
            startActivity(intent);
            finish();
        }
    }
    void change() {
        valuetext = rand.nextInt(4);
        valuecolor = rand.nextInt(4);
        colorText.setText(colorNames.get(valuetext));
        colorText.setTextColor(colors[valuecolor]);
        if(Integer.parseInt(score.getText().toString())==0) {
            timeSwapBuff += timeInMilliseconds;
            customHandler.removeCallbacks(updateTimerThread);
            Intent intent = new Intent(Reflex30.this, PlayAgain.class);
            intent.putExtra("Time", timer.getText().toString());
            intent.putExtra("Mode", "reflex");
            startActivity(intent);
            finish();
        }

    }
}
