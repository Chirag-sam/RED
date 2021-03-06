package com.tacyllems.game.red;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import java.util.ArrayList;
import java.util.Random;

public class StonerHard extends AppCompatActivity {

    SharedPreferences sharedPref;
    private ArrayList<String> colorNames = new ArrayList<>();
    private int colors[] = new int[4];
    private int colordraw[] = {R.drawable.blue_button_background, R.drawable.red_button_background, R.drawable.green_colour_background, R.drawable.yellow_button_background};
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
    private Boolean audio;
    private int valuecolor;
    private CountdownView mCountDownTimer;
    private int redback[] = {R.color.BLUE, R.color.GREEN, R.color.YELLOW, R.color.dark_green, R.color.orange, R.color.pink, R.color.gray, R.color.maroon, R.color.yellowish_orange, R.color.purple, R.color.colorBackgroundDark, R.color.white};
    private int blueback[] = {R.color.RED, R.color.GREEN, R.color.YELLOW, R.color.dark_green, R.color.orange, R.color.pink, R.color.gray, R.color.maroon, R.color.yellowish_orange, R.color.purple, R.color.colorBackgroundDark, R.color.white};
    private int greenback[] = {R.color.BLUE, R.color.RED, R.color.YELLOW, R.color.dark_green, R.color.orange, R.color.pink, R.color.gray, R.color.maroon, R.color.yellowish_orange, R.color.purple, R.color.colorBackgroundDark, R.color.white};
    private int yellowback[] = {R.color.BLUE, R.color.GREEN, R.color.RED, R.color.dark_green, R.color.orange, R.color.pink, R.color.gray, R.color.maroon, R.color.yellowish_orange, R.color.purple, R.color.colorBackgroundDark, R.color.white};

    private ArrayList<Integer> al = new ArrayList<>();

    public static int OpposeColor(int ColorToInvert) {
        int RGBMAX = 255;
        float[] hsv = new float[3];
        float H;

        Color.RGBToHSV(Color.red(ColorToInvert), RGBMAX - Color.green(ColorToInvert),
            Color.blue(ColorToInvert), hsv);

        H = (float) (hsv[0] + 0.5);

        if (H > 1) H -= 1;
        return Color.HSVToColor(hsv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stoner_hard);
        ButterKnife.bind(this);

        player = MediaPlayer.create(this, R.raw.ding);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(StonerHard.this);
        audio = sharedPref.getBoolean("audio", true);

        colorText = findViewById(R.id.colorText);
        mCountDownTimer = findViewById(R.id.mCountDownTimer);

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

        int ran1 = generateRandom(4, null);
        al.add(ran1);
        int ran2 = generateRandom(4, al);
        al.add(ran2);
        int ran3 = generateRandom(4, al);
        al.add(ran3);
        int ran4 = generateRandom(4, al);


        butt1.setBackgroundResource(colordraw[ran1]);
        setTag(colordraw[ran1], butt1);
        butt2.setBackgroundResource(colordraw[ran2]);
        setTag(colordraw[ran2], butt2);
        butt3.setBackgroundResource(colordraw[ran3]);
        setTag(colordraw[ran3], butt3);
        butt4.setBackgroundResource(colordraw[ran4]);
        setTag(colordraw[ran4], butt4);

        mCountDownTimer.start(speed);
        mCountDownTimer.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                mCountDownTimer.stop();
                Intent intent = new Intent();
                intent.putExtra("Score", String.valueOf(sc));
                intent.putExtra("Mode", "stoner");
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

    public void setTag(int draw, View v) {
        if (getResources().getDrawable(draw).getConstantState().equals
                (getResources().getDrawable(R.drawable.blue_button_background).getConstantState())) {
            v.setTag("blue");
        } else if (getResources().getDrawable(draw).getConstantState().equals
                (getResources().getDrawable(R.drawable.red_button_background).getConstantState())) {
            v.setTag("red");
        } else if (getResources().getDrawable(draw).getConstantState().equals
                (getResources().getDrawable(R.drawable.green_colour_background).getConstantState())) {
            v.setTag("green");
        } else if (getResources().getDrawable(draw).getConstantState().equals
                (getResources().getDrawable(R.drawable.yellow_button_background).getConstantState())) {
            v.setTag("yellow");
        }

    }

    @OnClick({R.id.butt1, R.id.butt2, R.id.butt3, R.id.butt4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.butt1:
                checkcolor(butt1);
                break;
            case R.id.butt2:
                checkcolor(butt2);
                break;
            case R.id.butt3:
                checkcolor(butt3);
                break;
            case R.id.butt4:
                checkcolor(butt4);
                break;
        }
    }

    public void checkcolor(View view) {
        int color;
        if (view.getTag().equals("red")) {
            color = getResources().getColor(R.color.RED);
        } else if (view.getTag().equals("green")) {
            color = getResources().getColor(R.color.GREEN);
        } else if (view.getTag().equals("yellow")) {
            color = getResources().getColor(R.color.YELLOW);
        } else {
            color = getResources().getColor(R.color.BLUE);
        }

        int contastcolor = OpposeColor(color);
        if (color == colors[valuecolor]) {
            if (audio) {
                if (player.isPlaying() == true) {
                    player.stop();
                    player.release();
                    player = MediaPlayer.create(StonerHard.this, R.raw.ding);
                }
                player.start();
            }
            rel.setBackgroundColor(contastcolor);
            sc = Integer.parseInt(score.getText().toString());
            sc++;
            score.setText(String.valueOf(sc));
            if (speed > 380)
                speed = speed - 10;
            al.clear();
            change();
        } else {
            mCountDownTimer.stop();
            Intent intent = new Intent();
            intent.putExtra("Score", String.valueOf(sc));
            intent.putExtra("Mode", "stoner");
            setResult(6969, intent);
            finish();
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