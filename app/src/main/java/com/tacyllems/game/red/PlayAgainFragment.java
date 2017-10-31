package com.tacyllems.game.red;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayAgainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayAgainFragment extends Fragment implements RewardedVideoAdListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MODE = "Mode";
    private static final String ARG_SCORE = "Score";
    private static final String ARG_DID = "Did";
    private static final String ARG_WINNER = "Winner";
    @BindView(R.id.status12)
    TextView status;
    @BindView(R.id.sctext)
    TextView sctext;
    @BindView(R.id.sc12)
    TextView sc;
    @BindView(R.id.best)
    TextView best;
    @BindView(R.id.restart)
    ImageButton restart;
    @BindView(R.id.help)
    ImageButton help;
    @BindView(R.id.home)
    ImageButton home;
    @BindView(R.id.share)
    ImageButton share;
    @BindView(R.id.playhigh)
    ImageButton playhigh;
    @BindView(R.id.buttonPanel)
    LinearLayout buttonPanel;
    @BindView(R.id.plus2)
    ImageView plus2;
    @BindView(R.id.addpont)
    LinearLayout addpont;
    @BindView(R.id.adView)
    AdView adView;
    @BindView(R.id.gamemode)
    TextView gamemode;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    @BindView(R.id.xp)
    TextView xp;
    @BindView(R.id.infinitetime)
    ImageButton infinitetime;
    @BindView(R.id.slowitemtext)
    TextView slowitemtext;
    @BindView(R.id.root)
    RelativeLayout root;
    Unbinder unbinder;
    InterstitialAd mInterstitialAd;
    RewardedVideoAd mAd;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    // TODO: Rename and change types of parameters
    private String Mode;
    private String Score;
    private Boolean Did;
    private String Winner;
    private int sco;
    private int slowvalue;
    private int noofgames = 0;
    private int currextgamexp;
    private int tempxp;
    private int totalxp;
    private ObjectAnimator animation1;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private OnFragmentInteractionListener mListener;

    public PlayAgainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayAgainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayAgainFragment newInstance(String param1, String param2) {
        PlayAgainFragment fragment = new PlayAgainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MODE, param1);
        args.putString(ARG_SCORE, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static PlayAgainFragment newInstance(String param1, String param2, Boolean param3) {
        PlayAgainFragment fragment = new PlayAgainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MODE, param1);
        args.putString(ARG_SCORE, param2);
        args.putBoolean(ARG_DID, param3);
        fragment.setArguments(args);
        return fragment;
    }

    public static PlayAgainFragment newInstance(String param1, String param2, String param4) {
        PlayAgainFragment fragment = new PlayAgainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MODE, param1);
        args.putString(ARG_SCORE, param2);
        args.putString(ARG_WINNER, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Mode = getArguments().getString(ARG_MODE);
            Score = getArguments().getString(ARG_SCORE);
            Did = getArguments().getBoolean(ARG_DID);
            Winner = getArguments().getString(ARG_WINNER);
        }
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sharedPref.edit();
        tempxp = sharedPref.getInt("tempxp", 0);
        slowvalue = sharedPref.getInt("slowitem", 0);

        mAd = MobileAds.getRewardedVideoAdInstance(getContext());

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.fullscreenadid));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play_again, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        slowitemtext.setText(String.valueOf(slowvalue));
        setgamemode(Mode);
        updatestats(Mode);
        if (getContext() != null) {
            progressBar2.getProgressDrawable()
                    .setColorFilter(ContextCompat.getColor(getContext(), R.color.white),
                            PorterDuff.Mode.SRC_IN);
        }
        switch (Mode) {
            case "reflex":
                sctext.setText("TIME");
                long time = Long.parseLong(Score);
                Log.e("Time", "time: " + time);
                status.setText("GAME OVER");
                int secs = (int) (time / 1000);
                int mins = secs / 60;
                secs = secs % 60;
                int milliseconds = (int) (time % 1000);
                sc.setText("" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d",
                        milliseconds));

                if (!Did) {
                    time = 3540000L;
                    sc.setText("00:00:000");
                    xp.setText("+0xp");
                } else {
                    currextgamexp = 10;
                    totalxp = tempxp + currextgamexp;
                    if (totalxp > 100) {
                        if_more_than_100(totalxp, tempxp);
                    } else {
                        editor.putInt("tempxp", totalxp);
                        editor.apply();
                        ObjectAnimator animation =
                                ObjectAnimator.ofInt(progressBar2, "progress", tempxp, totalxp);
                        animation.setDuration(2000);
                        animation.start();
                    }
                    xp.setText("+10xp");
                }

                long highScoret = sharedPref.getLong(Mode, 3540000);

                if (highScoret > time) {
                    status.setText("NEW BEST!!");
                    if (time == 3540000) {
                        highScoret = 0;
                    } else {
                        highScoret = time;
                    }
                    editor.putLong(Mode, time);
                    editor.commit();
                } else {
                    status.setText("GAME OVER!!");
                }
                if (highScoret == 3540000) {
                    best.setText("BEST: 00:00:000");
                } else {
                    secs = (int) (highScoret / 1000);
                    mins = secs / 60;
                    secs = secs % 60;
                    milliseconds = (int) (highScoret % 1000);
                    best.setText(
                            "BEST: " + "" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d",
                                    milliseconds));
                }
                break;
            case "multiplayer":
                if (Winner.equals("P1")) {
                    best.setText("Player 1 wins!!");
                } else {
                    best.setText("Player 2 wins!!");
                }
                break;
            case "easy"://fall Through
            case "hard":
            case "stoner":
            case "double":
                sco = Integer.parseInt(Score);
                currextgamexp = Integer.parseInt(Score);
                xp.setText("+" + currextgamexp + "xp");
                totalxp = tempxp + currextgamexp;
                if (totalxp > 100) {

                    if_more_than_100(totalxp, tempxp);
                } else {
                    editor.putInt("tempxp", totalxp);
                    editor.apply();
                    ObjectAnimator animation =
                            ObjectAnimator.ofInt(progressBar2, "progress", tempxp, totalxp);
                    animation.setDuration(2000);
                    animation.start();
                }

                long highScore = sharedPref.getInt(Mode, 0);

                if (highScore < sco) {
                    status.setText("NEW BEST!!");
                    highScore = sco;
                    editor.putInt(Mode, sco);
                    editor.commit();
                } else {
                    status.setText("GAME OVER!!");
                }
                sc.setText(String.valueOf(sco));
                best.setText("BEST: " + String.valueOf(highScore));
                break;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(
                    context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.restart, R.id.help, R.id.home, R.id.share, R.id.addpont, R.id.playhigh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restart:
                noofgames = sharedPref.getInt("noofgames", 0);
                Log.d("Wsa", "onCreate: " + noofgames);
                if (noofgames < 5) {
                    noofgames += 1;
                    editor.putInt("noofgames", noofgames);
                    editor.apply();
                    mListener.onLaunchActivityInteraction(Mode);
                } else {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                        noofgames = 0;
                        editor.putInt("noofgames", noofgames);
                        editor.apply();
                    } else {
                        mListener.onLaunchActivityInteraction(Mode);
                    }
                }

                break;
            case R.id.help:
                mListener.onLaunchActivityInteraction("help");
                break;
            case R.id.home:
                mListener.onLaunchActivityInteraction("back");
                break;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "I've Got " + sc.getText().toString() + " in RED, Can you beat it ;)");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share Score Via"));
                break;
            case R.id.addpont:
                showrewardVideo();
                break;
            case R.id.playhigh:
                mListener.onLeaderBoardInteraction(Mode);
                break;
        }
    }

    @Override
    public void onResume() {
        mAd.resume(getContext());
        super.onResume();
    }

    @Override
    public void onPause() {
        mAd.pause(getContext());
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mAd.destroy(getContext());
        super.onDestroy();
    }

    public void setgamemode(String gamem) {
        switch (gamem) {
            case "easy":
                gamemode.setText("EASY");
                break;
            case "hard":
                gamemode.setText("HARD");
                break;
            case "reflex":
                gamemode.setText("REFLEX30");
                break;
            case "double":
                gamemode.setText("DOUBLE TROUBLE");
                break;
            case "multiplayer":
                gamemode.setText("MULTIPLAYER");
                break;
            default:
                gamemode.setText("STONER HARD");
                break;
        }
    }

    public void if_more_than_100(int totalxp, int tempxp) {
        int sub_xp = totalxp - 100;
        editor.putInt("tempxp", sub_xp);
        editor.apply();

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar2, "progress", tempxp, 100);
        animation.setDuration(500);
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {

                if (sub_xp > 100) {

                    if_more_than_100(sub_xp, 0);
                } else {
                    slowvalue += 1;
                    slowitemtext.setText(String.valueOf(slowvalue));
                    editor.putInt("slowitem", slowvalue);
                    editor.apply();
                    animation1 = ObjectAnimator.ofInt(progressBar2, "progress", 0, sub_xp);
                    animation.setDuration(2000);
                    animation1.start();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animation.start();
    }

    public void updatestats(String ty) {
        // for total number of games
        int total;
        total = sharedPref.getInt("totalnumberofgames", 0);
        total++;
        editor.putInt("totalnumberofgames", total);
        editor.apply();

        //for specific modes
        switch (ty) {
            case "easy":
                int total1 = sharedPref.getInt("easytotal", 0);
                total1++;
                editor.putInt("easytotal", total1);
                editor.apply();
                break;
            case "hard":
                int total2 = sharedPref.getInt("hardtotal", 0);
                total2++;
                editor.putInt("hardtotal", total2);
                editor.apply();
                break;
            case "stoner":
                int total3 = sharedPref.getInt("stonertotal", 0);
                total3++;
                editor.putInt("stonertotal", total3);
                editor.apply();
                break;
            case "reflex":
                int total4 = sharedPref.getInt("reflextotal", 0);
                total4++;
                editor.putInt("reflextotal", total4);
                editor.apply();
                break;
            case "multiplayer":
                int total5 = sharedPref.getInt("multitotal", 0);
                total5++;
                editor.putInt("multitotal", total5);
                editor.apply();
                break;
            case "double":
                int total6 = sharedPref.getInt("doubletotal", 0);
                total6++;
                editor.putInt("doubletotal", total6);
                editor.apply();
                break;
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        mAd.show();
        avi.hide();
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        slowvalue += rewardItem.getAmount();
        slowitemtext.setText(String.valueOf(slowvalue));
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("slowitem", slowvalue);
        editor.apply();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

        void onLeaderBoardInteraction(String mode);

        void onLaunchActivityInteraction(String activity);
    }

    public void showrewardVideo() {
        mAd.loadAd(getString(R.string.videoadid), new AdRequest.Builder().build());
        avi.setVisibility(View.VISIBLE);
        avi.show();
        mAd.setRewardedVideoAdListener(this);
    }
}
