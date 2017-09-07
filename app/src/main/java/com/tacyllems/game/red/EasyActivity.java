package com.tacyllems.game.red;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
    @BindView(R.id.slowitemtext)
    TextView slowitemtext;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    @BindView(R.id.freeze)
    ImageButton freeze;
    private ArrayList<String> colorNames = new ArrayList<>();
    private int colors[] = new int[4];
    private int sc;
    private long speed;
    private int valuecolor;
    private int valuetext;
    private int slowvalue;
    private Random rand = new Random();
    private MediaPlayer player;
    private Boolean audio;
    private Boolean frozen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);
        ButterKnife.bind(this);
        progressBar2.setVisibility(View.GONE);
        freeze.setVisibility(View.VISIBLE);
        progressBar2.getProgressDrawable()
                .setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary),
                        android.graphics.PorterDuff.Mode.SRC_IN);
        player = MediaPlayer.create(this, R.raw.ding);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(EasyActivity.this);
        audio = sharedPref.getBoolean("audio", true);
        slowvalue = sharedPref.getInt("slowitem", 0);
        slowitemtext.setText(String.valueOf(slowvalue));
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
        if (!frozen) {
            cd.start(speed);
            cd.setOnEndAnimationFinish(() -> {
                if (audio) player.stop();
                cd.stop();
                Intent intent = new Intent(EasyActivity.this, PlayAgain.class);
                intent.putExtra("Score", sc);
                intent.putExtra("Mode", "easy");
                startActivity(intent);
                finish();
            });
        }
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
            if (speed > 400) speed = speed - 8;
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

    @OnClick(R.id.freeze)
    public void onSlowClicked() {
        progressBar2.setVisibility(View.VISIBLE);
        freeze.setVisibility(View.GONE);
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar2, "progress", 100, 0);
        animation.setDuration(10000);
        cd.stop();
        frozen = true;

        animation.setInterpolator(new DecelerateInterpolator());
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //do something when the countdown is complete
                progressBar2.setVisibility(View.GONE);
                freeze.setVisibility(View.VISIBLE);
                cd.start(speed);
                frozen = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animation.start();
    }
}
