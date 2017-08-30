package com.example.chirag.red;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayAgain extends AppCompatActivity {

    ImageView iv;
    ImageView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_again);

        String s = getIntent().getExtras().get("Score").toString();
        final String ty = getIntent().getExtras().get("Mode").toString();
        int sco = Integer.parseInt(s);
        TextView sc = (TextView)findViewById(R.id.sc);
        sc.setText("SCORE: "+String.valueOf(sco));

        iv = (ImageView)findViewById(R.id.iv);
        home = (ImageView)findViewById(R.id.home);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ty.equals("easy"))
                {
                    Intent intent = new Intent(PlayAgain.this, EasyActivity.class);
                    startActivity(intent);
                finish();}
                if(ty.equals("hard"))
                {
                    Intent intent = new Intent(PlayAgain.this, HardActivity.class);
                    startActivity(intent);
                finish();}
                if(ty.equals("stoner"))
                {
                    Intent intent = new Intent(PlayAgain.this, StonerHard.class);
                    startActivity(intent);
                    finish();}
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayAgain.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(PlayAgain.this, R.style.pop);
        builder.setMessage("Are You Sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
        //  super.onBackPressed();
    }
}
