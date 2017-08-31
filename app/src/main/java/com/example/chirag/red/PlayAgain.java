package com.example.chirag.red;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayAgain extends AppCompatActivity {

  @BindView(R.id.sc) TextView sc;
  @BindView(R.id.best) TextView best;
  @BindView(R.id.restart) ImageButton restart;
  @BindView(R.id.help) ImageButton help;
  @BindView(R.id.home) ImageButton home;
  @BindView(R.id.share) ImageButton share;
  private String ty;
  private String s;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_play_again);
    ButterKnife.bind(this);
    s = getIntent().getExtras().get("Score").toString();
    ty = getIntent().getExtras().get("Mode").toString();
    int sco = Integer.parseInt(s);
    TextView sc = findViewById(R.id.sc);
    sc.setText(String.valueOf(sco));
  }

  @OnClick({ R.id.restart, R.id.help, R.id.home, R.id.share })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.restart:
        switch (ty) {
          case "easy":
            startActivity(new Intent(PlayAgain.this, EasyActivity.class));
            finish();
            break;
          case "hard":
            startActivity(new Intent(PlayAgain.this, HardActivity.class));
            finish();
            break;
          case "stoner":
            startActivity(new Intent(PlayAgain.this, StonerHard.class));
            finish();
            break;
        }
        break;
      case R.id.help:
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Sample Text")
            .setMessage(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur sollicitudin diam purus, eget feugiat erat luctus malesuada. Sed egestas convallis metus, sed convallis orci gravida vel. In hac habitasse platea dictumst. Donec non dolor felis. Nam vel varius nibh, at gravida nisl. Curabitur blandit et massa eget lobortis. Integer vel efficitur augue, non fermentum lectus. Proin urna lacus, gravida nec porttitor at, mattis eu ex. Curabitur molestie lectus tellus, id eleifend urna ornare quis. Suspendisse id molestie metu")
            .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
        break;
      case R.id.home:
        finish();
        break;
      case R.id.share:
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
            "I've Got " + sc.getText().toString() + " in RED, Can you beat it ;)");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share Score Via"));
        break;
    }
  }
}
