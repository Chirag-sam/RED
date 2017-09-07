package com.tacyllems.game.red;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    @BindView(R.id.sc12)
    TextView sc;
    @BindView(R.id.best)
    TextView best;
    @BindView(R.id.restart)
    ImageButton restart;
    @BindView(R.id.help)
    ImageButton help;
    @BindView(R.id.home)
    ImageButton home;
    @BindView(R.id.share)
    ImageButton share;
    @BindView(R.id.status12)
    TextView status;
    @BindView(R.id.adView)
    AdView adView;
    @BindView(R.id.addpont)
    LinearLayout videoad;
    InterstitialAd mInterstitialAd;
    RewardedVideoAd mAd;
    private MediaPlayer laugh;
    private String ty;
    private String s;
    private int sco;
    private int slowvalue;
    private Boolean audio;
    private Long time;
    private int noofgames = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_again);
        ButterKnife.bind(this);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(PlayAgain.this);
        slowvalue = sharedPref.getInt("slowitem", 0);
        MobileAds.initialize(this, getString(R.string.appidads));
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
        mAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {

            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                mAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                slowvalue += rewardItem.getAmount();
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("slowitem", slowvalue);
                editor.commit();
                // Reward the user.
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {

            }
        });
        mInterstitialAd = new InterstitialAd(this);
        if (BuildConfig.DEBUG) {
            mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        } else {
            mInterstitialAd.setAdUnitId(getString(R.string.fullscreenadid));
        }
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                AdRequest adRequest = new AdRequest.Builder().build();
                mInterstitialAd.loadAd(adRequest);
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                noofgames = sharedPref.getInt("noofgames", 0);
                Log.d("Wsa", "onCreate: " + noofgames);
                if (noofgames < 5) {
                    noofgames += 1;
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("noofgames", noofgames);
                    editor.apply();
                } else {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                    noofgames = 0;
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("noofgames", noofgames);
                    editor.apply();
                }
                Log.i("Ads", "onAdLoaded");
            }
        });

        audio = sharedPref.getBoolean("audio", true);
        ty = getIntent().getExtras().get("Mode").toString();

        if (audio) {
            laugh = MediaPlayer.create(this, R.raw.dennis);
            laugh.start();
        }
        if (getIntent().hasExtra("Score")) {
            s = getIntent().getExtras().get("Score").toString();
            if (s != null) sco = Integer.parseInt(s);

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

            time = getIntent().getExtras().getLong("Time");
            Log.e("Time", "time: " + time);
            status.setText("GAME OVER");
            int secs = (int) (time / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (time % 1000);
            sc.setText("" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d",
                    milliseconds));
            if(getIntent().hasExtra("Did"))
            {
                time=354000L;
                sc.setText("00:00:000");
            }

            long highScore = sharedPref.getLong(ty,354000);

            if (highScore > time) {
                status.setText("NEW BEST!!");
                if(time==354000)
                    highScore=0;
                else
                    highScore = time;
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putLong(ty, time);
                editor.commit();
            } else {
                status.setText("GAME OVER!!");
            }
            secs = (int) (time / 1000);
            mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) (time % 1000);
            best.setText("BEST: " + "" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d",
                    milliseconds));

        }
    }

    @OnClick({R.id.restart, R.id.help, R.id.home, R.id.share, R.id.addpont})
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
                Long p = sharedPref.getLong("reflex30",354000);
                int secs = (int) (p / 1000);
                int mins = secs / 60;
                secs = secs % 60;
                int milliseconds = (int) (p % 1000);
                reflex30.setText("REFLEX30:"+"" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d",
                        milliseconds));
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

    @Override
    public void onResume() {
        mAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mAd.destroy(this);
        super.onDestroy();
    }
}
