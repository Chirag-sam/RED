package com.tacyllems.game.red;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.start)
    ImageButton start;
    @BindView(R.id.modescontainer)
    RelativeLayout modescontainer;
    @BindView(R.id.modes)
    TextView modes;
    @BindView(R.id.ads)
    ImageButton ads;
    @BindView(R.id.high)
    ImageButton high;
    @BindView(R.id.aboutus)
    ImageButton aboutus;
    @BindView(R.id.mute)
    ImageButton mute;
    Boolean audio;
    SharedPreferences sharedPref;
    InterstitialAd mInterstitialAd;
    @BindView(R.id.playhigh)
    ImageButton playhigh;
    private GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 9001;
    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInflow = true;
    private boolean mSignInClicked = false;
    boolean mExplicitSignOut = false;
    boolean mInSignInFlow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                // add other APIs and scopes here as needed
                .build();
        MobileAds.initialize(this, getString(R.string.appidads));
        mInterstitialAd = new InterstitialAd(this);

        mInterstitialAd.setAdUnitId(getString(R.string.fullscreenadid));

        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                AdRequest adRequest = new AdRequest.Builder().build();
                mInterstitialAd.loadAd(adRequest);
            }
        });

        sharedPref = PreferenceManager.getDefaultSharedPreferences(StartActivity.this);
        audio = sharedPref.getBoolean("audio", true);
        if (audio) {
            mute.setImageResource(R.drawable.ic_volume_up_black_24dp);
        } else {
            mute.setImageResource(R.drawable.ic_volume_off_black_24dp);
        }
        modescontainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog = new Dialog(StartActivity.this, R.style.dialogthemez);
                } else {
                    dialog = new Dialog(StartActivity.this);
                }
                dialog.setContentView(R.layout.mode_dialog);
                RelativeLayout easy = dialog.findViewById(R.id.easy);
                RelativeLayout hard = dialog.findViewById(R.id.hard);
                RelativeLayout stoner = dialog.findViewById(R.id.stoner);
                RelativeLayout reflex30 = dialog.findViewById(R.id.reflex30);
                RelativeLayout multiplayer = dialog.findViewById(R.id.multiplayer);
                easy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        modes.setText("EASY");dialog.dismiss();
                    }
                });
                hard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        modes.setText("HARD");dialog.dismiss();
                    }
                });
                stoner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        modes.setText("STONER HARD");dialog.dismiss();
                    }
                });
                reflex30.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        modes.setText("REFLEX 30");dialog.dismiss();
                    }
                });
                multiplayer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        modes.setText("MULTIPLAYER");dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @OnClick(R.id.start)
    public void onViewClicked() {
        switch (modes.getText().toString()) {
            case "EASY":
                startActivity(new Intent(StartActivity.this, EasyActivity.class));
                break;
            case "HARD":
                startActivity(new Intent(StartActivity.this, HardActivity.class));
                break;
            case "STONER HARD":
                startActivity(new Intent(StartActivity.this, StonerHard.class));
                break;
            case "REFLEX 30":
                startActivity(new Intent(StartActivity.this, Reflex30.class));
                break;
            case "MULTIPLAYER":
                startActivity(new Intent(StartActivity.this, Multiplayer.class));
                break;
            default:
                startActivity(new Intent(StartActivity.this, EasyActivity.class));
        }
    }

    @OnClick({R.id.ads, R.id.high, R.id.aboutus, R.id.mute})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ads:
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                break;
            case R.id.high:
                final Dialog dialog;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog = new Dialog(StartActivity.this, R.style.dialogthemez);
                } else {
                    dialog = new Dialog(StartActivity.this);
                }
                dialog.setContentView(R.layout.highscoredialog);
                TextView easy = dialog.findViewById(R.id.easy);
                TextView hard = dialog.findViewById(R.id.hard);
                TextView stoner = dialog.findViewById(R.id.stoner);
                TextView reflex30 = dialog.findViewById(R.id.reflex30);
                SharedPreferences sharedPreff =
                        PreferenceManager.getDefaultSharedPreferences(StartActivity.this);
                long easys = sharedPreff.getInt("easy", 0);
                easy.setText("EASY: " + easys);
                hard.setText("HARD: " + sharedPreff.getInt("hard", 0));
                stoner.setText("STONER HARD: " + sharedPreff.getInt("stoner", 0));
                Long p = sharedPref.getLong("reflex", 3540000L);

                int secs = (int) (p / 1000);
                int mins = secs / 60;
                secs = secs % 60;
                int milliseconds = (int) (p % 1000);
                if (p == 3540000) {
                    reflex30.setText("REFLEX30:\n" + " 00:00:000");
                } else {
                    reflex30.setText(
                            "REFLEX30:\n" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d",
                                    milliseconds));
                }
                dialog.show();
                break;
            case R.id.aboutus:
                startActivity(new Intent(StartActivity.this, AboutActivity.class));
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.easylead), 200);
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Attempt to reconnect
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInflow) {
            mAutoStartSignInflow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, "Unknown Sign in Error")) {
                mResolvingConnectionFailure = false;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mInSignInFlow && !mExplicitSignOut) {
            // auto sign in
            mGoogleApiClient.connect();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they
                // could not be signed in, such as "Unable to sign in."
                BaseGameUtils.showActivityResultError(this,
                        requestCode, resultCode, 0);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }



    @OnClick(R.id.playhigh)
    public void onPlayhighClicked() {
        mSignInClicked = true;
        if (mGoogleApiClient.isConnected()) {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent( mGoogleApiClient ), 1);
        } else {
            mGoogleApiClient.connect();
        }

    }
}
