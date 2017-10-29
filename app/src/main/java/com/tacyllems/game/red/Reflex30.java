package com.tacyllems.game.red;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.Random;

public class Reflex30 extends AppCompatActivity {

  long timeInMilliseconds = 0L;
  long timeSwapBuff = 0L;
  long updatedTime = 0L;
  @BindView(R.id.colorText) TextView colorText;
  @BindView(R.id.score) TextView score;
  @BindView(R.id.rlred) Button red;
  @BindView(R.id.rlyell) Button yellow;
  @BindView(R.id.rlblu) Button blue;
  @BindView(R.id.rlgree) Button green;
  @BindView(R.id.rel) RelativeLayout rel;
  SharedPreferences sharedPref;
  private TextView timer;
  private long startTime = 0L;
  private Handler customHandler = new Handler();
  private ArrayList<String> colorNames = new ArrayList<>();
  private int colors[] = new int[4];
  private int sc;
  private long speed;
  private int valuecolor;
  private int valuetext;
  private MediaPlayer player;
  private Boolean audio;
  private Random rand = new Random();
  private int init;
  private Runnable updateTimerThread = new Runnable() {
    public void run() {
      timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
      updatedTime = timeSwapBuff + timeInMilliseconds;
      int secs = (int) (updatedTime / 1000);
      int mins = secs / 60;
      secs = secs % 60;
      int milliseconds = (int) (updatedTime % 1000);
      timer.setText("" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d",
          milliseconds));
      customHandler.postDelayed(this, 0);
    }
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.getWindow()
        .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_reflex30);
    ButterKnife.bind(this);
    init = 0;
    timer = findViewById(R.id.timer);
    player = MediaPlayer.create(this, R.raw.ding);
    sharedPref = PreferenceManager.getDefaultSharedPreferences(Reflex30.this);
    audio = sharedPref.getBoolean("audio", true);
    colorNames.add("RED");
    colorNames.add("BLUE");
    colorNames.add("GREEN");
    colorNames.add("YELLOW");
    colors[0] = Color.RED;
    colors[1] = Color.BLUE;
    colors[2] = Color.GREEN;
    colors[3] = Color.YELLOW;
    score.setText("30");
  }

  @OnClick({ R.id.rlred, R.id.rlyell, R.id.rlblu, R.id.rlgree })
  public void onViewClicked(View view) {
    if (audio) {
      if (player.isPlaying() == true) {
        player.stop();
        player.release();
        player = MediaPlayer.create(this, R.raw.ding);
      }
      player.start();
    }
    if (init == 0) {
      startTime = SystemClock.uptimeMillis();
      customHandler.postDelayed(updateTimerThread, 0);
      init++;
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
      sc--;
      score.setText(String.valueOf(sc));
      if (speed > 400) speed = speed - 8;
      change();
    } else {
      if (audio) player.stop();
      timeSwapBuff += timeInMilliseconds;
      customHandler.removeCallbacks(updateTimerThread);
      if (audio) player.stop();
      Intent intent = new Intent();
      intent.putExtra("Score", String.valueOf(updatedTime));
      intent.putExtra("Mode", "reflex");
      intent.putExtra("Did", false);
      setResult(6969, intent);
      finish();
    }
  }

  void change() {
    valuetext = rand.nextInt(4);
    valuecolor = rand.nextInt(4);
    colorText.setText(colorNames.get(valuetext));
    colorText.setTextColor(colors[valuecolor]);
    if (Integer.parseInt(score.getText().toString()) == 0) {
      if (audio) player.stop();
      timeSwapBuff += timeInMilliseconds;
      customHandler.removeCallbacks(updateTimerThread);
      Intent intent = new Intent();
      intent.putExtra("Score", String.valueOf(updatedTime));
      intent.putExtra("Mode", "reflex");
      intent.putExtra("Did", true);
      setResult(6969, intent);
      finish();
    }
  }

  @Override public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus) {
      rel.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
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
