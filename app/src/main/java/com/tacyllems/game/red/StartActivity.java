package com.tacyllems.game.red;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class StartActivity extends AppCompatActivity {

  @BindView(R.id.start) Button start;
  @BindView(R.id.spinner) AppCompatSpinner spinner;
  @BindView(R.id.ads) ImageButton ads;
  @BindView(R.id.high) ImageButton high;
  @BindView(R.id.aboutus) ImageButton aboutus;
  @BindView(R.id.mute) ImageButton mute;
  Boolean audio;
  SharedPreferences sharedPref;
  InterstitialAd mInterstitialAd;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_start);
    ButterKnife.bind(this);
    MobileAds.initialize(this, getString(R.string.appidads));
    mInterstitialAd = new InterstitialAd(this);
    if (BuildConfig.DEBUG) {
      mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
    } else {
      mInterstitialAd.setAdUnitId(getString(R.string.fullscreenadid));
    }
    mInterstitialAd.loadAd(new AdRequest.Builder().build());
    mInterstitialAd.setAdListener(new AdListener() {
      @Override public void onAdClosed() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
      }
    });
    sharedPref = PreferenceManager.getDefaultSharedPreferences(StartActivity.this);
    audio = sharedPref.getBoolean("audio", true);
    if (audio) {
      mute.setImageResource(R.drawable.ic_volume_up_black_24dp);
    } else {
      mute.setImageResource(R.drawable.ic_volume_off_black_24dp);
    }
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_text,
        getResources().getStringArray(R.array.modes));
    spinner.setAdapter(adapter);
  }

  @OnClick(R.id.start) public void onViewClicked() {
    switch (spinner.getSelectedItem().toString()) {
      case "EASY":
        startActivity(new Intent(StartActivity.this, EasyActivity.class));
        break;
      case "HARD":
        startActivity(new Intent(StartActivity.this, HardActivity.class));
        break;
      case "STONER HARD":
        startActivity(new Intent(StartActivity.this, StonerHard.class));
        break;
      case "REFLEX 30":
        startActivity(new Intent(StartActivity.this, Reflex30.class));
        break;
      default:
        startActivity(new Intent(StartActivity.this, EasyActivity.class));
    }
  }

  @OnClick({ R.id.ads, R.id.high, R.id.aboutus, R.id.mute }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.ads:
        if (mInterstitialAd.isLoaded()) {
          mInterstitialAd.show();
        } else {
          Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
        break;
      case R.id.high:
        final Dialog dialog;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          dialog = new Dialog(StartActivity.this, R.style.dialogthemez);
        } else {
          dialog = new Dialog(StartActivity.this);
        }
        dialog.setContentView(R.layout.highscoredialog);
        TextView easy = dialog.findViewById(R.id.easy);
        TextView hard = dialog.findViewById(R.id.hard);
        TextView stoner = dialog.findViewById(R.id.stoner);
        TextView reflex30 = dialog.findViewById(R.id.reflex30);
        SharedPreferences sharedPreff = PreferenceManager.getDefaultSharedPreferences(StartActivity.this);
        long easys = sharedPreff.getInt("easy",0);
        easy.setText("EASY: "+easys);
        hard.setText("HARD: "+sharedPreff.getInt("hard",0));
        stoner.setText("STONER HARD: "+sharedPreff.getInt("stoner",0));
        Long p = sharedPref.getLong("reflex30",354000);

        int secs = (int) (p / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        int milliseconds = (int) (p % 1000);
        if(p==354000)
        {
          reflex30.setText("REFLEX30:"+" 00:00:000");
        }
        else
        reflex30.setText("REFLEX30:"+"" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d",
                milliseconds));
        dialog.show();
        break;
      case R.id.aboutus:
        break;
      case R.id.mute:
        audio = sharedPref.getBoolean("audio", true);
        if (audio) {
          mute.setImageResource(R.drawable.ic_volume_off_black_24dp);
          SharedPreferences.Editor editor = sharedPref.edit();
          editor.putBoolean("audio", false);
          editor.commit();
        } else {
          mute.setImageResource(R.drawable.ic_volume_up_black_24dp);
          SharedPreferences.Editor editor = sharedPref.edit();
          editor.putBoolean("audio", true);
          editor.commit();
        }
        break;
    }
  }
}
