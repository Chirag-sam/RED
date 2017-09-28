package com.tacyllems.game.red;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatsActivity extends AppCompatActivity implements StatsFragment.OnFragmentInteractionListener, HighScoreFragment.OnFragmentInteractionListener {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);

        tabLayout.addTab(tabLayout.newTab().setText("High Scores"));
        tabLayout.addTab(tabLayout.newTab().setText("Stats"));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

         com.tacyllems.game.red.StatsPagerAdapter adapter = new com.tacyllems.game.red.StatsPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
