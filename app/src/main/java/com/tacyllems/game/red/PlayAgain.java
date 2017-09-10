package com.tacyllems.game.red;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class PlayAgain extends AppCompatActivity {

  @BindView(R.id.sc12) TextView sc;
  @BindView(R.id.best) TextView best;
  @BindView(R.id.restart) ImageButton restart;
  @BindView(R.id.help) ImageButton help;
  @BindView(R.id.home) ImageButton home;
  @BindView(R.id.share) ImageButton share;
  @BindView(R.id.status12) TextView status;
  @BindView(R.id.adView) AdView adView;
  @BindView(R.id.addpont) LinearLayout videoad;
  @BindView(R.id.sctext) TextView sctext;
  InterstitialAd mInterstitialAd;
  RewardedVideoAd mAd;
  @BindView(R.id.gamemode) TextView gamemode;
  @BindView(R.id.progressBar2) ProgressBar progressBar2;
  @BindView(R.id.xp) TextView xp;
  @BindView(R.id.buttonPanel) LinearLayout buttonPanel;
  @BindView(R.id.plus2) ImageView plus2;
  @BindView(R.id.slowitemtext) TextView slowitemtext;
  private MediaPlayer laugh;
  private String ty;
  private String s;
  private int sco;
  private int slowvalue;
  private Boolean audio;
  private Long time;
  private int noofgames = 0;
  private int currextgamexp;
  private int tempxp;
  private int totalxp;
  private ObjectAnimator animation1;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_play_again);
    ButterKnife.bind(this);

    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(PlayAgain.this);
    tempxp = sharedPref.getInt("tempxp", 0);
    progressBar2.getProgressDrawable()
        .setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);

    slowvalue = sharedPref.getInt("slowitem", 0);
    slowitemtext.setText(String.valueOf(slowvalue));

    MobileAds.initialize(this, getString(R.string.appidads));
    AdRequest adRequest = new AdRequest.Builder().build();
    adView.loadAd(adRequest);
    mAd = MobileAds.getRewardedVideoAdInstance(this);
    mAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
    mAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
      @Override public void onRewardedVideoAdLoaded() {

      }

      @Override public void onRewardedVideoAdOpened() {

      }

      @Override public void onRewardedVideoStarted() {

      }

      @Override public void onRewardedVideoAdClosed() {
        mAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
      }

      @Override public void onRewarded(RewardItem rewardItem) {
        slowvalue += rewardItem.getAmount();
        slowitemtext.setText(String.valueOf(slowvalue));
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("slowitem", slowvalue);
        editor.commit();
        // Reward the user.
      }

      @Override public void onRewardedVideoAdLeftApplication() {

      }

      @Override public void onRewardedVideoAdFailedToLoad(int i) {

      }
    });
    mInterstitialAd = new InterstitialAd(this);
    if (BuildConfig.DEBUG) {
      mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
    } else {
      mInterstitialAd.setAdUnitId(getString(R.string.fullscreenadid));
    }
    mInterstitialAd.loadAd(new AdRequest.Builder().build());
    noofgames = sharedPref.getInt("noofgames", 0);
    Log.d("Wsa", "onCreate: " + noofgames);
    if (noofgames < 5) {
      noofgames += 1;
      SharedPreferences.Editor editor = sharedPref.edit();
      editor.putInt("noofgames", noofgames);
      editor.apply();
    } else {
      mInterstitialAd.setAdListener(new AdListener() {
        @Override public void onAdClosed() {

        }

        @Override public void onAdLoaded() {
          if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(null);
          } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
          }

          // Code to be executed when an ad finishes loading.

          Log.i("Ads", "onAdLoaded");
        }
      });
      noofgames = 0;
      SharedPreferences.Editor editor = sharedPref.edit();
      editor.putInt("noofgames", noofgames);
      editor.apply();
    }

    audio = sharedPref.getBoolean("audio", true);
    ty = getIntent().getExtras().get("Mode").toString();
    setgamemode(ty);

    if (audio) {
      laugh = MediaPlayer.create(this, R.raw.dennis);
      laugh.start();
    }
    if (getIntent().hasExtra("Score")) {
      s = getIntent().getExtras().get("Score").toString();
      if (s != null) sco = Integer.parseInt(s);
      currextgamexp = Integer.parseInt(s);
      xp.setText("+" + currextgamexp + "xp");
      SharedPreferences.Editor editorz = sharedPref.edit();
      totalxp = tempxp + currextgamexp;
      if (totalxp > 100) {

        if_more_than_100(totalxp, tempxp);
      } else {
        editorz.putInt("tempxp", totalxp);
        editorz.apply();
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar2, "progress", tempxp, totalxp);
        animation.setDuration(2000);
        animation.start();
      }

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
    if (getIntent().hasExtra("Time")) {

      sctext.setText("TIME");

      time = getIntent().getExtras().getLong("Time");
      Log.e("Time", "time: " + time);
      status.setText("GAME OVER");
      int secs = (int) (time / 1000);
      int mins = secs / 60;
      secs = secs % 60;
      int milliseconds = (int) (time % 1000);
      sc.setText("" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d",
          milliseconds));

      if (getIntent().hasExtra("Did")) {
        time = 3540000L;
        sc.setText("00:00:000");
        xp.setText("+0xp");
      } else {
        currextgamexp = 10;
        SharedPreferences.Editor editorzz = sharedPref.edit();
        totalxp = tempxp + currextgamexp;
        if (totalxp > 100) {
          if_more_than_100(totalxp, tempxp);
        } else {
          editorzz.putInt("tempxp", totalxp);
          editorzz.apply();
          ObjectAnimator animation =
              ObjectAnimator.ofInt(progressBar2, "progress", tempxp, totalxp);
          animation.setDuration(2000);
          animation.start();
        }
        xp.setText("+10xp");
      }

      long highScore = sharedPref.getLong(ty, 3540000);

      if (highScore > time) {
        status.setText("NEW BEST!!");
        if (time == 3540000) {
          highScore = 0;
        } else {
          highScore = time;
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(ty, time);
        editor.commit();
      } else {
        status.setText("GAME OVER!!");
      }
      if (highScore == 3540000) {
        best.setText("BEST: 00:00:000");
      } else {
        secs = (int) (highScore / 1000);
        mins = secs / 60;
        secs = secs % 60;
        milliseconds = (int) (highScore % 1000);
        best.setText(
            "BEST: " + "" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d",
                milliseconds));
      }
    }
  }

  @OnClick({ R.id.restart, R.id.help, R.id.home, R.id.share, R.id.addpont })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.restart:
        if (audio) {
          if (laugh.isPlaying() == true) {
            laugh.stop();
            laugh.release();
          }
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
          case "reflex":
            startActivity(new Intent(PlayAgain.this, Reflex30.class));
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
        TextView reflex30 = dialog.findViewById(R.id.reflex30);
        SharedPreferences sharedPref =
            PreferenceManager.getDefaultSharedPreferences(PlayAgain.this);
        long easys = sharedPref.getInt("easy", 0);
        easy.setText("EASY: " + easys);
        hard.setText("HARD: " + sharedPref.getInt("hard", 0));
        stoner.setText("STONER HARD: " + sharedPref.getInt("stoner", 0));
        Long pq = sharedPref.getLong("reflex", 3540000L);

        if (pq == 3540000) {
          reflex30.setText("REFLEX30:\n" + "00:00:000");
        } else {
          int secs = (int) (pq / 1000);
          int mins = secs / 60;
          secs = secs % 60;
          int milliseconds = (int) (pq % 1000);
          reflex30.setText(
              "REFLEX30:\n" + "" + mins + ":" + String.format("%02d", secs) + ":" + String.format(
                  "%03d", milliseconds));
        }
        dialog.show();
        break;
      case R.id.home:
        if (audio) {
          if (laugh.isPlaying() == true) {
            laugh.stop();
            laugh.release();
          }
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
      case R.id.addpont:
        if (mAd.isLoaded()) mAd.show();
        break;
    }
  }

  @Override public void onResume() {
    mAd.resume(this);
    super.onResume();
  }

  @Override public void onPause() {
    mAd.pause(this);
    super.onPause();
  }

  @Override public void onDestroy() {
    mAd.destroy(this);
    super.onDestroy();
  }

  public void setgamemode(String gamem) {
    if (gamem.equals("easy")) {
      gamemode.setText("EASY");
    } else if (gamem.equals("hard")) {
      gamemode.setText("HARD");
    } else if (gamem.equals("reflex")) {
      gamemode.setText("REFLEX30");
    } else {
      gamemode.setText("STONER HARD");
    }
  }

  public void if_more_than_100(int totalxp, int tempxp) {
    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(PlayAgain.this);
    int sub_xp = totalxp - 100;
    SharedPreferences.Editor editorz = sharedPref.edit();
    editorz.putInt("tempxp", sub_xp);
    editorz.apply();

    ObjectAnimator animation = ObjectAnimator.ofInt(progressBar2, "progress", tempxp, 100);
    animation.setDuration(500);
    animation.addListener(new Animator.AnimatorListener() {
      @Override public void onAnimationStart(Animator animator) {
      }

      @Override public void onAnimationEnd(Animator animator) {

        if (sub_xp > 100) {

          if_more_than_100(sub_xp, 0);
        } else {
          slowvalue += 1;
          slowitemtext.setText(String.valueOf(slowvalue));
          editorz.putInt("slowitem", slowvalue);
          editorz.apply();
          animation1 = ObjectAnimator.ofInt(progressBar2, "progress", 0, sub_xp);
          animation.setDuration(2000);
          animation1.start();
        }
      }

      @Override public void onAnimationCancel(Animator animator) {
      }

      @Override public void onAnimationRepeat(Animator animator) {
      }
    });
    animation.start();
  }
}
