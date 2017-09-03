package com.example.chirag.red;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

public class StonerHard extends AppCompatActivity {

    private ArrayList<String> colorNames = new ArrayList<>();
    private int colors[] = new int[4];
    private int colordraw[] = {R.drawable.blue_button_background, R.drawable.red_button_background, R.drawable.green_colour_background, R.drawable.yellow_button_background};
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
        setContentView(R.layout.activity_stoner_hard);
        ButterKnife.bind(this);

        player = MediaPlayer.create(this, R.raw.ding);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(StonerHard.this);
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

        cd.start(speed);
        cd.setOnEndAnimationFinish(new OnTimeFinish() {
            @Override
            public void onFinish() {
                cd.stop();
                Intent intent = new Intent(StonerHard.this, PlayAgain.class);
                intent.putExtra("Score", sc);
                intent.putExtra("Mode", "stoner");
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

    public void setTag(int draw, View v) {
        if (getResources().getDrawable(draw).getConstantState().equals
                (getResources().getDrawable(R.drawable.blue_button_background).getConstantState())) {
            v.setTag("blue");
            Log.e("The tag for", getResources().getDrawable(draw) + " is " + v.getTag());
        } else if (getResources().getDrawable(draw).getConstantState().equals
                (getResources().getDrawable(R.drawable.red_button_background).getConstantState())) {
            v.setTag("red");
            Log.e("The tag for", getResources().getDrawable(draw) + " is " + v.getTag());
        } else if (getResources().getDrawable(draw).getConstantState().equals
                (getResources().getDrawable(R.drawable.green_colour_background).getConstantState())) {
            v.setTag("green");
            Log.e("The tag for", getResources().getDrawable(draw) + " is " + v.getTag());
        } else if (getResources().getDrawable(draw).getConstantState().equals
                (getResources().getDrawable(R.drawable.yellow_button_background).getConstantState())) {
            v.setTag("yellow");
            Log.e("The tag for", getResources().getDrawable(draw) + " is " + v.getTag());
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
        if (view.getTag().equals("red"))
            color = getResources().getColor(R.color.RED);
        else if (view.getTag().equals("green"))
            color = getResources().getColor(R.color.GREEN);
        else if (view.getTag().equals("yellow"))
            color = getResources().getColor(R.color.YELLOW);
        else
            color = getResources().getColor(R.color.BLUE);
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
            speed = speed - 10;
            al.clear();
            change();
        } else {
            cd.stop();
            Intent intent = new Intent(StonerHard.this, PlayAgain.class);
            intent.putExtra("Score", sc);
            intent.putExtra("Mode", "stoner");
            startActivity(intent);
            finish();
        }
    }
}