package com.example.chirag.red;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.anastr.flattimelib.CountDownTimerView;
import com.github.anastr.flattimelib.colors.Colors;
import com.github.anastr.flattimelib.intf.OnTimeFinish;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> colorNames=new ArrayList<>();
    private int colors[]=new int[4];
    private CountDownTimerView cd;
    private TextView colorText;
    private RelativeLayout green;
    private RelativeLayout blue;
    private RelativeLayout red;
    private RelativeLayout yellow;
    private TextView score;
    private int sc;
    private long speed;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cd = (CountDownTimerView) findViewById(R.id.mCountDownTimer);
        colorText = (TextView) findViewById(R.id.colorText);
        green = (RelativeLayout)findViewById(R.id.rlgree);
        blue = (RelativeLayout)findViewById(R.id.rlblu);
        red = (RelativeLayout)findViewById(R.id.rlred);
        yellow = (RelativeLayout)findViewById(R.id.rlyell);
        score = (TextView)findViewById(R.id.score);

        colorNames.add("RED");colorNames.add("BLUE");colorNames.add("GREEN");colorNames.add("YELLOW");
        colors[0]= Color.RED; colors[1]=Color.BLUE;colors[2]=Color.GREEN;colors[3]=Color.YELLOW;

        speed=1000;
        change();

    }
    void change()
    {

        Random rand = new Random();
        int valuetext = rand.nextInt(4);
        final int valuecolor = rand.nextInt(4);
        colorText.setText(colorNames.get(valuetext));
        colorText.setTextColor(colors[valuecolor]);


        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valuecolor==0)
                {
                    sc = Integer.parseInt(score.getText().toString());
                    sc++;
                    score.setText(String.valueOf(sc));
                    speed=speed-10;
                    change();
                }
                else{
                    cd.stop();
                    Intent intent = new Intent(MainActivity.this, PlayAgain.class);
                    intent.putExtra("Score",sc);
                    startActivity(intent);}
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valuecolor==1)
                {
                    sc = Integer.parseInt(score.getText().toString());
                    sc++;
                    score.setText(String.valueOf(sc));
                    speed=speed-10;
                    change();
                }
                else
                {cd.stop();
                    Intent intent = new Intent(MainActivity.this, PlayAgain.class);
                    intent.putExtra("Score",sc);
                startActivity(intent);}
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valuecolor==2)
                {
                    sc = Integer.parseInt(score.getText().toString());
                    sc++;
                    score.setText(String.valueOf(sc));
                    speed=speed-10;
                    change();
                }
                else
                {cd.stop();
                    Intent intent = new Intent(MainActivity.this, PlayAgain.class);
                    intent.putExtra("Score",sc);
                    startActivity(intent);}
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valuecolor==3)
                {
                    sc = Integer.parseInt(score.getText().toString());
                    sc++;
                    score.setText(String.valueOf(sc));
                    speed=speed-10;
                    change();
                }
                else
                {cd.stop();
                    Intent intent = new Intent(MainActivity.this, PlayAgain.class);
                    intent.putExtra("Score",sc);
                    startActivity(intent);}
            }
        });


        cd.start(speed);
        cd.setOnEndAnimationFinish(new OnTimeFinish() {
            @Override
            public void onFinish() {
                cd.stop();
                Intent intent = new Intent(MainActivity.this, PlayAgain.class);
                intent.putExtra("Score",sc);
                startActivity(intent);
            }
        });
    }
}
