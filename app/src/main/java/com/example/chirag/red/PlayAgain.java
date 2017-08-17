package com.example.chirag.red;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayAgain extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_again);

        String s = getIntent().getExtras().get("Score").toString();
        int sco = Integer.parseInt(s);
        TextView sc = (TextView)findViewById(R.id.sc);
        sc.setText("SCORE: "+String.valueOf(sco));

        iv = (ImageView)findViewById(R.id.iv);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayAgain.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
