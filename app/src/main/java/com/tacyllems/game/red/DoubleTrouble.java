package com.tacyllems.game.red;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import java.util.ArrayList;
import java.util.Random;

public class DoubleTrouble extends AppCompatActivity {

  @BindView(R.id.colorText) TextView colorText;
  @BindView(R.id.mCountDownTimer1) CountdownView mCountDownTimer1;
  @BindView(R.id.colorText2) TextView colorText2;
  @BindView(R.id.mCountDownTimer2) CountdownView mCountDownTimer2;
  @BindView(R.id.score) TextView score;
  @BindView(R.id.butt1) Button butt1;
  @BindView(R.id.butt2) Button butt2;
  @BindView(R.id.butt3) Button butt3;
  @BindView(R.id.butt4) Button butt4;
  SharedPreferences sharedPref;
  int colorss;
  private ArrayList<String> colorNames = new ArrayList<>();
  private int colors[] = new int[4];
  private int sc;
  private long speed;
  private long speed2;
  private int valuecolor;
  private int valuetext;
  private int valuecolor2;
  private int valuetext2;
  private int slowvalue;
  private Random rand = new Random();
  private MediaPlayer player;
  private Boolean audio;
  private Boolean frozen = false;
  private Boolean gameOver = false;
  private ArrayList<Integer> al = new ArrayList<>();
  private int colordraw[] = {
      R.drawable.red_button_background, R.drawable.blue_button_background,
      R.drawable.green_colour_background, R.drawable.yellow_button_background
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.getWindow()
        .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_double_trouble);
    ButterKnife.bind(this);
    player = MediaPlayer.create(this, R.raw.ding);
    sharedPref = PreferenceManager.getDefaultSharedPreferences(DoubleTrouble.this);
    audio = sharedPref.getBoolean("audio", true);
    slowvalue = sharedPref.getInt("slowitem", 0);
    //slowitemtext.setText(String.valueOf(slowvalue));
    colorNames.add("RED");
    colorNames.add("BLUE");
    colorNames.add("GREEN");
    colorNames.add("YELLOW");
    colors[0] = Color.RED;
    colors[1] = Color.BLUE;
    colors[2] = Color.GREEN;
    colors[3] = Color.YELLOW;
    speed = 4000;
    speed2 = 4000;
    change("col1");
    change("col2");

