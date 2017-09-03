package com.example.chirag.red;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
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

public class StonerHard extends AppCompatActivity {

  private ArrayList<String> colorNames = new ArrayList<>();
  private int colors[] = new int[4];
  private int colordraw[] = {R.drawable.blue_button_background,R.drawable.red_button_background,R.drawable.green_colour_background,R.drawable.yellow_button_background};
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

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hard);

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
    final int valuecolor = rand.nextInt(4);
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
    setTag(colordraw[ran1],butt1);
    butt2.setBackgroundResource(colordraw[ran2]);
    setTag(colordraw[ran2],butt2);
    butt3.setBackgroundResource(colordraw[ran3]);
    setTag(colordraw[ran3],butt3);
    butt4.setBackgroundResource(colordraw[ran4]);
    setTag(colordraw[ran4],butt4);

    butt1.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        int color;
        if(butt1.getTag().equals("red"))
          color = getResources().getColor(R.color.RED);
        else if(butt1.getTag().equals("green"))
          color = getResources().getColor(R.color.GREEN);
        else if(butt1.getTag().equals("yellow"))
          color = getResources().getColor(R.color.YELLOW);
        else
          color = getResources().getColor(R.color.BLUE);

        int contastcolor = OpposeColor(color);
        Log.e("Color:"+color, "textColor:"+colors[valuecolor]+"Red: "+getResources().getColor(R.color.RED)+"Blue: "+getResources().getColor(R.color.BLUE)+"yellow: "+getResources().getColor(R.color.YELLOW)+"Green: "+getResources().getColor(R.color.GREEN));
        if (color == colors[valuecolor]) {
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
    });
    butt3.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        int color;
        if(butt3.getTag().equals("red"))
          color = getResources().getColor(R.color.RED);
        else if(butt3.getTag().equals("green"))
          color = getResources().getColor(R.color.GREEN);
        else if(butt3.getTag().equals("yellow"))
          color = getResources().getColor(R.color.YELLOW);
        else
          color = getResources().getColor(R.color.BLUE);
        int contastcolor = OpposeColor(color);
        Log.e("Color:"+color, "textColor:"+colors[valuecolor]+"Red: "+getResources().getColor(R.color.RED)+"Blue: "+getResources().getColor(R.color.BLUE)+"yellow: "+getResources().getColor(R.color.YELLOW)+"Green: "+getResources().getColor(R.color.GREEN));
        if (color == colors[valuecolor]) {
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
    });
    butt4.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        int color;
        if(butt4.getTag().equals("red"))
          color = getResources().getColor(R.color.RED);
        else if(butt4.getTag().equals("green"))
          color = getResources().getColor(R.color.GREEN);
        else if(butt4.getTag().equals("yellow"))
          color = getResources().getColor(R.color.YELLOW);
        else
          color = getResources().getColor(R.color.BLUE);
        int contastcolor = OpposeColor(color);
        Log.e("Color:"+color, "textColor:"+colors[valuecolor]+"Red: "+getResources().getColor(R.color.RED)+"Blue: "+getResources().getColor(R.color.BLUE)+"yellow: "+getResources().getColor(R.color.YELLOW)+"Green: "+getResources().getColor(R.color.GREEN));        if (color == colors[valuecolor]) {
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
    });
    butt2.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        int color;
        if(butt2.getTag().equals("red"))
          color = getResources().getColor(R.color.RED);
        else if(butt2.getTag().equals("green"))
          color = getResources().getColor(R.color.GREEN);
        else if(butt2.getTag().equals("yellow"))
          color = getResources().getColor(R.color.YELLOW);
        else
          color = getResources().getColor(R.color.BLUE);
        int contastcolor = OpposeColor(color);
        Log.e("Color:"+color, "textColor:"+colors[valuecolor]+"Red: "+getResources().getColor(R.color.RED)+"Blue: "+getResources().getColor(R.color.BLUE)+"yellow: "+getResources().getColor(R.color.YELLOW)+"Green: "+getResources().getColor(R.color.GREEN));        if (color == colors[valuecolor]) {
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
    });

    cd.start(speed);
    cd.setOnEndAnimationFinish(new OnTimeFinish() {
      @Override public void onFinish() {
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
  public void setTag(int draw,View v)
  {
    if(getResources().getDrawable(draw).getConstantState().equals
            (getResources().getDrawable(R.drawable.blue_button_background).getConstantState()))
    {v.setTag("blue");
      Log.e("The tag for", getResources().getDrawable(draw)+" is "+v.getTag());}
    else if(getResources().getDrawable(draw).getConstantState().equals
            (getResources().getDrawable(R.drawable.red_button_background).getConstantState()))
    {v.setTag("red");
    Log.e("The tag for", getResources().getDrawable(draw)+" is "+v.getTag());}
    else if(getResources().getDrawable(draw).getConstantState().equals
            (getResources().getDrawable(R.drawable.green_colour_background).getConstantState()))
    {v.setTag("green");
      Log.e("The tag for", getResources().getDrawable(draw)+" is "+v.getTag());}
    else if(getResources().getDrawable(draw).getConstantState().equals
            (getResources().getDrawable(R.drawable.yellow_button_background).getConstantState()))
    {v.setTag("yellow");
      Log.e("The tag for", getResources().getDrawable(draw)+" is "+v.getTag());}

  }
}