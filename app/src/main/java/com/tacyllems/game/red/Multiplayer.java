package com.tacyllems.game.red;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

public class Multiplayer extends AppCompatActivity {

    @BindView(R.id.rlred)
    Button rlred;
    @BindView(R.id.rlyell)
    Button rlyell;
    @BindView(R.id.rlblu)
    Button rlblu;
    @BindView(R.id.rlgree)
    Button rlgree;
    @BindView(R.id.colorText)
    TextView colorText;
    @BindView(R.id.mCountDownTimer)
    CountdownView mCountDownTimer;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.rlred1)
    Button rlred1;
    @BindView(R.id.rlyell1)
    Button rlyell1;
    @BindView(R.id.rlblu1)
    Button rlblu1;
    @BindView(R.id.rlgree1)
    Button rlgree1;
    @BindView(R.id.colorText2)
    TextView colorText2;
    @BindView(R.id.score2)
    TextView score2;
    private int sc;
    private int sc2;
    private long speed;
    private MediaPlayer player;
    private Boolean audio;
    SharedPreferences sharedPref;
    private ArrayList<String> colorNames = new ArrayList<>();
    private int colors[] = new int[4];
    private int valuecolor;
    private int valuecolor1;
    private int valuetext;
    private Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_multiplayer);
        ButterKnife.bind(this);
        mCountDownTimer.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                if (audio) player.stop();
                mCountDownTimer.stop();
                Intent intent = new Intent(Multiplayer.this, MultiplayerEnd.class);
                intent.putExtra("Score1", sc);
                intent.putExtra("Score2", sc2);

                intent.putExtra("Mode", "multiplayer");
                startActivity(intent);
            }
        });

        player = MediaPlayer.create(this, R.raw.ding);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(Multiplayer.this);
        audio = sharedPref.getBoolean("audio", true);

        colorNames.add("RED");
        colorNames.add("BLUE");
        colorNames.add("GREEN");
        colorNames.add("YELLOW");
        colors[0] = Color.RED;
        colors[1] = Color.BLUE;
        colors[2] = Color.GREEN;
        colors[3] = Color.YELLOW;
        speed = 60000;
        mCountDownTimer.start(speed);
        change();
        change2();
    }

    void change() {
        valuetext = rand.nextInt(4);
        valuecolor = rand.nextInt(4);
        colorText.setText(colorNames.get(valuetext));
        colorText.setTextColor(colors[valuecolor]);
    }

    void change2() {
        valuetext = rand.nextInt(4);
        valuecolor1 = rand.nextInt(4);
        colorText2.setText(colorNames.get(valuetext));
        colorText2.setTextColor(colors[valuecolor1]);
    }

    @OnClick({R.id.rlred, R.id.rlyell, R.id.rlblu, R.id.rlgree, R.id.rlred1, R.id.rlyell1, R.id.rlblu1, R.id.rlgree1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlred:
                checkcolor(((ColorDrawable) rlred.getBackground()).getColor());
                break;
            case R.id.rlyell:
                checkcolor(((ColorDrawable) rlyell.getBackground()).getColor());
                break;
            case R.id.rlblu:
                checkcolor(((ColorDrawable) rlblu.getBackground()).getColor());
                break;
            case R.id.rlgree:
                checkcolor(((ColorDrawable) rlgree.getBackground()).getColor());
                break;
            case R.id.rlred1:
                checkcolor1(((ColorDrawable) rlred1.getBackground()).getColor());
                break;
            case R.id.rlyell1:
                checkcolor1(((ColorDrawable) rlyell1.getBackground()).getColor());
                break;
            case R.id.rlblu1:
                checkcolor1(((ColorDrawable) rlblu1.getBackground()).getColor());
                break;
            case R.id.rlgree1:
                checkcolor1(((ColorDrawable) rlgree1.getBackground()).getColor());
                break;
        }
    }

    void checkcolor(int color) {
        if (color == colors[valuecolor]) {
            sc = Integer.parseInt(score.getText().toString());
            sc++;
            score.setText(String.valueOf(sc));
            change();
        } else {
            sc = Integer.parseInt(score.getText().toString());
            sc--;
            score.setText(String.valueOf(sc));
            change();
        }
    }

    void checkcolor1(int color) {
        if (color == colors[valuecolor1]) {
            sc2 = Integer.parseInt(score2.getText().toString());
            sc2++;
            score2.setText(String.valueOf(sc2));
            change2();
        } else {
            sc2 = Integer.parseInt(score2.getText().toString());
            sc2--;
            score2.setText(String.valueOf(sc2));
            change2();
        }
    }


}
