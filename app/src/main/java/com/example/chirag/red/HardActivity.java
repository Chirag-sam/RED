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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.anastr.flattimelib.CountDownTimerView;
import com.github.anastr.flattimelib.intf.OnTimeFinish;

import java.util.ArrayList;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HardActivity extends AppCompatActivity {

    private ArrayList<String> colorNames = new ArrayList<>();
    private int colors[] = new int[4];
    private CountDownTimerView cd;
    private TextView colorText;
    private Button butt4;
    private Button butt3;
    private Button butt1;
    private Button butt2;
    private TextView score;
    private int sc;
    private long speed;
    private RelativeLayout rel;
    private MediaPlayer player;
    SharedPreferences sharedPref;
    private Boolean audio;
    private int valuecolor;

    private ArrayList<Integer> al = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard);
        ButterKnife.bind(this);
        player = MediaPlayer.create(this, R.raw.ding);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(HardActivity.this);
        audio = sharedPref.getBoolean("audio", true);

        cd = findViewById(R.id.mCountDownTimer);
        colorText = findViewById(R.id.colorText);

        butt4 = findViewById(R.id.butt4);
        butt3 = findViewById(R.id.butt3);
        butt1 = findViewById(R.id.butt1);
        butt2 = findViewById(R.id.butt2);
        score = findViewById(R.id.score);
        rel = findViewById(R.id.rel);

        colorNames.add("RED");
        colorNames.add("BLUE");
        colorNames.add("GREEN");
        colorNames.add("YELLOW");
        colors[0] = Color.RED;
        colors[1] = Color.BLUE;
        colors[2] = Color.GREEN;
        colors[3] = Color.YELLOW;

        speed = 1000;
        change();
    }


    void change() {

        Random rand = new Random();
        int valuetext = rand.nextInt(4);
        valuecolor = rand.nextInt(4);
        colorText.setText(colorNames.get(valuetext));
        colorText.setTextColor(colors[valuecolor]);
        ChangeBackgroundColour(colors[valuecolor]);

        int ran1 = generateRandom(4, null);

        al.add(ran1);
        int ran2 = generateRandom(4, al);
        al.add(ran2);
        int ran3 = generateRandom(4, al);
        al.add(ran3);
        int ran4 = generateRandom(4, al);

        butt1.setBackgroundColor(colors[ran1]);
        butt2.setBackgroundColor(colors[ran2]);
        butt3.setBackgroundColor(colors[ran3]);
        butt4.setBackgroundColor(colors[ran4]);

        cd.start(speed);
        cd.setOnEndAnimationFinish(new OnTimeFinish() {
            @Override
            public void onFinish() {
                cd.stop();
                Intent intent = new Intent(HardActivity.this, PlayAgain.class);
                intent.putExtra("Score", sc);
                intent.putExtra("Mode", "hard");
                startActivity(intent);
                finish();
            }
        });
    }

    public int generateRandom(int end, ArrayList<Integer> excludeRows) {
        Random rand = new Random();
        int valuetext = rand.nextInt(end);

        if (excludeRows != null) {
            while (excludeRows.contains(valuetext)) {
                rand = new Random();
                valuetext = rand.nextInt(end);
            }
        }

        return valuetext;
    }

    public void ChangeBackgroundColour(int color) {
        if (color == getResources().getColor(R.color.BLUE) || color == getResources().getColor(R.color.RED))
            rel.setBackgroundColor(Color.parseColor("#ffffff"));
        else if (color == getResources().getColor(R.color.YELLOW) || color == getResources().getColor(R.color.GREEN))
            rel.setBackgroundColor(Color.parseColor("#000000"));
    }

    @OnClick({R.id.butt1, R.id.butt2, R.id.butt3, R.id.butt4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.butt1:
                checkcolor(((ColorDrawable) butt1.getBackground()).getColor());
                break;
            case R.id.butt2:
                checkcolor(((ColorDrawable) butt2.getBackground()).getColor());
                break;
            case R.id.butt3:
                checkcolor(((ColorDrawable) butt3.getBackground()).getColor());
                break;
            case R.id.butt4:
                checkcolor(((ColorDrawable) butt4.getBackground()).getColor());
                break;
        }
    }
    void checkcolor(int color)
    {
        if (color == colors[valuecolor]) {
            if (audio) {
                if (player.isPlaying() == true) {
                    player.stop();
                    player.release();
                    player = MediaPlayer.create(HardActivity.this, R.raw.ding);
                }
                player.start();
            }
            sc = Integer.parseInt(score.getText().toString());
            sc++;
            score.setText(String.valueOf(sc));
            speed = speed - 10;
            al.clear();
            change();
        } else {
            cd.stop();
            Intent intent = new Intent(HardActivity.this, PlayAgain.class);
            intent.putExtra("Score", sc);
            intent.putExtra("Mode", "hard");
            startActivity(intent);
            finish();
        }
    }
}
