package com.example.chirag.red;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayAgain extends AppCompatActivity {

  @BindView(R.id.sc) TextView sc;
  @BindView(R.id.best) TextView best;
  @BindView(R.id.restart) ImageButton restart;
  @BindView(R.id.help) ImageButton help;
  @BindView(R.id.home) ImageButton home;
  @BindView(R.id.share) ImageButton share;
  @BindView(R.id.status) TextView status;
  private MediaPlayer laugh;
  private String ty;
  private String s;
  private Boolean audio;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_play_again);
    ButterKnife.bind(this);
    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(PlayAgain.this);
    audio = sharedPref.getBoolean("audio",true);
    if(audio)
    {
      laugh = MediaPlayer.create(this, R.raw.dennis);
      laugh.start();
    }
    s = getIntent().getExtras().get("Score").toString();
    ty = getIntent().getExtras().get("Mode").toString();
    int sco = Integer.parseInt(s);

    long highScore = sharedPref.getInt(ty, 0);
    if (highScore < sco) {
      status.setText("NEW BEST!!");
      highScore = sco;
      SharedPreferences.Editor editor = sharedPref.edit();
      editor.putInt(ty, sco);
      editor.commit();
    } else {
      status.setText("GAME OVER!!");
    }
    sc.setText(String.valueOf(sco));
    best.setText("BEST: " + String.valueOf(highScore));
  }

  @OnClick({ R.id.restart, R.id.help, R.id.home, R.id.share })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.restart:
        if(laugh.isPlaying()==true)
        {
          laugh.stop();
          laugh.release();
        }
        switch (ty) {
          case "easy":
            startActivity(new Intent(PlayAgain.this, EasyActivity.class));
            finish();
            break;
          case "hard":
            startActivity(new Intent(PlayAgain.this, HardActivity.class));
            finish();
            break;
          case "stoner":
            startActivity(new Intent(PlayAgain.this, StonerHard.class));
            finish();
            break;
        }
        break;
      case R.id.help:
//        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Sample Text")
//            .setMessage(
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur sollicitudin diam purus, eget feugiat erat luctus malesuada. Sed egestas convallis metus, sed convallis orci gravida vel. In hac habitasse platea dictumst. Donec non dolor felis. Nam vel varius nibh, at gravida nisl. Curabitur blandit et massa eget lobortis. Integer vel efficitur augue, non fermentum lectus. Proin urna lacus, gravida nec porttitor at, mattis eu ex. Curabitur molestie lectus tellus, id eleifend urna ornare quis. Suspendisse id molestie metu")
//            .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
//        AlertDialog dialog = builder.create();
//        dialog.show();
//        break;
        final Dialog dialog;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          dialog = new Dialog(PlayAgain.this, R.style.dialogthemez);
        } else {
          dialog = new Dialog(PlayAgain.this);
        }
        dialog.setContentView(R.layout.highscoredialog);
        TextView easy = dialog.findViewById(R.id.easy);
        TextView hard = dialog.findViewById(R.id.hard);
        TextView stoner = dialog.findViewById(R.id.stoner);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(PlayAgain.this);
        long easys = sharedPref.getInt("easy",0);
        easy.setText("EASY: "+easys);
        hard.setText("HARD: "+sharedPref.getInt("hard",0));
        stoner.setText("STONER HARD: "+sharedPref.getInt("stoner",0));
        dialog.show();
        break;
      case R.id.home:
        if(audio)
          if(laugh.isPlaying()==true)
          {
              laugh.stop();
              laugh.release();
          }
        finish();
        break;
      case R.id.share:
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
            "I've Got " + sc.getText().toString() + " in RED, Can you beat it ;)");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share Score Via"));
        break;
    }
  }
}
