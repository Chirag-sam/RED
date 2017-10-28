package com.tacyllems.game.red;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.newInstance("Welcome to R3D!", "Think you're good with colors? Let's find out!", R.drawable.app_logo, getResources().getColor(R.color.lightish_purple)));
        addSlide(AppIntroFragment.newInstance("Instructions:", "Select the color of the text and not what it says!", R.drawable.app_logo, getResources().getColor(R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Except....", "When you're playing double trouble where you'll have to do both based on what it says! Good luck with that. (Evil laugh)", R.drawable.app_logo, getResources().getColor(R.color.primary)));

    }
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
      finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
      finish();
    }
}
