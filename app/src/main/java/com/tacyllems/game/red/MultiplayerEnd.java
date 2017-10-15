package com.tacyllems.game.red;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MultiplayerEnd extends Activity {

    @BindView(R.id.p1score)
    TextView p1score;
    @BindView(R.id.winner)
    TextView winner;
    @BindView(R.id.p2score)
    TextView p2score;
    @BindView(R.id.home)
    ImageButton home;
    @BindView(R.id.playagain)
    ImageButton playagain;
    @BindView(R.id.rel)
    RelativeLayout rel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_multiplayer_end);
        ButterKnife.bind(this);
        int sc = Integer.parseInt(String.valueOf(getIntent().getExtras().get("Score1")));
        int sc2 = Integer.parseInt(String.valueOf(getIntent().getExtras().getInt("Score2")));
        p1score.setText(String.valueOf(sc));
        p2score.setText(String.valueOf(sc2));

        if (sc > sc2) {
            winner.setText("Player 2 wins!");
        } else if (sc2 > sc) {
            winner.setText("Player 1 wins!");
        } else {
            winner.setText("Draw!!");
        }
    }

    @OnClick({R.id.home, R.id.playagain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home:
                startActivity(new Intent(MultiplayerEnd.this, StartActivity.class));
                break;
            case R.id.playagain:
                startActivity(new Intent(MultiplayerEnd.this, Multiplayer.class));
                break;
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            rel.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
