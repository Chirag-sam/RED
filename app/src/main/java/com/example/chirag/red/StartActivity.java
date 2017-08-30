package com.example.chirag.red;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class StartActivity extends AppCompatActivity {

    Button start;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_text,getResources().getStringArray(R.array.modes));
        spinner.setAdapter(adapter);
        start = (Button) findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (spinner.getSelectedItem().toString())
                {
                    case "EASY":

                        startActivity(new Intent(StartActivity.this, EasyActivity.class));
                        finish();
                        break;
                    case "HARD":
                        startActivity(new Intent(StartActivity.this, HardActivity.class));
                        finish();
                        break;
                    case "STONER HARD":
                        startActivity(new Intent(StartActivity.this, StonerHard.class));
                        finish();
                        break;
                    default:
                        startActivity(new Intent(StartActivity.this, EasyActivity.class));
                        finish();
                }
            }
        });

    }
}