    mCountDownTimer1.setOnCountdownEndListener(cv -> {
      if (audio) player.stop();
      mCountDownTimer1.stop();
      Intent intent = new Intent(DoubleTrouble.this, PlayAgain.class);
      intent.putExtra("Score", sc);
      intent.putExtra("Mode", "double");
      startActivity(intent);
      finish();
    });
    mCountDownTimer2.setOnCountdownEndListener(cv -> {
      if (audio) player.stop();
      mCountDownTimer2.stop();
      Intent intent = new Intent(DoubleTrouble.this, PlayAgain.class);
      intent.putExtra("Score", sc);
      intent.putExtra("Mode", "double");
      startActivity(intent);
      finish();
    });
  }

  void change(String coltype) {
    if (coltype.equals("col1")) {
      al.clear();
      int ran1 = generateRandom(4, null);
      al.add(ran1);
      int ran2 = generateRandom(4, al);
      butt1.setBackgroundResource(colordraw[ran1]);
      setTag(colordraw[ran1], butt1);
      butt3.setBackgroundResource(colordraw[ran2]);
      setTag(colordraw[ran2], butt3);

      valuetext = rand.nextInt(4);
      valuecolor = rand.nextBoolean() ? ran1 : ran2;
      colorText.setText(colorNames.get(valuetext));
      colorText.setTextColor(colors[valuecolor]);
      //        if (!frozen) {
      mCountDownTimer1.start(speed);
    } else {
      al.clear();
      int ran1 = generateRandom(4, null);
      al.add(ran1);
      int ran2 = generateRandom(4, al);
      butt2.setBackgroundResource(colordraw[ran1]);
      setTag(colordraw[ran1], butt2);
      butt4.setBackgroundResource(colordraw[ran2]);
      setTag(colordraw[ran2], butt4);

      valuetext2 = rand.nextBoolean() ? ran1 : ran2;
      valuecolor2 = rand.nextBoolean() ? ran1 : ran2;
      colorText2.setText(colorNames.get(valuetext2));
      colorText2.setTextColor(colors[valuecolor2]);
      mCountDownTimer2.start(speed2);
    }

    //        }
  }

  @OnClick({ R.id.butt1, R.id.butt3 }) public void onViewClicked(View view) {
    if (audio) {
      if (player.isPlaying()) {
        player.stop();
        player.release();
        player = MediaPlayer.create(this, R.raw.ding);
      }
      player.start();
    }
    switch (view.getId()) {
      case R.id.butt1:
        checkcolor(butt1, "col1");
        break;
      case R.id.butt3:
        checkcolor(butt3, "col1");
        break;
    }
  }

  @OnClick({ R.id.butt2, R.id.butt4 }) public void onOtherViewClicked(View view) {
    if (audio) {
      if (player.isPlaying()) {
        player.stop();
        player.release();
        player = MediaPlayer.create(this, R.raw.ding);
      }
      player.start();
    }
    switch (view.getId()) {
      case R.id.butt2:
        checkcolor(butt2, "col2");
        break;
      case R.id.butt4:
        checkcolor(butt4, "col2");
        break;
    }
  }

  void checkcolor(View view, String coltype) {
    int color, answercolor;
    String tempcolor;
    if (view.getTag().equals("red")) {
      color = getResources().getColor(R.color.RED);
    } else if (view.getTag().equals("green")) {
      color = getResources().getColor(R.color.GREEN);
    } else if (view.getTag().equals("yellow")) {
      color = getResources().getColor(R.color.YELLOW);
    } else {
      color = getResources().getColor(R.color.BLUE);
    }
    if (coltype.equals("col1")) {
      if (color == colors[valuecolor]) {
        sc = Integer.parseInt(score.getText().toString());
        sc++;
        score.setText(String.valueOf(sc));
        if (speed > 400) speed = speed - 8;
        change("col1");
      } else {
        mCountDownTimer1.stop();
        gameOver = true;
        if (audio) player.stop();
        Intent intent = new Intent(DoubleTrouble.this, PlayAgain.class);
        intent.putExtra("Score", sc);
        intent.putExtra("Mode", "double");
        startActivity(intent);
        finish();
      }
    } else {
      Log.e("col2", "checkcolor: " + colorText2.getText()
          .toString()
          .equalsIgnoreCase(view.getTag().toString()));
      if (colorText2.getText().toString().equalsIgnoreCase(view.getTag().toString())) {
        sc = Integer.parseInt(score.getText().toString());
        sc++;
        score.setText(String.valueOf(sc));
        if (speed2 > 400) speed2 = speed2 - 8;
        change("col2");
      } else {
        mCountDownTimer2.stop();
        gameOver = true;
        if (audio) player.stop();
        Intent intent = new Intent(DoubleTrouble.this, PlayAgain.class);
        intent.putExtra("Score", sc);
        intent.putExtra("Mode", "double");
        startActivity(intent);
        finish();
      }
    }
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
    if (getResources().getDrawable(draw)
        .getConstantState()
        .equals(getResources().getDrawable(R.drawable.blue_button_background).getConstantState())) {
      v.setTag("blue");
    } else if (getResources().getDrawable(draw)
        .getConstantState()
        .equals(getResources().getDrawable(R.drawable.red_button_background).getConstantState())) {
      v.setTag("red");
    } else if (getResources().getDrawable(draw)
        .getConstantState()
        .equals(
            getResources().getDrawable(R.drawable.green_colour_background).getConstantState())) {
      v.setTag("green");
    } else if (getResources().getDrawable(draw)
        .getConstantState()
        .equals(
            getResources().getDrawable(R.drawable.yellow_button_background).getConstantState())) {
      v.setTag("yellow");
    }
  }

 /*     @OnClick(R.id.freeze)
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
      }*/
}
