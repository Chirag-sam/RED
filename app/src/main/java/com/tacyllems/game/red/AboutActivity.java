package com.tacyllems.game.red;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about);
        count = 0;

        TextView version = (TextView) findViewById(R.id.introtext2);
        TextView name1 = (TextView) findViewById(R.id.name1);
        TextView name2 = (TextView) findViewById(R.id.name2);
        ImageView logo = (ImageView) findViewById(R.id.introimg);
        ImageView img1 = (ImageView) findViewById(R.id.image1);
        ImageView img2 = (ImageView) findViewById(R.id.image2);

        version.setText("Version: " + GetAppVersion());

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if(count==15)
                {
                    count =0;
                    startActivity(new Intent(AboutActivity.this,Tribute.class));
                    finish();
                }

            }
        });
    }

    private String GetAppVersion() {
        try {
            PackageInfo _info = getPackageManager().getPackageInfo(getPackageName(), 0);
            return _info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "Error. We didn't expect this.";
        }
    }
}