package com.example.chirag.red;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.github.anastr.flattimelib.CountDownTimerView;

import java.util.ArrayList;
import java.util.Random;

public class EasyActivity extends AppCompatActivity {

    @BindView(R.id.colorText)
    TextView colorText;
    @BindView(R.id.mCountDownTimer)
    CountDownTimerView cd;
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
    private Random rand = new Random();
    private MediaPlayer player;
    private Boolean audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);
        ButterKnife.bind(this);
        player = MediaPlayer.create(this, R.raw.ding);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(EasyActivity.this);
        audio = sharedPref.getBoolean("audio", true);
        colorNames.add("RED");
        colorNames.add("BLUE");
        colorNames.add("GREEN");
        colorNames.add("YELLOW");
        colors[0] = Color.RED;
        colors[1] = Color.BLUE;
        colors[2] = Color.GREEN;
        colors[3] = Color.YELLOW;
        speed = 1500;
        change();
    }

    void change() {
        valuetext = rand.nextInt(4);
        valuecolor = rand.nextInt(4);
        colorText.setText(colorNames.get(valuetext));
        colorText.setTextColor(colors[valuecolor]);
        cd.start(speed);
        cd.setOnEndAnimationFinish(() -> {
            cd.stop();
            Intent intent = new Intent(EasyActivity.this, PlayAgain.class);
            intent.putExtra("Score", sc);
            intent.putExtra("Mode", "easy");
            startActivity(intent);
            finish();
        });
    }

    @OnClick({R.id.rlred, R.id.rlyell, R.id.rlblu, R.id.rlgree})
    public void onViewClicked(View view) {
        if (audio) {
            if (player.isPlaying() == true) {
                player.stop();
                player.release();
                player = MediaPlayer.create(this, R.raw.ding);
            }
            player.start();
        }
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
            sc++;
            score.setText(String.valueOf(sc));
            speed = speed - 10;
            change();
        } else {
            cd.stop();
            if (audio) player.stop();
            Intent intent = new Intent(EasyActivity.this, PlayAgain.class);
            intent.putExtra("Score", sc);
            intent.putExtra("Mode", "easy");
            startActivity(intent);
            finish();
        }
    }
}
