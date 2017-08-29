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

public class Hard extends AppCompatActivity {

    private ArrayList<String> colorNames=new ArrayList<>();
    private int colors[]=new int[4];
    private CountDownTimerView cd;
    private TextView colorText;
    private Button butt4;
    private Button butt3;
    private Button butt1;
    private Button butt2;
    private TextView score;
    private int sc;
    private long speed;
    private ArrayList<Integer> al = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard);

        cd = (CountDownTimerView) findViewById(R.id.mCountDownTimer);
        colorText = (TextView) findViewById(R.id.colorText);

        butt4 = (Button)findViewById(R.id.butt4);
        butt3 = (Button)findViewById(R.id.butt3);
        butt1 = (Button)findViewById(R.id.butt1);
        butt2 = (Button)findViewById(R.id.butt2);
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

        int ran1 = generateRandom(4,null);

        al.add(ran1);
        int ran2 = generateRandom(4,al);
        al.add(ran2);
        int ran3 = generateRandom(4,al);
        al.add(ran3);
        int ran4 = generateRandom(4,al);

        butt1.setBackgroundColor(colors[ran1]);
        butt2.setBackgroundColor(colors[ran2]);
        butt3.setBackgroundColor(colors[ran3]);
        butt4.setBackgroundColor(colors[ran4]);


        butt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color =((ColorDrawable)butt1.getBackground()).getColor();
                if(color==colors[valuecolor])
                {


                    String hexColor = String.format("#%06X", (0xFFFFFF & color));

                    sc = Integer.parseInt(score.getText().toString());
                    sc++;
                    score.setText(String.valueOf(sc));
                    speed=speed-10;
                    al.clear();
                    change();
                }
                else{
                    cd.stop();
                    Intent intent = new Intent(Hard.this, PlayAgain.class);
                    intent.putExtra("Score",sc);
                    intent.putExtra("Mode","hard");
                    startActivity(intent);}
            }
        });
        butt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color =((ColorDrawable)butt3.getBackground()).getColor();
                if(color==colors[valuecolor])
                {
                    sc = Integer.parseInt(score.getText().toString());
                    sc++;
                    score.setText(String.valueOf(sc));
                    speed=speed-10;
                    al.clear();
                    change();
                }
                else
                {cd.stop();
                    Intent intent = new Intent(Hard.this, PlayAgain.class);
                    intent.putExtra("Score",sc);
                    intent.putExtra("Mode","hard");
                    startActivity(intent);}
            }
        });
        butt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color =((ColorDrawable)butt4.getBackground()).getColor();
                if(color==colors[valuecolor])
                {
                    sc = Integer.parseInt(score.getText().toString());
                    sc++;
                    score.setText(String.valueOf(sc));
                    speed=speed-10;
                    al.clear();
                    change();
                }
                else
                {cd.stop();
                    Intent intent = new Intent(Hard.this, PlayAgain.class);
                    intent.putExtra("Score",sc);
                    intent.putExtra("Mode","hard");
                    startActivity(intent);}
            }
        });
        butt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color =((ColorDrawable)butt2.getBackground()).getColor();
                if(color==colors[valuecolor])
                {
                    sc = Integer.parseInt(score.getText().toString());
                    sc++;
                    score.setText(String.valueOf(sc));
                    speed=speed-10;
                    al.clear();
                    change();
                }
                else
                {cd.stop();
                    Intent intent = new Intent(Hard.this, PlayAgain.class);
                    intent.putExtra("Score",sc);
                    intent.putExtra("Mode","hard");
                    startActivity(intent);}
            }
        });


        cd.start(speed);
        cd.setOnEndAnimationFinish(new OnTimeFinish() {
            @Override
            public void onFinish() {
                cd.stop();
                Intent intent = new Intent(Hard.this, PlayAgain.class);
                intent.putExtra("Score",sc);
                intent.putExtra("Mode","hard");
                startActivity(intent);
            }
        });
    }
    public int generateRandom(int end, ArrayList<Integer> excludeRows) {
        Random rand = new Random();
        int valuetext = rand.nextInt(end);



        if(excludeRows!=null) {
            while (excludeRows.contains(valuetext)) {
                rand = new Random();
                valuetext = rand.nextInt(end);
            }
        }

        return valuetext;
    }
}
