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

public class HardActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    @BindView(R.id.slowitemtext)
    TextView slowitemtext;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    @BindView(R.id.freeze)
    ImageButton freeze;
    private ArrayList<String> colorNames = new ArrayList<>();
    private int colors[] = new int[4];
    private TextView colorText;
    private Button butt4;
    private Button butt3;
    private Button butt1;
    private Button butt2;
    private TextView score;
    private int slowvalue;
    private int sc;
    private long speed;
    private RelativeLayout rel;
    private MediaPlayer player;
    private Boolean audio;
    private int valuecolor;
    private Boolean frozen = false;
    private Boolean gameOver = false;
    private CountdownView mCountDownTimer;

    private ArrayList<Integer> al = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hard);
        ButterKnife.bind(this);
        progressBar2.setVisibility(View.GONE);
        freeze.setVisibility(View.VISIBLE);
        mCountDownTimer = findViewById(R.id.mCountDownTimer);
        progressBar2.getProgressDrawable()
                .setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary),
                        PorterDuff.Mode.SRC_IN);
        player = MediaPlayer.create(this, R.raw.ding);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(HardActivity.this);
        audio = sharedPref.getBoolean("audio", true);
        slowvalue = sharedPref.getInt("slowitem", 0);
        slowitemtext.setText(String.valueOf(slowvalue));
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

        speed = 1200;
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

        if (!frozen) {
            mCountDownTimer.start(speed);

        }
        mCountDownTimer.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                mCountDownTimer.stop();
                Intent intent = new Intent();
                intent.putExtra("Score", String.valueOf(sc));
                intent.putExtra("Mode", "hard");
                setResult(6969, intent);
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
        if (color == getResources().getColor(R.color.BLUE) || color == getResources().getColor(R.color.RED)) {
            rel.setBackgroundColor(Color.parseColor("#ffffff"));
            score.setTextColor(Color.parseColor("#000000"));
            freeze.setImageResource(R.drawable.ic_infinite_time_black);
        } else if (color == getResources().getColor(R.color.YELLOW) || color == getResources().getColor(R.color.GREEN)) {
            rel.setBackgroundColor(Color.parseColor("#000000"));
            score.setTextColor(Color.parseColor("#ffffff"));
            freeze.setImageResource(R.drawable.ic_infinite_time);
        }
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

    void checkcolor(int color) {
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
            if (speed > 380)
                speed = speed - 10;
            al.clear();
            change();
        } else {
            mCountDownTimer.stop();
            gameOver = true;
            Intent intent = new Intent();
            intent.putExtra("Score", String.valueOf(sc));
            intent.putExtra("Mode", "hard");
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

            ObjectAnimator animation = ObjectAnimator.ofInt(progressBar2, "progress", 100, 0);
            animation.setDuration(5000);
            mCountDownTimer.pause();
            frozen = true;
            slowvalue -= 1;
            SharedPreferences.Editor editorz = sharedPref.edit();
            editorz.putInt("slowitem", slowvalue);
            editorz.apply();

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
