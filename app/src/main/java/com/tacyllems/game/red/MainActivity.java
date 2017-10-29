package com.tacyllems.game.red;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.google.example.games.basegameutils.BaseGameUtils;

public class MainActivity extends AppCompatActivity
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    StartFragment.OnFragmentInteractionListener, PlayAgainFragment.OnFragmentInteractionListener {
  // request codes we use when invoking an external activity
  private static final int RC_RESOLVE = 5000;
  private static final int RC_UNUSED = 5001;
  private static final int RC_SIGN_IN = 9001;
  private static final int RC_GAMESCORE = 6969;
  // tag for debug logging
  final boolean ENABLE_DEBUG = true;
  final String TAG = "MainActivity";
  // Fragments
  StartFragment mMainMenuFragment;
  PlayAgainFragment mWinFragment;
  boolean mExplicitSignOut = false;
  boolean mInSignInFlow = false;
  @BindView(R.id.root) RelativeLayout root;
  @BindView(R.id.fragment) FrameLayout fragment;
  // Client used to interact with Google APIs
  private GoogleApiClient mGoogleApiClient;
  // Are we currently resolving a connection failure?
  private boolean mResolvingConnectionFailure = false;
  // Has the user clicked the sign-in button?
  private boolean mSignInClicked = false;
  // Automatically start the sign-in flow when the Activity starts
  private boolean mAutoStartSignInFlow = true;
  private SharedPreferences sharedPref;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.getWindow()
        .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_start);
    ButterKnife.bind(this);
    sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
    if (savedInstanceState != null) {
      return;
    }
    mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(Games.API)
        .addScope(Games.SCOPE_GAMES)
        .build();

    // create fragments
    mMainMenuFragment = new StartFragment();
    mWinFragment = new PlayAgainFragment();

    // listen to fragment events

    getSupportFragmentManager().beginTransaction().add(R.id.fragment, mMainMenuFragment).commit();

    MobileAds.initialize(this, getString(R.string.appidads));
  }

  // Switch UI to the given fragment
  void switchToFragment(Fragment newFrag) {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment, newFrag)
        .addToBackStack(null)
        .commit();
  }

  private boolean isSignedIn() {
    return (mGoogleApiClient != null && mGoogleApiClient.isConnected());
  }

  @Override protected void onStop() {
    super.onStop();
    Log.d(TAG, "onStop(): disconnecting");
    if (mGoogleApiClient.isConnected()) {
      mGoogleApiClient.disconnect();
    }
  }

  @Override protected void onStart() {
    super.onStart();
    callintroifnecessary();
    if (!mInSignInFlow && !mExplicitSignOut) {
      // auto sign in
      mGoogleApiClient.connect();
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    super.onActivityResult(requestCode, resultCode, intent);
    if (requestCode == RC_SIGN_IN) {
      mSignInClicked = false;
      mResolvingConnectionFailure = false;
      if (resultCode == RESULT_OK) {
        mGoogleApiClient.connect();
      } else {
        BaseGameUtils.showActivityResultError(this, requestCode, resultCode, R.string.sign_in_other_error);
      }
    }
    if (resultCode == Activity.RESULT_CANCELED) {
      // Do something
    }
    if (requestCode == RC_GAMESCORE && intent != null) {
      String mode = intent.getStringExtra("Mode");
      String sco = intent.getStringExtra("Score");
      Boolean did = intent.getBooleanExtra("Did", false);

      PlayAgainFragment p;
      switch (mode) {
        case "easy":
          p = PlayAgainFragment.newInstance(mode, sco);
          switchToFragment(p);
          if (mGoogleApiClient.isConnected()) {
            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.easylead),
                Integer.parseInt(sco));
          }
          break;
        case "hard":
          p = PlayAgainFragment.newInstance(mode, sco);
          switchToFragment(p);
          if (mGoogleApiClient.isConnected()) {
            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.hardlead),
                Integer.parseInt(sco));
          }
          break;
        case "stoner":
          p = PlayAgainFragment.newInstance(mode, sco);
          switchToFragment(p);
          if (mGoogleApiClient.isConnected()) {
            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.stonerhardlead),
                Integer.parseInt(sco));
          }
          break;
        case "reflex":
          p = PlayAgainFragment.newInstance(mode, sco, did);
          switchToFragment(p);
          break;
        case "double":
          p = PlayAgainFragment.newInstance(mode, sco);
          switchToFragment(p);
          break;
      }
    }
  }

  @Override public void onConnected(Bundle bundle) {
    Log.d(TAG, "onConnected(): connected to Google APIs");
    // Show sign-out button on main menu
    if (mMainMenuFragment != null) mMainMenuFragment.setShowSignInButton(false);

    // Set the greeting appropriately on main menu
    Player p = Games.Players.getCurrentPlayer(mGoogleApiClient);
  }

  @Override public void onConnectionSuspended(int i) {
    Log.d(TAG, "onConnectionSuspended(): attempting to connect");
    mGoogleApiClient.connect();
  }

  @Override public void onConnectionFailed(ConnectionResult connectionResult) {
    Log.d(TAG, "onConnectionFailed(): attempting to resolve");
    if (mResolvingConnectionFailure) {
      Log.d(TAG, "onConnectionFailed(): already resolving");
      return;
    }

    if (mSignInClicked || mAutoStartSignInFlow) {
      mAutoStartSignInFlow = false;
      mSignInClicked = false;
      mResolvingConnectionFailure =
          BaseGameUtils.resolveConnectionFailure(this, mGoogleApiClient, connectionResult,
              RC_SIGN_IN, getString(R.string.sign_in_other_error));
    }

    mMainMenuFragment.setShowSignInButton(true);
  }

  @Override public void onSignInButtonClicked() {
    // start the sign-in flow
    mSignInClicked = true;
    mGoogleApiClient.connect();
  }

  @Override public void onSignOutButtonClicked() {
    mSignInClicked = false;
    Games.signOut(mGoogleApiClient);
    if (mGoogleApiClient.isConnected()) {
      mGoogleApiClient.disconnect();
    }

    mMainMenuFragment.setShowSignInButton(true);
  }

  //@OnClick(R.id.playhigh) public void onPlayhighClicked() {
  //  mSignInClicked = true;
  //  if (mGoogleApiClient.isConnected()) {
  //    startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(mGoogleApiClient), 1);
  //  } else {
  //    mGoogleApiClient.connect();
  //  }
  //}

  @Override public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus) {
      root.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_FULLSCREEN
          | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
  }

  void callintroifnecessary() {
    long gamesplayed = sharedPref.getInt("introshown", 0);
    if (gamesplayed == 0) {
      startActivity(new Intent(MainActivity.this, IntroActivity.class));
      SharedPreferences.Editor editor = sharedPref.edit();
      editor.putInt("introshown", 1);
      editor.apply();
    }
  }

  @Override public void onStartFragmentInteraction(String activity) {
    switch (activity) {
      case "EASY":
        startActivityForResult(new Intent(MainActivity.this, EasyActivity.class), RC_GAMESCORE);
        break;
      case "HARD":
        startActivityForResult(new Intent(MainActivity.this, HardActivity.class), RC_GAMESCORE);
        break;
      case "STONER HARD":
        startActivityForResult(new Intent(MainActivity.this, StonerHard.class), RC_GAMESCORE);
        break;
      case "REFLEX 30":
        startActivityForResult(new Intent(MainActivity.this, Reflex30.class), RC_GAMESCORE);
        break;
      case "MULTIPLAYER":
        startActivity(new Intent(MainActivity.this, Multiplayer.class));
        break;
      case "DOUBLE TROUBLE":
        startActivityForResult(new Intent(MainActivity.this, DoubleTrouble.class), RC_GAMESCORE);
        break;
      case "HIGH SCORES":
        startActivity(new Intent(MainActivity.this, StatsActivity.class));
        break;
      case "PLAY HIGH SCORES":
        if (isSignedIn()) {
          startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(mGoogleApiClient),
              RC_UNUSED);
        } else {
          BaseGameUtils.makeSimpleDialog(this, getString(R.string.sign_in_other_error)).show();
        }
        break;
      case "ABOUT US":
        startActivity(new Intent(MainActivity.this, AboutActivity.class));
        break;
      case "INFO":
        startActivity(new Intent(MainActivity.this, IntroActivity.class));
        break;
      default:
        startActivity(new Intent(MainActivity.this, EasyActivity.class));
    }
  }

  @Override public void onLeaderBoardInteraction(String mode) {
    if (mGoogleApiClient.isConnected()) {
      switch (mode) {
        case "easy":
          startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
              getString(R.string.easylead)), 1);
          break;
        case "hard":
          startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
              getString(R.string.hardlead)), 1);
          break;
        case "stoner":
          startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
              getString(R.string.stonerhardlead)), 1);
          break;
      }
    }
  }

  @Override public void onLaunchActivityInteraction(String activity) {
    switch (activity) {
      case "easy":
        getSupportFragmentManager().popBackStackImmediate();
        startActivityForResult(new Intent(this, EasyActivity.class), RC_GAMESCORE);

        break;
      case "hard":
        getSupportFragmentManager().popBackStackImmediate();
        startActivityForResult(new Intent(this, HardActivity.class), RC_GAMESCORE);

        break;
      case "stoner":
        getSupportFragmentManager().popBackStackImmediate();
        startActivityForResult(new Intent(this, StonerHard.class), RC_GAMESCORE);

        break;
      case "reflex":
        getSupportFragmentManager().popBackStackImmediate();
        startActivityForResult(new Intent(this, Reflex30.class), RC_GAMESCORE);

        break;
      case "double":
        getSupportFragmentManager().popBackStackImmediate();
        startActivityForResult(new Intent(this, DoubleTrouble.class), RC_GAMESCORE);
        break;
      case "help":
        startActivity(new Intent(MainActivity.this, IntroActivity.class));
        break;
      case "back":
        getSupportFragmentManager().popBackStackImmediate();
    }
  }
}
