package com.tacyllems.game.red;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StatsFragment extends Fragment {
    @BindView(R.id.totalgamesplayed)
    TextView totalgamesplayed;
    @BindView(R.id.easygamesplayed)
    TextView easygamesplayed;
    @BindView(R.id.hardgamesplayed)
    TextView hardgamesplayed;
    @BindView(R.id.stonergamesplayed)
    TextView stonergamesplayed;
    @BindView(R.id.reflexgamesplayed)
    TextView reflexgamesplayed;
    @BindView(R.id.doublegamesplayed)
    TextView doublegamesplayed;
    @BindView(R.id.multigamesplayed)
    TextView multigamesplayed;
    Unbinder unbinder;
    @BindView(R.id.card_total_games)
    CardView cardTotalGames;
    @BindView(R.id.card_easy_games)
    CardView cardEasyGames;
    @BindView(R.id.card_hard_games)
    CardView cardHardGames;
    @BindView(R.id.card_stoner_games)
    CardView cardStonerGames;
    @BindView(R.id.card_reflex_games)
    CardView cardReflexGames;
    @BindView(R.id.card_double_games)
    CardView cardDoubleGames;
    @BindView(R.id.card_multi_games)
    CardView cardMultiGames;
    private SharedPreferences sharedPref;

    private OnFragmentInteractionListener mListener;

    public StatsFragment() {
        // Required empty public constructor
    }

    public static StatsFragment newInstance(String param1, String param2) {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((StatsActivity) getContext()).setAdapter(1);


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        unbinder = ButterKnife.bind(this, view);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int total = sharedPref.getInt("totalnumberofgames", 0);
        totalgamesplayed.setText(String.valueOf(total));
        int total1 = sharedPref.getInt("hardtotal", 0);
        hardgamesplayed.setText(String.valueOf(total1));
        int total2 = sharedPref.getInt("multitotal", 0);
        multigamesplayed.setText(String.valueOf(total2));
        int total3 = sharedPref.getInt("stonertotal", 0);
        stonergamesplayed.setText(String.valueOf(total3));
        int total4 = sharedPref.getInt("reflextotal", 0);
        reflexgamesplayed.setText(String.valueOf(total4));
        int total5 = sharedPref.getInt("doubletotal", 0);
        doublegamesplayed.setText(String.valueOf(total5));
        int total6 = sharedPref.getInt("easytotal", 0);
        easygamesplayed.setText(String.valueOf(total6));
        leftToRightAnimate(cardTotalGames);
        leftToRightAnimate(cardHardGames);
        leftToRightAnimate(cardReflexGames);
        leftToRightAnimate(cardMultiGames);
        rightToLeftAnimate(cardEasyGames);
        rightToLeftAnimate(cardStonerGames);
        rightToLeftAnimate(cardDoubleGames);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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

    @Override
    public void onResume() {
        Log.d("Stats", "onResume: ");
        super.onResume();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    void rightToLeftAnimate(CardView card){
        Animation right_to_left_animation_for_card = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_right_to_position);
        right_to_left_animation_for_card.setDuration(1000L);
        card.startAnimation(right_to_left_animation_for_card);
    }
    void leftToRightAnimate(CardView card){
        Animation left_to_right_animation_for_card = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_left_to_position);
        left_to_right_animation_for_card.setDuration(1000L);
        card.startAnimation(left_to_right_animation_for_card);
    }
}
