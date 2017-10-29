package com.tacyllems.game.red;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import java.util.ArrayList;
import java.util.Random;

public class EasyActivity extends AppCompatActivity {

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
    SharedPreferences sharedPref;
    @BindView(R.id.slowitemtext)
    TextView slowitemtext;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    @BindView(R.id.freeze)
    ImageButton freeze;
    @BindView(R.id.rel)
    RelativeLayout rel;
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
    private Boolean gameOver = false;
    private CountdownView mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_easy);
        ButterKnife.bind(this);
        progressBar2.setVisibility(View.GONE);
        freeze.setVisibility(View.VISIBLE);
        mCountDownTimer = findViewById(R.id.mCountDownTimer);
        progressBar2.getProgressDrawable()
                .setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary),
                        PorterDuff.Mode.SRC_IN);
        mCountDownTimer.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                if (audio) player.stop();
                mCountDownTimer.stop();
                Intent intent = new Intent();
                intent.putExtra("Score", String.valueOf(sc));
                intent.putExtra("Mode", "easy");
                setResult(6969, intent);
                finish();
            }
        });
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
        speed = 2000;
        change();
    }

    void change() {
        valuetext = rand.nextInt(4);
        valuecolor = rand.nextInt(4);
        colorText.setText(colorNames.get(valuetext));
        colorText.setTextColor(colors[valuecolor]);
        if (!frozen) {
            mCountDownTimer.start(speed);

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
            mCountDownTimer.stop();
            gameOver = true;
            if (audio) player.stop();
            Intent intent = new Intent();
            intent.putExtra("Score", String.valueOf(sc));
            intent.putExtra("Mode", "easy");
            setResult(6969, intent);
            finish();
        }
    }

    @OnClick(R.id.freeze)
    public void onSlowClicked() {
        slowvalue = sharedPref.getInt("slowitem", 0);
        if (slowvalue > 0) {

            progressBar2.setVisibility(View.VISIBLE);
            freeze.setVisibility(View.GONE);
            slowitemtext.setVisibility(View.GONE);

            slowvalue -= 1;
            SharedPreferences.Editor editorz = sharedPref.edit();
            editorz.putInt("slowitem", slowvalue);
            editorz.apply();

            ObjectAnimator animation = ObjectAnimator.ofInt(progressBar2, "progress", 100, 0);
            animation.setDuration(7000);
            mCountDownTimer.pause();
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
                    slowitemtext.setVisibility(View.VISIBLE);
                    slowitemtext.setText(String.valueOf(slowvalue));
                    if (!gameOver) {
                        mCountDownTimer.start(speed);
                        frozen = false;
                    }
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            rel.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
