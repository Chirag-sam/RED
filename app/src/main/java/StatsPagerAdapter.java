package com.tacyllems.game.red;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tacyllems.game.red.HighScoreFragment;
import com.tacyllems.game.red.StatsFragment;

/**
 * Created by CHIRAG on 28-09-2017.
 */

public class StatsPagerAdapter extends FragmentStatePagerAdapter {

    int mNumberOfTabs;

    public  StatsPagerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNumberOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                HighScoreFragment hf = new HighScoreFragment();
                return hf;
            case 1:
                StatsFragment sf = new StatsFragment();
                return sf;
            default:
            return null;
        }
    }

    @Override
    public int getCount() {
        return mNumberOfTabs;
    }
}
