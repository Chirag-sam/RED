package com.tacyllems.game.red;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class StartFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  boolean mShowSignIn = true;
  @BindView(R.id.start) ImageButton start;
  @BindView(R.id.modescontainer) Spinner modescontainer;
  @BindView(R.id.ads) ImageButton ads;
  @BindView(R.id.high) ImageButton high;
  @BindView(R.id.playhigh) ImageButton playhigh;
  @BindView(R.id.mute) ImageButton mute;
  @BindView(R.id.aboutus) ImageButton aboutus;
  @BindView(R.id.info) ImageButton info;
  Unbinder unbinder;
  Boolean audio;
  SharedPreferences sharedPref;
  InterstitialAd mInterstitialAd;
  @BindView(R.id.linearll) LinearLayout linearll;
  @BindView(R.id.signin) Button signin;
  // TODO: Rename and change types of parameters

  private OnFragmentInteractionListener mListener;

  public StartFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
    audio = sharedPref.getBoolean("audio", true);
    mInterstitialAd = new InterstitialAd(getContext());
    mInterstitialAd.setAdUnitId(getString(R.string.fullscreenadid));
    mInterstitialAd.loadAd(new AdRequest.Builder().build());
    mInterstitialAd.setAdListener(new AdListener() {
      @Override public void onAdClosed() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
      }
    });
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_start, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onStart() {
    super.onStart();
    CustomAdapter adapter =
        new CustomAdapter(getContext(), getResources().getStringArray(R.array.game_modes));
    modescontainer.setAdapter(adapter);
    if (getContext() != null) {
      modescontainer.getBackground()
          .setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary),
              PorterDuff.Mode.SRC_ATOP);
    }
    mute.setImageResource(
        audio ? R.drawable.ic_volume_up_black_24dp : R.drawable.ic_volume_off_black_24dp);
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(
          context.toString() + " must implement OnFragmentInteractionListener");
    }
  }

  @Override public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({
      R.id.start, R.id.ads, R.id.high, R.id.playhigh, R.id.mute, R.id.aboutus, R.id.info,
      R.id.signin
  }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.start:
        if (mListener != null) {
          mListener.onStartFragmentInteraction(modescontainer.getSelectedItem().toString());
        }
        break;
      case R.id.ads:
        if (mInterstitialAd.isLoaded()) mInterstitialAd.show();
        break;
      case R.id.high:
        if (mListener != null) {
          mListener.onStartFragmentInteraction("HIGH SCORES");
        }
        break;
      case R.id.playhigh:
        if (mListener != null) {
          mListener.onStartFragmentInteraction("PLAY HIGH SCORES");
        }
        break;
      case R.id.mute:
        audio = sharedPref.getBoolean("audio", true);
        if (audio) {
          mute.setImageResource(R.drawable.ic_volume_off_black_24dp);
          SharedPreferences.Editor editor = sharedPref.edit();
          editor.putBoolean("audio", false);
          editor.apply();
        } else {
          mute.setImageResource(R.drawable.ic_volume_up_black_24dp);
          SharedPreferences.Editor editor = sharedPref.edit();
          editor.putBoolean("audio", true);
          editor.apply();
        }
        break;
      case R.id.aboutus:
        if (mListener != null) {
          mListener.onStartFragmentInteraction("ABOUT US");
        }
        break;
      case R.id.info:
        if (mListener != null) {
          mListener.onStartFragmentInteraction("INFO");
        }
        break;
      case R.id.signin:
        if (mListener != null) {
          if (mShowSignIn) {
            mListener.onSignInButtonClicked();
          } else {
            mListener.onSignOutButtonClicked();
          }
        }
        break;
    }
  }

  public void setShowSignInButton(boolean showSignIn) {
    mShowSignIn = showSignIn;
    if (showSignIn) {
      signin.setText("Play Sign In");
    } else {
      signin.setText("Play Sign out");
    }
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
    void onStartFragmentInteraction(String activity);

    void onSignInButtonClicked();

    void onSignOutButtonClicked();
  }
}
