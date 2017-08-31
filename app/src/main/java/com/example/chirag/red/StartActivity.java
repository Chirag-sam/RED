package com.example.chirag.red;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {

  @BindView(R.id.start) Button start;
  @BindView(R.id.spinner) AppCompatSpinner spinner;
  @BindView(R.id.ads) ImageButton ads;
  @BindView(R.id.high) ImageButton high;
  @BindView(R.id.aboutus) ImageButton aboutus;
  @BindView(R.id.mute) ImageButton mute;
  Boolean audio;
  SharedPreferences sharedPref;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_start);
    ButterKnife.bind(this);
    sharedPref = this.getPreferences(Context.MODE_PRIVATE);
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
      default:
        startActivity(new Intent(StartActivity.this, EasyActivity.class));
    }
  }

  @OnClick({ R.id.ads, R.id.high, R.id.aboutus, R.id.mute }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.ads:
        break;
      case R.id.high:
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
