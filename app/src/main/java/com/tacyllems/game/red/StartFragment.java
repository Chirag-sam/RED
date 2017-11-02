package com.tacyllems.game.red;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.accessibility.AccessibilityManager;
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
import java.lang.reflect.Method;

/**

 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class StartFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

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
  AlertDialog deleteDialog;
  @BindView(R.id.linearll) LinearLayout linearll;

  // TODO: Rename and change types of parameters

  private OnFragmentInteractionListener mListener;

  public StartFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Boolean hc = check_if_high_contrast_is_on();
    if(hc==true)
    {
      display_dialog();
    }



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
      R.id.start, R.id.ads, R.id.high, R.id.playhigh, R.id.mute, R.id.aboutus, R.id.info
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
    }
  }

  public Boolean check_if_high_contrast_is_on()
  {
    AccessibilityManager am = (AccessibilityManager) getActivity().getSystemService(Context.ACCESSIBILITY_SERVICE);
    Class clazz = am.getClass();
    Method m = null;
    try {
      m = clazz.getMethod("isHighTextContrastEnabled",null);
    } catch (NoSuchMethodException e) {
      Log.e("FAIL", "isHighTextContrastEnabled not found in AccessibilityManager");
      return false;
    }


    Object result = null;
    try {
      result = m.invoke(am, null);
      if (result != null && result instanceof Boolean)  {
        Boolean b = (Boolean)result;
        Log.e("result", "b =" + b);
        return b;
      }
    }  catch (Exception e) {
      android.util.Log.e("fail",  "isHighTextContrastEnabled invoked with an exception" + e.getMessage());
      return false;
    }
    return false;
  }
  public void display_dialog()
  {
    LayoutInflater factory = LayoutInflater.from(getActivity());
    final View deleteDialogView = factory.inflate(R.layout.high_contrast_dialog, null);
    deleteDialog = new AlertDialog.Builder(getActivity()).create();
    deleteDialog.setCancelable(false);
    deleteDialog.setCanceledOnTouchOutside(false);
    deleteDialog.setView(deleteDialogView);
    deleteDialogView.findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
      }
    });

    deleteDialog.show();
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == Activity.RESULT_CANCELED) {
      Boolean b = check_if_high_contrast_is_on();
      if (b == true) {
        display_dialog();
      } else {
        deleteDialog.dismiss();
      }
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
