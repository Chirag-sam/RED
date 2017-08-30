package com.example.chirag.red;

import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.anastr.flattimelib.CountDownTimerView;
import com.github.anastr.flattimelib.intf.OnTimeFinish;

import java.util.ArrayList;
import java.util.Random;

public class EasyActivity extends AppCompatActivity {

    private ArrayList<String> colorNames = new ArrayList<>();
    private int colors[] = new int[4];
    private CountDownTimerView cd;
    private TextView colorText;
    private Button green;
    private Button blue;
    private Button red;
    private Button yellow;
    private TextView score;
    private int sc;
    private long speed;
    private int valuecolor;
private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);
rl=(RelativeLayout)findViewById(R.id.rel);
        cd = (CountDownTimerView) findViewById(R.id.mCountDownTimer);
        colorText = (TextView) findViewById(R.id.colorText);
        green = (Button) findViewById(R.id.rlgree);
        blue = (Button) findViewById(R.id.rlblu);
        red = (Button) findViewById(R.id.rlred);
        yellow = (Button) findViewById(R.id.rlyell);
        score = (TextView) findViewById(R.id.score);

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
        final int valuecolor = rand.nextInt(4);
        colorText.setText(colorNames.get(valuetext));
        colorText.setTextColor(colors[valuecolor]);
//        Color textColor = Color.rgb(255,
//                255-Color.green(colors[valuecolor]),
//                255-Color.blue(colors[valuecolor]));

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check(((ColorDrawable) red.getBackground()).getColor());
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check(((ColorDrawable) blue.getBackground()).getColor());
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check(((ColorDrawable) green.getBackground()).getColor());
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check(((ColorDrawable) yellow.getBackground()).getColor());
            }
        });


        cd.start(speed);
        cd.setOnEndAnimationFinish(new OnTimeFinish() {
            @Override
            public void onFinish() {
                cd.stop();
                Intent intent = new Intent(EasyActivity.this, PlayAgain.class);
                intent.putExtra("Score", sc);
                intent.putExtra("Mode", "easy");
                startActivity(intent);
            }
        });
    }

    void check(int color) {
        if (color == colors[valuecolor]) {
            sc = Integer.parseInt(score.getText().toString());
            sc++;
            score.setText(String.valueOf(sc));
            speed = speed - 10;
            change();
        } else {
            cd.stop();
            Intent intent = new Intent(EasyActivity.this, PlayAgain.class);
            intent.putExtra("Score", sc);
            intent.putExtra("Mode", "easy");
            startActivity(intent);
        }
    }
}
